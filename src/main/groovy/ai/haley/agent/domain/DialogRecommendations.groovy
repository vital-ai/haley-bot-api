package ai.haley.agent.domain

import ai.haley.agent.api.AgentContext;
import ai.vital.vitalservice.query.ResultList
import groovy.lang.Closure;;

class DialogRecommendations extends DialogElement {

	//if set a facts graph root -> solution fact is created, all results are linked to this HyperEdge_hasRecommendationElement
	boolean createSolutionFact = false
	
	//set when fact is created
	String solutionFactURI = null
	
	
	//a closure that returns function params, returns Map<String, Object>
	Closure params = { DialogRecommendations thisElement, AgentContext context -> [:] }	
	
	Closure processResults = { DialogRecommendations thisElement, AgentContext context, ResultList results -> 
		//NOP
	}
	
	//by default all recommendations are available
	Closure available = { DialogRecommendations dd, AgentContext context ->
		return true
	}
	
	/**
	 * by default a text message with exception message is sent
	 * params: DialogRecommendations thisElement, AgentContext context, Exception exception
	 */
	Closure onException = null//{ DialogRecommendations thisElement, AgentContext context, Exception exception ->
//		context.replyWithText(null, exception.localizedMessage)
//	}
	
	Object copy() {
		DialogRecommendations d = new DialogRecommendations()
		d.available = available
		d.createSolutionFact = createSolutionFact
		d.id = id
		d.onException = onException
		d.params = params
		d.processResults = processResults
		d.revert = revert
		d.state = new HashMap<String, Object>(state)
		return d
	}
	
}
