package ai.haley.agent.domain

//internal signalling message, necessary when switching to a bot that should init/resume dialog (ask question)
class DialogResume extends DialogElement {

	@Override
	public Object copy() {
		DialogResume ds = new DialogResume()
		ds.id = id
		ds.revert = revert
		ds.state = new HashMap<String, Object>(state)
		return ds;
	}

}
