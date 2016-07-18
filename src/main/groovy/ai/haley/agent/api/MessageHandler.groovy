package ai.haley.agent.api

import com.vitalai.aimp.domain.AIMPMessage

import groovy.lang.Closure;

class MessageHandler {

	//optional filter
	Class<? extends AIMPMessage> msgClass = null
	
	//optional filter for msgClass
	boolean includeSubTypes = false
	
//	{ AgentContext context, List<GraphObject> inputMessage ->
//		return false
//	}
	/**
	 * Closure is provided with AgentContext context, List<GraphObject> inputMessage params.
	 * Must return boolean to indicate if the message was handled and processing should end or not.
	 */
	Closure handler = null 
	/**
	 * @param handler see handler field documentation
	 */
	public MessageHandler(Closure handler) {
		super();
		this.handler = handler;
	}

	/**
	 * 
	 * @param msgClass optionally filter message by class 
	 * @param includeSubTypes works with msgClass filter
	 * @param handler see handler field documentation
	 */
	public MessageHandler(Class<? extends AIMPMessage> msgClass, boolean includeSubTypes, Closure handler) {
		super();
//		if(msgClass == null) throw new NullPointerException("null msgClass")
		if(handler == null) throw new NullPointerException("null handler")
		this.msgClass = msgClass;
		this.includeSubTypes = includeSubTypes;
		this.handler = handler;
	}
	
}
