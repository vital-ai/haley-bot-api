package ai.haley.agent.domain

import ai.haley.agent.api.AgentContext
import ai.haley.agent.builder.BotBuilder
import groovy.lang.Closure;;

class DialogGenerator extends DialogElement {

	/**
	 * may be overwritten {DialogGenerator thisElement, AgentContext context, List<GraphObject> inputMessage}
	 * generates dialogs by pushing dialog elements at the top of the queue
	 * if no questions are added then the message is sent to default handlers - out of dialog
	 * default handler pushes all skipped questions and returns true
	 * @return true if the generator is done and should be removed from the queue
	 */
	Closure generateDialog = null

	
	//by default all dialog generators are available
	Closure available = { DialogGenerator thisElement, AgentContext context ->
		return true
	}
		
	@Override
	public Object copy() {
		DialogGenerator dg = new DialogGenerator()
		dg.available = available
		dg.generateDialog = generateDialog
		dg.id = id
		dg.revert = revert
		dg.state = new HashMap<String, Object>(state)
		return dg;
	}

	
	
}
