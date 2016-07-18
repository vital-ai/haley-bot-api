package ai.haley.agent.domain

//should be put at the beginning of a dialog 
class DialogBegin extends DialogElement {

	@Override
	public Object copy() {
		DialogBegin b = new DialogBegin()
		b.id = id
		b.revert = revert
		return b
	}

	
}
