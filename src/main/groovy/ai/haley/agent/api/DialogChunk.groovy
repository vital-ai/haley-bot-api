package ai.haley.agent.api

import ai.haley.agent.domain.DialogElement
import ai.haley.agent.domain.DialogQuestion;
import ai.haley.agent.domain.DialogQuestionEnd
import ai.haley.agent.domain.DialogQuestionStart;;

class DialogChunk extends ArrayList<DialogElement> {

	public void validate() {
		
		if(this.size() == 0) throw new Exception("Empty chunk")
		
		if(! ( this.get(0) instanceof DialogQuestionStart)) throw new Exception("DialogChunk must start with DialogQuestionStart")
		
		if(! ( this.get(this.size() - 1) instanceof DialogQuestionEnd)) throw new Exception("DialogChunk must end with DialogQuestionEnd")
		
		int q = 0
		for(DialogElement el : this) {
			if(el instanceof DialogQuestion) q++
		}
		
		if(q == 0) throw new Exception("No DialogQuestion in DialogChunk")
		if(q > 1) throw new Exception("Exactly one DialogQuestion in DialogChunk expected, got: ${q}")
		
	}
	
}
