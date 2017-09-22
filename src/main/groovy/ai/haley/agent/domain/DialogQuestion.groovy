package ai.haley.agent.domain

import com.vitalai.aimp.domain.Choice
import com.vitalai.aimp.domain.PropertyFact
import com.vitalai.aimp.domain.Question
import com.vitalai.aimp.domain.QuestionMessage
import com.vitalai.aimp.domain.UnsetFactMessage

import ai.haley.agent.api.AgentContext
import ai.haley.agent.api.FactScope
import ai.haley.agent.builder.BotBuilder;
import ai.vital.domain.VITAL_Fact;
import ai.vital.vitalsigns.model.GraphObject;
import ai.vital.vitalsigns.model.VitalApp
import ai.vital.vitalsigns.model.property.URIProperty


class DialogQuestion extends DialogElement {
	
	DialogQuestion() {
		//called when a question is reverted (go back requested)
		//by default all associated facts are removed, closure returns nothing
		revert = { DialogQuestion thisQuestion, AgentContext context ->
			
			
			context.removeFactsByQuestion(thisQuestion)

			/*
			if(thisQuestion.factsURIs.size() == 0) return
						
			DialogInnerMessage innerMsg = new DialogInnerMessage()
			innerMsg.message = [ new UnsetFactMessage().generateURI((VitalApp) null) ]
			
			for(String factURI : thisQuestion.factsURIs) {
				innerMsg.message.add(new VITAL_Fact(URI: factURI))
			}
			
			context.dialogState.queue.putOnTop(innerMsg)
			*/
			
		}
	}
	
	//transient parent questions
	DialogQuestion parentQuestion
	
	//set when the question is sent to the user
	boolean sent = false
	
	//property name
	Class<? extends PropertyFact> factClass

	//if set the facts wil be associated with this object rather than dialog session,
	// the parent object must exist in the facts container
	String parentFactURI
	
	String factPropertyName

	//by default ephemeral
	FactScope factScope = FactScope.dialog
	
	
	// validation type to be applied to given question answer, integer fact questions by default will get 'integer', double fact questions 'double'
	// but a question may set more specific validator such as 'currency' etc 
	String validationType
	
	//question is now a closure, params: DialogQuestion questionData(this), AgentContext context 
	Closure question

	List<Choice> choices = []

	boolean multipleFacts = false
	
	//if help was already requested
	boolean helpRequested = false
	
	//the message was skipped in the flow
	boolean skipped = false

	//a fact for this question was created etc
	boolean handled = false
	
	Double index = null
	
	//by default all questions are available
	Closure available = { DialogQuestion questionData, AgentContext context ->
		return true
	}
	
	//set when message is to be sent to other channel URIs as well, by default this channel only
	boolean sendToThisChannel = true
	List<String> otherChannelURIs = []
	
	//keeps the facts URIs that should be removed when reverting this question
	Set<String> factsURIs = new HashSet<String>()
	
	//list of answers to this question
	List<List<GraphObject>> answers = []
	
	//process answer, if returns true no more processing is necessary, returning false means a new question should be popped
	
	//	returning true means that it’s not expecting any more messages to arrive and that it’s “done” and can be removed from the dialog queue
	// returning false means no more processing should take place

	//implementation injected
	Closure defaultProcessMessage = BotBuilder.defaultProcessMessage//{ DialogQuestion questionData, AgentContext context, List<GraphObject> answerObjects ->

//	}
	
	//custom implementation placeholder
	Closure processMessage = null/*{ DialogQuestion questionData, AgentContext context, List<GraphObject> answerObjects ->
	
		return defaultProcessMessageDefaultQuestionAnswerHandler.processMessage(questionData, context, answerObjects)
		
	}*/
	
	/**
	 * optional text answer processor - applied to text question / replies only
	 * may transform the input text message
	 * should either return string directly (may be altered) or {@link ai.haley.agent.api.TextProcessorResult} 
	 */
	Closure textResponseProcessor = null
	
	
	boolean generated = false
	
	
	DialogQuestion copy() {
		
		DialogQuestion q = new DialogQuestion()
		q.available = available
		q.choices = choices
		q.factClass = factClass
		q.factPropertyName = factPropertyName
		q.factScope = factScope
//		q.factsURIs = factsURIs
		q.generated = generated
		q.id = id
		q.index = index
//		q.helpRequested = helpRequested
		q.multipleFacts = multipleFacts
		q.parentFactURI = parentFactURI
		q.processMessage = processMessage
		q.question = question
		q.revert = revert
//		q.sent = sent
//		q.skipped = skipped
		q.state = new HashMap<String, Object>(state)
		q.textResponseProcessor = textResponseProcessor
		q.validationType = validationType
		
		//runtime
//		q.sendToThisChannel = sendToThisChannel
//		q.otherChannelURIs = otherChannelURI
		
		return q	
		
	}

	@Override
	protected Class<? extends GraphObject> getURIClass() {
		return QuestionMessage.class
	}
	
	
	
}