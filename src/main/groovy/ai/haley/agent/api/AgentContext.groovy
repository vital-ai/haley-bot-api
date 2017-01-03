package ai.haley.agent.api

import ai.haley.agent.domain.DialogElement
import ai.haley.agent.domain.DialogPageStart
import ai.haley.agent.domain.DialogQuestion
import ai.vital.domain.VITAL_Fact
import ai.vital.vitalservice.VitalStatus
import ai.vital.vitalservice.query.ResultList
import ai.vital.vitalsigns.model.GraphObject
import ai.vital.vitalsigns.model.VITAL_Container

import com.vitalai.aimp.domain.AIMPMessage
import com.vitalai.aimp.domain.BaseProfile
import com.vitalai.aimp.domain.BooleanPropertyFact
import com.vitalai.aimp.domain.Channel
import com.vitalai.aimp.domain.CurrentBotMessage
import com.vitalai.aimp.domain.DialogStatusMessage
import com.vitalai.aimp.domain.DoublePropertyFact
import com.vitalai.aimp.domain.HaleyTextMessage
import com.vitalai.aimp.domain.IntegerPropertyFact
import com.vitalai.aimp.domain.ProcessorRequestMessage
import com.vitalai.aimp.domain.PropertyFact
import com.vitalai.aimp.domain.Session
import com.vitalai.aimp.domain.StringPropertyFact
import com.vitalai.aimp.domain.TruthPropertyFact
import com.vitalai.aimp.domain.UnsetFactMessage

interface AgentContext {

	BaseProfile getProfile()

	Session getSession()
	
	Channel getChannel()
	
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
	
	public AIMPMessage sendGenericMessage(AIMPMessage input, AIMPMessage output, GraphObject... payload)	
	
	
//	public List<GraphObject> getFactsGraph(FactScope scope) {
//		return new ArrayList<GraphObject>( _getContainer(scope).factsGraph.getAllObjects() )	
//	}
	
	public void sendQuestion(DialogQuestion nextQuestion, AIMPMessage msg, String questionTextPrefix)
	
	public void sendDialogPageQuestion(DialogPageStart dialogPageStart, DialogQuestion nextQuestion, AIMPMessage msg, String questionTextPrefix)
	
	public void sendDialogPageElement(DialogPageStart dialogPageStart, DialogElement element, AIMPMessage inputMsg)
	
	
	public int removeFacts(FactScope scope, String factName)
	
//	public FactsContainer getFactsContainer(FactScope scope)
	
	public VITAL_Container getFactsContainerView(FactScope scope)	
	
	public Set<String> setFact(FactScope scope, DialogQuestion sourceQuestion, String answer)
	
	public Set<String> setFact(FactScope scope, String parentFactURI, Class<? extends PropertyFact> factClass, String propertyName, Object val, boolean multivalue)

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
	
	public StringPropertyFact getStringFactForFact(FactScope scope, String parentURI, String factName)
	
	public BooleanPropertyFact getBooleanFact(FactScope scope, String factName)
	
	public BooleanPropertyFact getBooleanFactForFact(FactScope scope, String parentURI, String factName) 
	
	public IntegerPropertyFact getIntegerFact(FactScope scope, String factName)
	
	public IntegerPropertyFact getIntegerFactForFact(FactScope scope, String parentURI, String factName)
	
	public List<IntegerPropertyFact> getIntegerFactsList(FactScope scope, String factName)
	
	
	public DoublePropertyFact getDoubleFact(FactScope scope, String factName)
	
	public DoublePropertyFact getDoubleFactForFact(FactScope scope, String parentURI, String factName)

	public TruthPropertyFact getTruthFact(FactScope scope, String factName)
	
	public TruthPropertyFact getTruthFactForFact(FactScope scope, String parentURI, String factName)
	
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

	VITAL_Fact addGenericFactObject(FactScope scope, GraphObject factParent, VITAL_Fact childFact)

	
	/**
	 * Resolves a resultlist that's stored in a fact
	 * @param scope
	 * @param resultListFactURI
	 * @return ResultList or null if not found
	 */
	ResultList getResultList(FactScope scope, String resultListFactURI)

	
	/**
	 * Resolves solution objects
	 * @param scope
	 * @param solutionFactURI
	 * @return List<GraphObject> of this solution of null if not found
	 */
	List<GraphObject> getSolutionObjects(FactScope scope, String solutionFactURI)
	
	
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
		
}
