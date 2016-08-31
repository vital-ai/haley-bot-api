package ai.haley.agent.domain

import ai.haley.agent.api.AgentContext
import groovy.lang.Closure;


/**
 * Demarcates the end of questions in dialog page batch
 * 
 *  
 * start-page
 * question1
 * question2
 * ...
 * questionN
 * end-page-questions
 * datascript-call
 * query-call-insert
 * end-page
 * dialog-generator
 * 
 * @author Derek
 *
 */
class DialogPageQuestionsEnd extends DialogElement {

	//by default all dialog end elements are available
	Closure available = { DialogPageQuestionsEnd thisElement, AgentContext context ->
		return true
	}
	
	@Override
	public Object copy() {
		DialogPageQuestionsEnd  d = new DialogPageQuestionsEnd()
		d.available = available
		d.id = id
		d.revert = revert
		d.state = new HashMap<String, Object>(state)
		return d;
	}

	

}
