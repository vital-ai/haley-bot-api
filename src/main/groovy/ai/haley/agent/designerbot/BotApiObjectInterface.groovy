package ai.haley.agent.designerbot

import java.security.AccessController
import java.security.PrivilegedAction
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher
import java.util.regex.Pattern

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import ai.haley.agent.api.AgentContext
import ai.haley.agent.api.ChannelDetails
import ai.haley.agent.api.ChannelSession;
import ai.haley.agent.api.ChatRulesEngineResponse
import ai.haley.agent.api.FactScope
import ai.haley.agent.api.InterAccountChannel

//import ai.haley.agent.impl.AgentContextImpl
//import ai.haley.agent.impl.LoginsProfilesAccess
//import ai.haley.agent.utils.QueryUtils
import ai.haley.workflow.api.WorkflowAPI
//import ai.haley.workflow.api.WorkflowAPIFactory

import ai.vital.domain.Account
import ai.vital.domain.FileNode
import ai.vital.domain.FileNode_PropertiesHelper
import ai.vital.domain.Login
import ai.vital.query.querybuilder.VitalBuilder
import ai.vital.vitalservice.VitalStatus
import ai.vital.vitalservice.query.ResultList
import ai.vital.vitalsigns.model.GraphObject
import ai.vital.vitalsigns.model.property.URIProperty

import com.vitalai.aimp.domain.AIMPMessage
import com.vitalai.aimp.domain.AccountAction;
import com.vitalai.aimp.domain.AccountInteractionPart;
import com.vitalai.aimp.domain.BaseProfile
import com.vitalai.aimp.domain.Channel
import com.vitalai.aimp.domain.Edge_hasRelationship;
import com.vitalai.aimp.domain.Endpoint
import com.vitalai.aimp.domain.EndpointProfile
import com.vitalai.aimp.domain.Entity
import com.vitalai.aimp.domain.EntityProperty
import com.vitalai.aimp.domain.EntitySet
import com.vitalai.aimp.domain.RelationshipProperty
import com.vitalai.aimp.domain.RelationshipSet
import com.vitalai.aimp.domain.Session
import com.vitalai.aimp.domain.UserTextMessage

interface BotApiObjectInterface {

	
	static class LoginWithChannel {
		
		Login login
		
		Channel channel

		public LoginWithChannel(Login login, Channel channel) {
			super();
			this.login = login;
			this.channel = channel;
		}
		
		@Override
		public String toString() {
			return "{login: ${login} / channel: ${channel}}"
		}
		
	}
	
	//private static VitalBuilder builder = new VitalBuilder()
	
	//private AgentContext context
	
	//private List<GraphObject> inputMessage
	
	//private final static Logger log = LoggerFactory.getLogger(BotApiObject.class)

	//private LoginsProfilesAccess loginsProfilesAccessObject

	//public BotApiObject(AgentContext context, List<GraphObject> inputMessage)
	
	public List<String> getDialogNames() 
	
	public String getDialogName() 

	//protected LoginsProfilesAccess getLoginsProfilesAccess() 
	
	
	/**
	 * Returns public fileNode URL or @{link BotApiObject#NOT_FOUND_URL} constant
	 * @param fileName
	 * @return
	 */
	String selectResource(String fileName) 
	
	FileNode selectPublicFileNode(String fileName) 
	
	FileNode selectPrivateFileNode(String fileName) 
	
	
	byte[] getFileNodeContents(FileNode fileNode) 
	
	InputStream getFileNodeContentsStream(FileNode fileNode) 
	
	
	static class MessageHistory {
		
		List<Channel> channels = []
		
		Endpoint endpoint = null
		
	}
	
	/**
	 * Returns current message resolved history
	 */
	public MessageHistory getMessageHistory()
	
	//static String s3PublicURL(String fileURL) 

	List<String> listCurrentProfileFactNames(String optionalFilter) 
	
	Collection<String> listProfileFiles(String profileURI, optionalFilter) 
	
	/**
	 * @return full list of all available entity sets (global + current account)
	 */
	List<EntitySet> getEntitySets()

	/**
	 * @return an entity set (global or  current account) or <code>null</code> if not found
	 */
	EntitySet getEntitySetByName(String entitySetName) 
	
	/**
	 * @return entity set properties list or throws exception if entity set not found or access forbidden
	 */
	List<EntityProperty> getEntitySetProperties(String entitySetURI)
		
	
	/**
	 * Wraps an entity with name->propertyURI layer
	 */
	EntityWrapper wrapEntity(Entity entity) 
	
	/**
	 * Create new entity with name->propertyURI layer
	 */
	EntityWrapper createNewWrappedEntity(String entitySetName) 
	
	
	/**
	 * Lists current account logins
	 * @param loginTypeFilter required, determines profiles path
	 * @param regexFilter optional
	 * @param offset non negative number
	 * @param limit 1-1000
	 * @return
	 */
	public List<Login> listLogins(Class<? extends Login> loginTypeFilter, String regexFilter, int offset, int limit) 
	

	/**
	 * Lists current account logins
	 * @param loginTypeFilter required, determines profiles path
	 * @param regexFilter optional
	 * @param factsQueryFilter, a closure that is called with VitalGraphArcContainer factArc
	 * @param offset non negative number
	 * @param limit 1-1000
	 * @return
	 */
	public List<Login> listLoginsWithProfileFactsFilter(Class<? extends Login> loginTypeFilter, String regexFilter, Closure factsQueryFilter, int offset, int limit) 
	
	/**
	 * Lists current account endpoint profiles
	 * @param loginTypeFilter required, determines profiles path
	 * @param loginRegexFilter optional
	 * @param endpointProfileTypeFilter optional
	 * @param offset non negative number
	 * @param limit 1-1000
	 * @return
	 */
	public List<EndpointProfile> listEndpointProfiles(Class<? extends Login> loginTypeFilter, String loginRegexFilter, Class<? extends EndpointProfile> endpointProfileTypeFilter, int offset, int limit) 
	
	/**
	 * Lists current account profiles
	 * @param loginTypeFilter required, determines profiles path
	 * @param regexFilter optional
	 * @param factsQueryFilter, a closure that is called with VitalGraphArcContainer factArc
	 * @param offset non negative number
	 * @param limit 1-1000
	 * @return
	 */
	public List<BaseProfile> listProfiles(Class<? extends Login> loginTypeFilter, String regexFilter, Closure factsQueryFilter, int offset, int limit)
	
	
	/**
	 * Lists current account [login,channel] pairs
	 * Lists current account profiles
	 * @param loginTypeFilter required, determines profiles path
	 * @param regexFilter optional
	 * @param factsQueryFilter, a closure that is called with VitalGraphArcContainer factArc
	 * @param offset non negative number
	 * @param limit 1-1000
	 * @return
	 */
	public List<LoginWithChannel> listLoginWithChannelsWithProfileFactsFilter(Class<? extends Login> loginTypeFilter, String regexFilter, Closure factsQueryFilter, int offset, int limit)
		
	/**
	 * Calls chat rules engine (if enabled) and returns output intent
	 */
	public String callChatRulesEngine(String inputIntent) 

	
	public String getAccountURI() 
	
	public Login getLogin() 
	
	public Session getSession() 
	
	public List<ChannelSession> getSessions() 
	
	public boolean kickSessionByID(String userID, String sessionID)

	public boolean kickSession(ChannelSession channelSession) 
	
	public boolean kickCurrentSession() 
	
	public String getLoginUsername() 
	
	/**
	 * Returns true if this channel is a child channel
	 * @return
	 */
	public boolean isChildChannel() 
	
	public ChannelDetails getChannelDetails() 
	
	public List<ChannelDetails> listChildrenChannels() 
	
	public ChannelDetails spawnChildChannel(ChannelDetails newChannelDetails)
	
	public ChannelDetails killChildChannel(String channelName) 
	
	/**
	 * only available in child channels. It sends a notification message and closes the channel immediately
	 * NotificationMessage
	 */
	public void killThisChildChannel() 

	
	public void registerForRealtimeticksNotification() 

	public void unregisterFromRealtimeticksNotification() 

	public WorkflowAPI getWorkflowAPI()
	
	public FileNode getFileNode(String fileNodeURI)

	public boolean deleteFileNode(String fileNodeURI)
	
	public FileNode createFileNodeWithContents(FileNode fileNode, byte[] contents)
		
	
	public FileNode createFileNodeFromBlob(FileNode fileNode, String blobPath, boolean deleteOnSuccess)
	
	public FileNode createFileNodeFromFileNode(FileNode newFileNode, String sourceFileNodeURI)
		
	
	public List<GraphObject> unpackGraphObjects(List<String> bindingNames, List<GraphObject> objects)
	

	/**
	 * Returns list of inter accounts channels
	 * @param queryTerm (optional)
	 * @param offset
	 * @param limit (max 100)
	 * @return
	 */
	public List<InterAccountChannel> searchInterMessagingAccounts(String queryTerm, int offset, int limit)
	
	public List<InterAccountChannel> getInterMessagingAccounts(Collection<String> accountURIs)

	
	/**
	 * Sends an interaction to another account, sharing all objects in the payload
	 * @param recipientAccountURI
	 * @param accountAction
	 * @param payload
	 * @return result list with status. On success result list contains new AccountInteractionPart
	 */
	public ResultList sendAccountInteraction(String recipientAccountURI, AccountAction accountAction, Collection<GraphObject> payload)
	
	/**
	 * Forwards existing interaction to another account, sharing account action and all objects in the payload.
	 * Account action is retrieved
	 * @param recipientAccountURI
	 * @param accountInteractionPart
	 * @param payload
	 * @return result list with status. On success result list contains new AccountInteractionPart
	 */
	public ResultList forwardAccountInteraction(String recipientAccountURI, AccountInteractionPart accountInteractionPart, Collection<GraphObject> payload)
}

