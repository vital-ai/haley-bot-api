package ai.haley.agent.domain

import ai.haley.agent.api.AgentContext
import groovy.lang.Closure;

/**
 * Demarcates a dialog page end
 * 
 *  
 * @author Derek
 *
 */
class DialogPageEnd extends DialogElement {

	//by default all dialog end elements are available
	Closure available = { DialogPageEnd thisElement, AgentContext context ->
		return true
	}
	
	@Override
	public Object copy() {
		DialogPageEnd d = new DialogPageEnd()
		d.available = available
		d.id = id
		d.revert = revert
		d.state = new HashMap<String, Object>(state)
		return d;
	}

	
}
