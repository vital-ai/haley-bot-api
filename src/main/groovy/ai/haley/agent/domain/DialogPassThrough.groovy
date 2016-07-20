package ai.haley.agent.domain

class DialogPassThrough extends DialogElement {

	@Override
	public Object copy() {
		DialogPassThrough pt = new DialogPassThrough()
		pt.id = id
		pt.revert = revert
		pt.state = new HashMap<String, Object>(state)
		return pt;
	}

	
	
}
