package ai.haley.agent.api

import ai.haley.agent.domain.DialogElement
import ai.haley.agent.domain.DialogPageStart
import ai.haley.agent.domain.DialogQuestion
import ai.vital.vitalservice.VitalStatus
import ai.vital.vitalservice.query.ResultList
import ai.vital.vitalsigns.model.GraphObject

import com.vitalai.aimp.domain.AIMPMessage
import com.vitalai.aimp.domain.BaseProfile
import com.vitalai.aimp.domain.BooleanPropertyFact
import com.vitalai.aimp.domain.Channel
import com.vitalai.aimp.domain.CurrentBotMessage
import com.vitalai.aimp.domain.DialogStatusMessage
import com.vitalai.aimp.domain.DoublePropertyFact
import com.vitalai.aimp.domain.Endpoint
import com.vitalai.aimp.domain.GraphObjectFact;
import com.vitalai.aimp.domain.HaleyTextMessage
import com.vitalai.aimp.domain.HyperEdge_hasListFactElement
import com.vitalai.aimp.domain.IntegerPropertyFact
import com.vitalai.aimp.domain.ListFact
import com.vitalai.aimp.domain.ProcessorRequestMessage
import com.vitalai.aimp.domain.PropertyFact
import com.vitalai.aimp.domain.Session
import com.vitalai.aimp.domain.StringPropertyFact
import com.vitalai.aimp.domain.ThinkingMessage;
import com.vitalai.aimp.domain.TruthPropertyFact
import com.vitalai.aimp.domain.URIPropertyFact
import com.vitalai.aimp.domain.UnsetFactMessage

interface AgentContext {

	BaseProfile getProfile()

	Session getSession()
	
	Channel getChannel()

	//returns the only endpoint associated with this channel, null if not endpoint if shared endpoint case
	Endpoint getEndpoint() 
	
	//returns endpoint for given uri if associated with this channel
	Endpoint getEndpointForURI(String endpointURI)
	
	//returns all endpoints associated with this channel, or empty list if none
	List<Endpoint> getEndpoints()	
	
	InteractionMode getChannelInteractionMode()
	
	BotSessionState getDialogState()	
	
	//objects to be saved/deleted at the end of request
	
	//do not expose this
//	AgentResponse agentResponse
	
	IHaleyAgent getAgentInstance()
	
	//sends output message as it is
	public VitalStatus sendOutputMessage(List<GraphObject> gs)

	
	public HaleyTextMessage replyWithText(AIMPMessage input, String text)
	
	public HaleyTextMessage sendTextMessage(AIMPMessage input, String text)
	
	public ThinkingMessage sendThinkingMessage(AIMPMessage input)
	
	public AIMPMessage sendGenericMessage(AIMPMessage input, AIMPMessage output, GraphObject... payload)	
	
	
	public void sendQuestion(DialogQuestion nextQuestion, AIMPMessage msg, String questionTextPrefix)
	
	public void sendDialogPageQuestion(DialogPageStart dialogPageStart, DialogQuestion nextQuestion, AIMPMessage msg, String questionTextPrefix)
	
	public void sendDialogPageElement(DialogPageStart dialogPageStart, DialogElement element, AIMPMessage inputMsg)
	
	
	public int removeFacts(FactScope scope, String factName)
	
//	public FactsContainer getFactsContainer(FactScope scope)
	
	public Set<String> setFact(FactScope scope, DialogQuestion sourceQuestion, String answer)
	
	public Set<String> setFact(FactScope scope, String parentFactURI, Class<? extends PropertyFact> factClass, String propertyName, Object val, boolean multivalue)

	
	public Set<String> setListFact(FactScope scope, ListFact listFact, List<GraphObject> objects, Map<String, HyperEdge_hasListFactElement> listHyperEdgesPrototypes)
	
	public Set<String> setGraphObjectFact(FactScope scope, GraphObjectFact fact, GraphObject object)
	
	
	//utility method
	//TODO
//	public Set<String> setFact(FactScope scope, GraphObject parentObject, PropertyFactInfo factInfo, Object value)	
	
	boolean hasFactForQuestion(DialogQuestion question)
	
	//has fact with given name and scope
	boolean hasFact(FactScope scope, String factName)
	
	//has fact with given name, any scope
	boolean hasFact(String factName)
	
	/**
	 * check if a profile fact for given session is already set
	 * @param question
	 * @param overridden scope
	 * @return
	 */
	boolean hasFactForQuestion(DialogQuestion question, FactScope scope)
	
	public StringPropertyFact getStringFact(FactScope scope, String factName)
	
	public BooleanPropertyFact getBooleanFact(FactScope scope, String factName)
	
	public IntegerPropertyFact getIntegerFact(FactScope scope, String factName)
	
	public List<IntegerPropertyFact> getIntegerFactsList(FactScope scope, String factName)
	
	
	public DoublePropertyFact getDoubleFact(FactScope scope, String factName)
	
	public URIPropertyFact getURIFact(FactScope scope, String factName)
	
	public TruthPropertyFact getTruthFact(FactScope scope, String factName)
	
	public PropertyFact getFact(FactScope scope, String factName)
	
	public Object getFactValue(FactScope scope, String factName)
	
	public List<Object> getFactValues(FactScope scope, String factName)
	
	
	//purges profile and session facts
	public void resetProfile(boolean keepbotFact)
	
	public int removeFactByURI(String factURI)
	
	public int removeFactsByMessageConstraints(AgentContext context, UnsetFactMessage msg)
		
	CurrentBotMessage sendCurrentBotMessage(AIMPMessage msg)
		
	DialogStatusMessage sendDialogStatusMessage(AIMPMessage msg, String status)
		
	int removeFactsByQuestion(DialogQuestion question)

	GraphObject getFactsGraphObject(FactScope scope, String objectURI)

	GraphObject getFactGraphRoot(FactScope scope)

	/**
	 * Resolves a resultlist that's stored as a fact
	 * @param scope
	 * @param factName
	 * @return ResultList or null if not found or different fact type 
	 */
	ResultList getResultList(FactScope scope, String factName)

	
	/**
	 * Resolves solution objects
	 * @param scope
	 * @param factName
	 * @return List<GraphObject> of this solution of null if not found
	 */
	List<GraphObject> getSolutionObjects(FactScope scope, String factName)
	
	
	/**
	 * Switches current context to a different bot and sends given messages immediately
	 * Current bot should finish processing current message immediately
	 * @param inputMessage  
	 * @param botName
	 * @param message(s)
	 */
	void switchToBot(AIMPMessage inputMessage, String botName, List<GraphObject>... messages)

	
	/**
	 * Sends a message to be processed internally within the same context
	 */
	void sendInnerMessage(AIMPMessage message, List<GraphObject> payload)

	
	/**
	 * Adds dialog page child question if not exists
	 * @param parentQuestion
	 * @param newChildQquestion
	 * @return
	 */
	boolean addChildQuestion(DialogQuestion parentQuestion, DialogQuestion newChildQquestion)

	/**
	 * Removes page question by id, retracts facts and notifies client 
	 * @param questionID
	 * @return
	 */
	boolean removePageQuestion(String questionID)
	
	
	/**
	 * Send data modification event
	 */
	void sendDataModificationEvent(Class<? extends GraphObject> graphObjectClass, String objectURI)
	
	/**
	 * Sends a processor request message. The input message requestURI is used if provided
	 * @param processor
	 * @param inputMessage
	 * @param request
	 * @param payload
	 */
	void sendProcessorRequest(String processor, AIMPMessage inputMessage, ProcessorRequestMessage request,List<GraphObject> payload)
	
	/**
	 * Returns default botID (name) of current channel
	 * @param defaultBotID 
	 */
	String getDefaultBotID()
	

	/**
	 * A method for cross channel communication. I can be used to to current channel.	
	 * @param channelName
	 * @param input
	 * @param output
	 * @param payload
	 */
	void sendMessageToChannel(String channelName, AIMPMessage input, AIMPMessage output, List<GraphObject> payload)
	
	/**
	 * Shortcut for sending text only messages to a channel 
	 * @param channelName
	 * @param input
	 * @param text
	 */
	void sendTextMessageToChannel(String channelName, AIMPMessage input, String text)

	void restartCurrentBot()
	
	List<BaseProfile> getChannelUsers()

	List<ChannelSession> getChannelSessions()
	
	/**
	 * Closes given session. Returns true if ok, false if session not found
	 * @param session
	 * @return
	 */
	boolean closeChannelSession(ChannelSession session)
	
	/**
	 * Unloads current bot. Returns true if succeeded, false if bot couldn't be unloaded
	 * @return
	 */
	boolean unloadBot()
			
	/**
	 * timestamp when last non-tick message was sent to a channel
	 */
	Long getLastChannelActivityTimestamp()
	
	/**
	 * timestamp when last non-tick message was processed by current bot
	 * may be <code>null</code> if current bot was unloaded
	 */
	Long getLastBotActivityTimestamp()
	

	List<GraphObject> getAllFactObjects(FactScope fs)
	
	Set<String> setPropertyFact(FactScope fs, List<PropertyFact> vals)

	Channel getChannelByName(String channelName)	
}
