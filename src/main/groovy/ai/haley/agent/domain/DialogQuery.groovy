package ai.haley.agent.domain

import ai.haley.agent.api.AgentContext;
import ai.vital.query.querybuilder.VitalBuilder
import ai.vital.vitalservice.query.ResultList
import groovy.lang.Closure

class DialogQuery extends DialogElement {

	/**
	 * when enabled it will create {@link com.vitalai.aimp.domain.ResultListFact} appended to this dialog {@link com.vitalai.aimp.domain.DialogSession} element
	 * each result element will be connected to ResultListFact with {@link com.vitalai.aimp.domain.HyperEdge_hasResultElement} hyperedge that has scope property
	 * order of elements guaranteed with core listIndex property 
	 */ 
	boolean createResultListFact = false
	
	//set when fact is created
	String resultListFactURI = null
	
	//by default queries local agent service
	String serviceName
	
	//by default all queries are available
	Closure available = { DialogQuery thisElement, AgentContext context ->
		return true
	}
	
	
	//a closure that creates query, must be overwritten
	Closure createQuery = { DialogQuery thisElement, AgentContext context -> 
		return null
	}
	
	//handles query results, returns nothing
	Closure handleResults = { DialogQuery thisElement, AgentContext context, ResultList results ->
		
	}
	
	/**
	 * by default a text message with exception message is thrown
	 * params: DialogQuery thisElement, AgentContext context, Exception exception
	 */
	Closure onException = null//{ DialogQuerythisElement, AgentContext context, Exception exception ->
//		context.replyWithText(null, exception.localizedMessage)
//	}

	@Override
	public Object copy() {
		DialogQuery dq = new DialogQuery()
		dq.available = available
		dq.createResultListFact = createResultListFact
		dq.createQuery = createQuery
		dq.handleResults = handleResults
		dq.id = id
		dq.onException = onException
		dq.revert = revert
		dq.serviceName = serviceName
		return dq;
	}
	
	
	
}
