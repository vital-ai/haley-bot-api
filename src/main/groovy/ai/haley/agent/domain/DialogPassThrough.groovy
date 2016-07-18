package ai.haley.agent.domain

class DialogPassThrough extends DialogElement {

	@Override
	public Object copy() {
		DialogPassThrough pt = new DialogPassThrough()
		pt.id = id
		pt.revert = revert
		return pt;
	}

	
	
}
