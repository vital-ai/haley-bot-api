package ai.haley.agent.api

import com.vitalai.aimp.domain.Bot
import com.vitalai.aimp.domain.Channel
import com.vitalai.aimp.domain.Edge_hasChannelBot
import com.vitalai.aimp.domain.Edge_hasChildChannel

class ChannelDetails {

	//set if child
	Channel parentChannel
	
	//set if child
	Edge_hasChildChannel parentChannelEdge
	
	Channel channel
	
	Bot defaultBot
	
	List<Edge_hasChannelBot> channelBotEdges = []
	
	List<Bot> bots = []
	
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
			"bots: ${botsString}";
		
		return s
			
	}
	
	
}
