package ai.haley.agent.domain

import ai.haley.agent.api.AgentContext;
import ai.vital.domain.VITAL_Fact
import ai.vital.vitalsigns.model.GraphObject;
import ai.vital.vitalsigns.model.VITAL_Container

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
	 * a filter that may modify facts graph objects list to be serialized
	 * the graph also contains dialog session object
	 * CURRENT: all input objects are serialized
	 * IN FUTURE: Default filter removes ResultListFacts, SolutionFacts and PredictionFacts with connected objects 
	 * returns Collection<GraphObject>
	 */
	Closure factsGraphFilter = { AgentContext ctx, Collection<GraphObject> inputObjects ->
		//NOP
		
		return inputObjects
		
		//TODO
		/*
		List hyperNodeClasses = [ResultListFact.class, SolutionFact.class, PredictionFact.class]
		List hyperEdgeClasses = [HyperEdge_hasResultElement.class, HyperEdge_hasRecommendationElement.class, HyperEdge_hasPredictionElement.class]
		
		List<GraphObject> outputList = []
		
		for(GraphObject g : inputList) {
			
			for(Class c : hyperNodeClasses) {
				
			}
			if(g.getClass())
			
		}
		
		return outputList
		*/
	}
	
	@Override
	public Object copy() {
		DialogSerialize ds = new DialogSerialize()
		ds.id = id
		ds.factsGraphFilter = factsGraphFilter
		ds.revert = revert
		return ds
	}

	
	
}
