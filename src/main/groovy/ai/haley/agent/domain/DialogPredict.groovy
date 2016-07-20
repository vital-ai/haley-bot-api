package ai.haley.agent.domain

import ai.haley.agent.api.AgentContext
import ai.vital.vitalservice.query.ResultList
import groovy.lang.Closure;

class DialogPredict extends DialogElement {

	//a closure that returns function params, returns Map<String, Object>
	Closure params = { DialogPredict thisElement, AgentContext context -> [:] }
	
	Closure processResults = { DialogPredict thisElement, AgentContext context, ResultList results ->
		//NOP
	}
	
	//by default all predicts are available
	Closure available = { DialogPredict dd, AgentContext context ->
		return true
	}
	
	/**
	 * by default a text message with exception message is sent
	 * params: DialogPredict thisElement, AgentContext context, Exception exception
	 */
	Closure onException = null//{ DialogPredict thisElement, AgentContext context, Exception exception ->
//		context.replyWithText(null, exception.localizedMessage)
//	}
	
	@Override
	public Object copy() {
		DialogPredict dp = new DialogPredict()
		dp.available = available
		dp.id = id
		dp.onException = onException
		dp.params = params
		dp.processResults = processResults
		dp.revert = revert
		dp.state = new HashMap<String, Object>(state)
		return dp;
	}

	
	
}
