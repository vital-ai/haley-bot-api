package ai.haley.agent.builder

import ai.haley.agent.api.DialogMode;
import ai.haley.agent.domain.DialogElement;;

class Dialog {

	DialogMode mode
	
	List<DialogElement> dialogElements = []
	
	public void add(DialogElement el) {
		dialogElements.add(el)
	}
	
	public void addAll(List<DialogElement> els) {
		dialogElements.addAll(els)
	}
	
}
