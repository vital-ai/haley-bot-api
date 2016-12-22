package ai.haley.agent.domain

import ai.haley.agent.api.AgentContext
import groovy.lang.Closure;

/**
 * Demarcates a dialog page start
 * 
 * start-page
 * question1
 * question2
 * ...
 * questionN
 * or dialogpagetext or dialogbuttonelement 
 * end-page-questions
 * datascript-call
 * query-call-insert
 * end-page
 * dialog-generator
 *  
 *  special pages (goodbyePage=true) should not have any question elements as the dialog is meant to be over at this point 
 *  
 * @author Derek
 *
 */
class DialogPageStart extends DialogElement {

	int page
	
	int total
	
	boolean goodbyePage = false
	
	String title
	
	//by default all dialog end elements are available
	Closure available = { DialogPageStart thisElement, AgentContext context ->
		return true
	}
	
	//checks whether the page is complete and next page may be fetched
	//returns error string or null
	Closure validate = { DialogPageStart thisElement, AgentContext context ->
		return null
	}
	
	@Override
	public Object copy() {
		DialogPageStart d = new DialogPageStart()
		d.available = available
		d.goodbyePage = goodbyePage
		d.id = id
		d.page = page
		d.revert = revert
		d.state = new HashMap<String, Object>(state)
		d.title = title
		d.total = total
		d.validate = validate
		return d;
	}

	
}
