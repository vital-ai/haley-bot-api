package ai.haley.agent.domain

import ai.haley.agent.api.AgentContext
import groovy.lang.Closure;

class DialogEnd extends DialogElement {

	//by default all dialog end elements are available
	Closure available = { DialogEnd thisElement, AgentContext context ->
		return true
	}
	
	@Override
	public Object copy() {
		DialogEnd d = new DialogEnd()
		d.available = available
		d.id = id
		d.revert = revert
		d.state = new HashMap<String, Object>(state)
		return d;
	}

	
}
