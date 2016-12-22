package ai.haley.agent.domain

import groovy.lang.Closure;

class DialogPageText extends DialogElement {

	//by default all buttons are available
	//DialogPageText, AgentContext context ->
	Closure available = null
	
	//must return AIMP DialogTextCard , params: DialogPageText button(this), AgentContext context
	Closure text
	
	Double index = null
	
	boolean sent = false
	
	@Override
	public Object copy() {
		DialogPageText b = new DialogPageText()
		b.available = available
		b.text = text
		b.id = id
		b.index = index
		b.revert = revert
		b.state = new HashMap<String, Object>(state)
		return b
	}

	
}
