package ai.haley.agent.domain

import ai.haley.agent.api.AgentContext;
import ai.vital.vitalservice.query.ResultList
import groovy.lang.Closure;;

class DialogDatascript extends DialogElement {

	String serviceName
	
	String functionName

	//a closure that returns function params, returns Map<String, Object>
	Closure params = { DialogDatascript thisElement, AgentContext context -> [:] }	
	
	//this phase should return true if proceed (callfunction) or skip calling
	Closure validate = { DialogDatascript thisElement, AgentContext context -> 
		//NOP
		return true
	}
	
	Closure processResults = { DialogDatascript thisElement, AgentContext context, ResultList results -> 
		//NOP
	}
	
	//by default all datascripts are available
	Closure available = { DialogDatascript dd, AgentContext context ->
		return true
	}
	
	
	/**
	 * by default a text message with exception message is thrown
	 * params: DialogDatascript thisElement, AgentContext context, Exception exception 
	 */
	Closure onException = null//{ DialogDatascript thisElement, AgentContext context, Exception exception -> 
//		context.replyWithText(null, exception.localizedMessage)
//	}
	
	
	Object copy() {
		DialogDatascript d = new DialogDatascript()
		d.available = available
		d.id = id
		d.functionName = functionName
		d.onException = onException
		d.params = params
		d.processResults = processResults
		d.revert = revert
		d.serviceName = serviceName
		d.state = new HashMap<String, Object>(state)
		d.validate = validate
		return d
	}
	
}
