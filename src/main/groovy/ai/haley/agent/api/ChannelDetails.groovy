package ai.haley.agent.api

import com.vitalai.aimp.domain.Bot
import com.vitalai.aimp.domain.Channel
import com.vitalai.aimp.domain.Edge_hasChannelBot
import com.vitalai.aimp.domain.Edge_hasChildChannel

class ChannelDetails {

	/**
	 * ThreadURI to be set for all sent aimp messages.
	 * Default: empty
	 */
	public final static String FACT_defaultThreadURI = 'defaultThreadURI'
	
	/**
	 * When set child channel sends messages to parent channel URI (URI)
	 * Default: true
	 */
	public final static String FACT_defaultChannelParent = 'defaultChannelParent'
	
	/**
	 * When set child channel send messages to output queue only (broadcast)  
	 * Default: true
	 */
	public final static String FACT_defaultBroadcastOnly = 'defaultBroadcastOnly'
	
	/**
	 * Send messages to parent internally only for processing (if child channel).
	 * When enabled it overrides FACT_defaultChannelParent and FACT_defaultBroadcastOnly  
	 * Default: false
	 */
	public final static String FACT_directToParent = 'directToParent'
	
	
	//set if child
	Channel parentChannel
	
	//set if child
	Edge_hasChildChannel parentChannelEdge
	
	Channel channel
	
	Bot defaultBot
	
	List<Edge_hasChannelBot> channelBotEdges = []
	
	List<Bot> bots = []
	
	//initialization option
	//the settings are controlled with channel facts
	String defaultThreadURI = null
	Boolean defaultChannelParent = true
	Boolean defaultBroadcastOnly = true
	Boolean directToParent = false
	
	@Override
	public String toString() {
		
		String botsString = "[${bots.size}]"
		boolean f = true
		for(Bot b : bots) {
			botsString += ( (f ? " " : ", ") + " ${b.name} ${b.URI}" )
			if(b == defaultBot) {
				botsString += "(DEFAULT)"
			}
			f = false
		}
		
		String s = "ChannelDetails - name: ${channel.name} URI: ${channel.URI} ${parentChannel != null ? '[CHILD]' : '[PARENT]'}" +
			(parentChannel ? ( "parent channel name: " + parentChannel.name + " URI: " + parentChannel.URI ) : "") + " " +
			"bots: ${botsString} defaultThreadURI: ${defaultThreadURI} defaultChannelParent: ${defaultChannelParent} " + 
			"defaultBroadcastOnly: ${defaultBroadcastOnly} directToParent: ${directToParent}";
		
		return s
			
	}
	
	
}
