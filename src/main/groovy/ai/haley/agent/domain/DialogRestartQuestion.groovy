package ai.haley.agent.domain

import ai.haley.agent.api.AgentContext

class DialogRestartQuestion extends DialogElement {

	//must be set
	String questionID 
	
	//if this closure returns true the dialog should go back to a particular previous question 
	Closure applicable = { DialogRestartQuestion drq, AgentContext context ->
		return false
	}
	
	@Override
	public Object copy() {
		DialogRestartQuestion drq = new DialogRestartQuestion()
		drq.applicable = applicable
		drq.id = id
		drq.questionID = questionID
		return drq;
	}

	
	
}
