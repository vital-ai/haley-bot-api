package ai.haley.agent.builder

import com.vitalai.aimp.domain.AnswerMessage;

import ai.haley.agent.api.DialogMode
import ai.haley.agent.api.InteractionMode;
import ai.haley.agent.domain.DialogElement;;

class Dialog {

	DialogMode mode
	
	InteractionMode interactionMode
	
	/**
	 * The default implementation switches to {@link ai.haley.agent.api.DialogMode.search} search mode.
	 * Params: AgentContext context, AnswerMessage msg 
	 */
	Closure onClose = null
	
	/**
	 * Called when the dialog is being destroyed ( ended or restarted)  
	 * Params: AgentContext context
	 */
	Closure cleanup = null 
	
	List<DialogElement> dialogElements = []
	
	public void add(DialogElement el) {
		dialogElements.add(el)
	}
	
	public void addAll(List<DialogElement> els) {
		dialogElements.addAll(els)
	}
	
}
