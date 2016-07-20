package ai.haley.agent.domain

import ai.vital.vitalsigns.model.GraphObject;

/**
 * contains an inner message to be handled
 * @author Derek
 *
 */
class DialogInnerMessage extends DialogElement {

	List<GraphObject> message
	
	//important!
	boolean processed = false
	
	@Override
	public Object copy() {
		DialogInnerMessage im = new DialogInnerMessage()
		im.id = id
		im.revert = revert
		im.message = message
		im.processed = processed
		im.state = new HashMap<String, Object>(state)
		return im;
	}

}
