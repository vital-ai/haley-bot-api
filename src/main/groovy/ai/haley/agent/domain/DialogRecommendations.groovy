package ai.haley.agent.domain

import ai.haley.agent.api.AgentContext;
import ai.vital.vitalservice.query.ResultList
import groovy.lang.Closure;;

class DialogRecommendations extends DialogElement {

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
		d.id = id
		d.onException = onException
		d.params = params
		d.processResults = processResults
		d.revert = revert
		return d
	}
	
}
