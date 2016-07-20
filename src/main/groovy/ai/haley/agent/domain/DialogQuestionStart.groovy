package ai.haley.agent.domain

/**
 * Question start marker. A question block contains exactly one {@link DialolgElemet}
 */
class DialogQuestionStart extends DialogElement {
	
	@Override
	public Object copy() {
		DialogQuestionStart dqs = new DialogQuestionStart()
		dqs.id = id
		dqs.revert = revert
		dqs.state = new HashMap<String, Object>(state)
		return dqs;
	}

}
