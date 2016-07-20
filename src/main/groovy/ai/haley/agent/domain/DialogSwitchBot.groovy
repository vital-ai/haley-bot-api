package ai.haley.agent.domain

import ai.haley.agent.api.AgentContext;

class DialogSwitchBot extends DialogElement {

	//should return the new bot id to, returning null or returning current bot id prevents switching
	Closure switchBot = { DialogSwitchBot thisElement, AgentContext context ->
		
//		context.getAgentInstance().getA
		//shoudl
		return null
		
	}

	@Override
	public Object copy() {
		DialogSwitchBot d = new DialogSwitchBot()
		d.id = id
		d.revert = revert
		d.state = new HashMap<String, Object>(state)
		d.switchBot = switchBot
		return d;
	}

		
}
