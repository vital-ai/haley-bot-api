package ai.haley.agent.domain

import ai.haley.agent.api.AgentContext;
import groovy.lang.Closure;

class DialogText extends DialogElement {

	//by default all text messages are available
	Closure available = { DialogText dialogText, AgentContext context ->
		return true
	}
	
	//should return text
	Closure text = { AgentContext context ->
		"SET ME!"
	}
	
	Object copy() {
		DialogText d = new DialogText()
		d.available = available
		d.id = id
		d.revert = revert
		d.text = text
		return d
	}
	
}
