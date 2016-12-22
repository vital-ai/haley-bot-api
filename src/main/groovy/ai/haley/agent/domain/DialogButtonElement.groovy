package ai.haley.agent.domain

import groovy.lang.Closure;

class DialogButtonElement extends DialogElement {

	//by default all buttons are available
	//DialogButton, AgentContext context ->
	Closure available = null
	
	//must return AIMP DialogButton, params: DialogButton button(this), AgentContext context
	Closure button
	
	Double index = null
	
	boolean sent = false
	
	@Override
	public Object copy() {
		DialogButtonElement b = new DialogButtonElement()
		b.available = available
		b.button = button
		b.id = id
		b.index = index
		b.revert = revert
		b.state = new HashMap<String, Object>(state)
		return b
	}

	
	
}
