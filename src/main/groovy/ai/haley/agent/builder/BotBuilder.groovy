package ai.haley.agent.builder

import com.vitalai.aimp.domain.AIMPMessage;

import ai.haley.agent.api.AgentContext
import ai.haley.agent.api.IHaleyAgent
import ai.haley.agent.api.InteractionMode;
import ai.haley.agent.api.MessageHandler
import ai.haley.agent.domain.DialogElement;
import groovy.lang.Closure;

abstract class BotBuilder {

	//runtime bot name
	String name
	
	//set by agent
	static Closure defaultProcessMessage = null
	
	//set by agent
//	static Closure defaultGenerateDialog = null
	
	//set by agent
	//	{ AgentContext context, List<GraphObject> inputMessage -> }
	Closure defaultHandleRetraction = null
		
	//custom
	Closure handleRetraction = null
		
	//set by agent
	//{ AgentContext context, List<GraphObject> inputMessage ->
	Closure defaultHandleSetFact = null
		
	Closure handleSetFact = null
		
	//AgentContext context, List<GraphObject> inputMessage ->
	Closure defaultHandleGetFacts = null
		
	Closure handleGetFacts = null
		
	Map<String, Closure> closureServiceRegistry = [:]
	
	
	public void addServiceClosure(String key, Closure service) {
		
		closureServiceRegistry[key] = service
	}
	
	
	public boolean removeServiceClosure(String key) {
		
		if(closureServiceRegistry.containsKey(key)) {
			
			closureServiceRegistry.remove(key)
			
			return true
		}
		
		return false
		
	}
	
	public Closure getServiceClosure(String key) {
		
		return closureServiceRegistry.get(key)
	}
	
	
	//optional registry called before handle all closure
//	HandlerRegistry handleAllRegistry = null
	private List<MessageHandler> handleAllHandlers = []
		
	//unless overridden the default handle all closure should use handler registry object
	//{AgentContext context, List<GraphObject> inputMessage ->
	Closure defaultHandleAllMessages = null
	//	HandlerRegistry fallbackRegistry = null
		
	Closure handleAllMessages = null
	
	
		
	Closure defaultHandleDialogMessage = null
	//{ AgentContext context, List<GraphObject> inputMessage ->/
	Closure handleDialogMessage = null//{ AgentContext context, List<GraphObject> inputMessage ->
			
	//		return DefaultDialogMessageImplementation.handleDialogMessage(context, inputMessage)
			
	//	}
		
//	HandlerRegistry fallbackRegistry = null
	private List<MessageHandler> fallbackHandlers = []
		
	//unless overridden the default message fallback should use handler registry object
	Closure defaultHandleMessageFallback = null
	Closure handleMessageFallback = null//{ AgentContext context, List<GraphObject> inputMessage ->
	
	
	// AgentContext context, DialogQuestion question
	// the closure returns help message context 
	Closure helpMessageContent = null
	
	
	/**
	 * An optional handler that is called after the bot is switched to and before inital messages are processed
	 * returning true would exit the processing thread immediately
	 * Params: AgentContext context, ActionSource actionSource, List<GraphObject>... intitialMessages
	 */
	Closure onBotSwitched = null//{}() closure, which can be passed events to be handled upon the switch 
	
	
	//dialog pattern should be built once
	private Dialog dialog

	boolean initialized = false
		
	public final void init(IHaleyAgent agent, Map<String, Object> config) {
		
		if(initialized) throw new RuntimeException("Bot already initialized")
		
		setupBot(agent, config)
		
		dialog = buildDialog(agent)
		
		if(dialog == null) throw new RuntimeException("Dialog object not created")
		
		if(dialog.mode == null) throw new RuntimeException("Dialog.mode not set")
		if(dialog.dialogElements == null) throw new RuntimeException("Dialog.dialogElements list is null")
		
		initHandlers(agent)
		
		initialized = true
		
	}
	
	/**
	 * Sets up the bot internals
	 * @param agent
	 * @param config
	 */
	protected abstract void setupBot(IHaleyAgent agent, Map<String, Object> config);
	
	/**
	 * Builds a pattern dialog instance. Called only once in bot lifecycle.
	 * @param agent
	 * @return
	 */
	protected abstract Dialog buildDialog(IHaleyAgent agent) 
	
	/**
	 * Initializes bot handlers. Called only once in bot lifecycle.
	 * @param agent
	 */
	protected abstract void initHandlers(IHaleyAgent agent)
	
	protected abstract void destroy()
	
	public final Dialog getDialog() {
		
		if(dialog == null) throw new Exception("Dialog not initialized")
		Dialog copy = new Dialog()
		copy.mode = dialog.mode
		copy.onClose = dialog.onClose
		copy.cleanup = dialog.cleanup
		for(DialogElement e : dialog.dialogElements) {
			copy.dialogElements.add(e.copy())
		}
		return copy
	}

	public MessageHandler registerFallbackHandler(Closure handler) {
		MessageHandler mh = new MessageHandler(handler)
		registerFallbackHandlerObject(mh)
		return mh
	}
	
	public MessageHandler registerFallbackHandler(Class<? extends AIMPMessage> msgClass, boolean includeSubTypes, Closure handler) {
		MessageHandler mh = new MessageHandler(msgClass, includeSubTypes,handler)
		registerFallbackHandlerObject(mh)
		return mh
	}
	
	public boolean registerFallbackHandlerObject(MessageHandler handler) {
		if(this.fallbackHandlers.contains(handler)) return false
		this.fallbackHandlers.add(handler)
		return true
	}
	
	public boolean unregisterFallbackHandler(MessageHandler handler) {
		return this.fallbackHandlers.remove(handler)
	}
	
	public List<MessageHandler> listFallbackHandlers() {
		return new ArrayList<MessageHandler>(this.fallbackHandlers)
	}
	
	public MessageHandler registerHandleAllHandler(Closure handler) {
		MessageHandler mh = new MessageHandler(handler)
		registerHandleAllHandlerObject(mh)
		return mh
	}
	
	public MessageHandler registerHandleAllHandler(Class<? extends AIMPMessage> msgClass, boolean includeSubTypes, Closure handler) {
		MessageHandler mh = new MessageHandler(msgClass, includeSubTypes,handler)
		registerHandleAllHandlerObject(mh)
		return mh
	}
	
	public boolean registerHandleAllHandlerObject(MessageHandler handler) {
		if(this.handleAllHandlers.contains(handler)) return false
		this.handleAllHandlers.add(handler)
		return true
	}
	
	public boolean unregisterHandleAllHandler(MessageHandler handler) {
		return this.handleAllHandlers.remove(handler)
	}
	
	public List<MessageHandler> listHandleAllHandlers() {
		return new ArrayList<MessageHandler>(this.handleAllHandlers)
	}
	
		
}
