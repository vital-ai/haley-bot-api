package ai.haley.agent.api

enum InteractionMode {

	/**
	 * bot will answer to all messages
	 */ 
	all,
	
	/**
	 * bot will answer to direct messages only, non-text answers will be processed as usual 
	 */
	direct

	
	static InteractionMode fromString(String s) {
		if(s == null) throw new Exception("Null interactionMode input string")
		return valueOf(s.toLowerCase())
		
	}
		
}
