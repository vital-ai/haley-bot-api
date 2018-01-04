package ai.haley.agent.api

import ai.vital.domain.Login
import com.vitalai.aimp.domain.BaseProfile;
import com.vitalai.aimp.domain.Edge_hasSessionChannel;
import com.vitalai.aimp.domain.Session;

class ChannelSession {

	private Login login
	
	private BaseProfile profile
	
	private Session session
	
	private Edge_hasSessionChannel sessionChannelEdge
	
	public Session getSession() {
		return this.session
	}
	
	public BaseProfile getProfile() {
		return this.profile
	}
	
	public Login getLogin() {
		return this.login
	} 

	public Long getLastActiveTimestamp() {
		return this.sessionChannelEdge?.lastActivityTime?.longValue()
	}
	
	public Long getLastUserLeftAppTimestamp() {
		return this.session.lastLeftAppMessageTime?.longValue()
	}
	
}
