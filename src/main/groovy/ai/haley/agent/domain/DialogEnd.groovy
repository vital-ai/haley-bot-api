package ai.haley.agent.domain

class DialogEnd extends DialogElement {

	@Override
	public Object copy() {
		DialogEnd d = new DialogEnd()
		d.id = id
		d.revert = revert
		return d;
	}

	
}
