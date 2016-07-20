package ai.haley.agent.domain

class DialogEnd extends DialogElement {

	@Override
	public Object copy() {
		DialogEnd d = new DialogEnd()
		d.id = id
		d.revert = revert
		d.state = new HashMap<String, Object>(state)
		return d;
	}

	
}
