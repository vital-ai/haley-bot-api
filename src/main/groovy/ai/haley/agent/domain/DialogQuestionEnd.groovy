package ai.haley.agent.domain

/**
 * Question end marker. A question block contains exactly one {@link DialolgElemet}
 */
class DialogQuestionEnd extends DialogElement {

	@Override
	public Object copy() {
		DialogQuestionEnd dqe = new DialogQuestionEnd()
		dqe.id = id
		dqe.revert = revert
		dqe.state = new HashMap<String, Object>(state)
		return dqe;
	}

	
	
}
