package ai.haley.agent.domain

import ai.haley.agent.api.AgentContext
import ai.vital.domain.HyperEdge_hasFact;
import ai.vital.domain.VITAL_Fact
import ai.vital.vitalsigns.meta.GraphContext;
import ai.vital.vitalsigns.model.GraphObject;
import ai.vital.vitalsigns.model.VITAL_Container
import ai.vital.vitalsigns.model.VITAL_HyperEdge;
import ai.vital.vitalsigns.model.VITAL_HyperNode
import ai.vital.vitalsigns.model.VITAL_Node

import com.vitalai.aimp.domain.DialogSession;
import com.vitalai.aimp.domain.HyperEdge_hasPredictionElement;
import com.vitalai.aimp.domain.HyperEdge_hasRecommendationElement;
import com.vitalai.aimp.domain.HyperEdge_hasResultElement
import com.vitalai.aimp.domain.PredictionFact;
import com.vitalai.aimp.domain.PropertyFact
import com.vitalai.aimp.domain.ResultListFact
import com.vitalai.aimp.domain.SolutionFact;;;

/**
 * persists ephemeral dialogsession facts
 * @author Derek
 *
 */
class DialogSerialize extends DialogElement {

	/**
	 * null filter means all solutions facts are skipped
	 * input: AgentContext ctx, SolutionFact solutionFact, List<GraphObject> solutionObjectsWithHyperEdges
	 * Returns filtered graph objects list, null list means the solution fact should be skipped
	 */
	Closure solutionObjectsFilter = null

	
		
	/**
	 * null filter means all result list facts are skipped
	 * input: AgentContext ctx, ResultListFact resultListFact, List<GraphObject> resultListObjectsWithHyperEdges
	 * Returns filtered graph objects list, null list means the result list fact should be skipped
	 */
	Closure resultListObjectsFilter = null
	
	
	/**
	 * null filter means all predictions are skipped
	 * input: AgentContext ctx, PedictionFact predictionFact, List<GraphObject> resultListObjectsWithHyperEdges
	 * Returns filtered graph objects list, null list means the prediction fact should be skipped
	 */
	Closure predictionObjectsFilter = null
	
	
	/**
	 * null filter means all normal facts are skipped
	 * By default all normal facts are persisted (closure returning true) 
	 * input: AgentContext ctx, VITAL_Container factsContainer, GraphObject factParent, VITAL_Fact fact
	 * Returns boolean to indicate if a fact should be persisted or skipped. If false dependent facts will no be processed by this closure and not passed to this closure
	 */
	Closure factsFilter = { AgentContext ctx, VITAL_Container factsContainer, GraphObject factParent, VITAL_Fact fact ->
		return true
	}
	
	
	/**
	 * Default filter is driven by solution-, resultList- prediction- and facts- filters 
	 * returns Collection<GraphObject>
	 */
	Closure factsGraphFilter = { AgentContext ctx, VITAL_Container container, DialogSession dialogSession ->

		List<GraphObject> output = [dialogSession]
		
		for(VITAL_Fact fact : dialogSession.getFacts(GraphContext.Container, container)) {

			if(fact instanceof SolutionFact) {
				
				if(solutionObjectsFilter == null) continue
				
				SolutionFact sf = fact
				
				List<GraphObject> recommendationElements = []
				for(HyperEdge_hasRecommendationElement he : sf.getRecommendationElementHyperEdgesOut(GraphContext.Container, [container])) {
					recommendationElements.add(he)
					GraphObject dest = container.get(he.getDestinationURI())
					if(dest != null) {
						recommendationElements.add(dest)
					}
				}
				
				recommendationElements = solutionObjectsFilter.call(ctx, sf, recommendationElements)
				if(recommendationElements == null) continue
				
				output.add(sf)
				
				for(HyperEdge_hasFact fhe : sf.getFactHyperEdgesIn(GraphContext.Container, [container], dialogSession)) {
					output.add(fhe)
				}
				
				output.addAll(recommendationElements)
				
			} else if(fact instanceof ResultListFact) {
			
				if(resultListObjectsFilter == null) continue
				
				ResultListFact rlf = fact
				
				List<GraphObject> resultListElements = []
				for(HyperEdge_hasResultElement he : rlf.getResultElementHyperEdgesOut(GraphContext.Container, [container])) {
					resultListElements.add(he)
					GraphObject dest = container.get(he.getDestinationURI())
					if(dest != null) {
						resultListElements.add(dest)
					}
				}
				
				resultListElements = resultListObjectsFilter.call(ctx, rlf, resultListElements)
				if(resultListElements == null) continue
				
				output.add(rlf)
				
				for(HyperEdge_hasFact fhe : rlf.getFactHyperEdgesIn(GraphContext.Container, [container], dialogSession)) {
					output.add(fhe)
				}
				
				output.addAll(resultListElements)
				
			} else if(fact instanceof PredictionFact) {
			
				if(predictionObjectsFilter == null) continue
			
				PredictionFact pf = fact
				
				List<GraphObject> predictionElements = []
				for(HyperEdge_hasPredictionElement he : pf.getPredictionElementHyperEdgesOut(GraphContext.Container, [container])) {
					predictionElements.add(he)
					GraphObject dest = container.get(he.getDestinationURI())
					if(dest != null) {
						predictionElements.add(dest)
					}
				}
				
				predictionElements = predictionObjectsFilter.call(ctx, pf, predictionElements)
				if(predictionElements == null) continue
				
				output.add(pf)
			
				for(HyperEdge_hasFact fhe : pf.getFactHyperEdgesIn(GraphContext.Container, [container], dialogSession)) {
					output.add(fhe)
				}
				
				output.addAll(predictionElements)
				
			} else {
			
				//normal fact
			
				processFact(this, output, ctx, container, dialogSession, fact)
				
			
			}
						
		}
		
		return output
	}
	
	static void processFact(DialogSerialize ds, List<GraphObject> output, AgentContext ctx, VITAL_Container factsContainer, GraphObject factParent, VITAL_Fact fact) {
		
		//just skip
		if(ds.factsFilter == null) return
		
		boolean resp = ds.factsFilter(ctx, factsContainer, factParent, fact)
		if(!resp) return
		
		output.add(fact)
		
		for(HyperEdge_hasFact he : fact.getFactHyperEdgesIn(GraphContext.Container, [factsContainer], factParent)) {
			output.add(he)
		}
		
		for(VITAL_Fact f : fact.getFacts(GraphContext.Container, factsContainer)) {
			processFact(ds, output, ctx, factsContainer, fact, f)
		}
		 
		
	}
	
	@Override
	public Object copy() {
		DialogSerialize ds = new DialogSerialize()
		ds.id = id
		ds.factsFilter = factsFilter
		ds.factsGraphFilter = factsGraphFilter
		ds.predictionObjectsFilter = predictionObjectsFilter
		ds.resultListObjectsFilter = resultListObjectsFilter
		ds.revert = revert
		ds.solutionObjectsFilter = solutionObjectsFilter
		ds.state = new HashMap<String, Object>(state)
		return ds
	}

	
	
}
