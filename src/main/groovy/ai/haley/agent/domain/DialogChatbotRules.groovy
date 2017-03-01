package ai.haley.agent.domain

import java.util.List;
import java.util.Set;

import com.vitalai.aimp.domain.Choice;
import com.vitalai.aimp.domain.PropertyFact;

import ai.haley.agent.api.AgentContext
import ai.haley.agent.api.FactScope;
import ai.vital.vitalsigns.model.GraphObject;
import groovy.lang.Closure;

class DialogChatbotRules extends DialogElement {

	
	DialogChatbotRules() {
		
		revert = { DialogQuestion thisQuestion, AgentContext context ->
			
			context.removeFacts(this.factScope, factPropertyName)

		}

		
	}
	
	//property name
	Class<? extends PropertyFact> factClass

	String factPropertyName

	//by default ephemeral
	FactScope factScope = FactScope.dialog
	
	boolean multipleFacts = false
	
	//by default all chatbot rules are available
	Closure available = { DialogChatbotRules questionData, AgentContext context ->
		return true
	}
	
	//custom implementation placeholder
	Closure processMessage = null/*{ DialogQuestion questionData, AgentContext context, List<GraphObject> answerObjects ->
	
		return defaultProcessMessageDefaultQuestionAnswerHandler.processMessage(questionData, context, answerObjects)
		
	}*/
	
	DialogQuestion copy() {
		
		DialogQuestion q = new DialogQuestion()
		q.available = available
		q.factClass = factClass
		q.factPropertyName = factPropertyName
		q.factScope = factScope
//		q.factsURIs = factsURIs
		q.id = id
//		q.helpRequested = helpRequested
		q.multipleFacts = multipleFacts
		q.processMessage = processMessage
		q.revert = revert
//		q.sent = sent
//		q.skipped = skipped
		q.state = new HashMap<String, Object>(state)
		return q	
		
	}

	@Override
	protected Class<? extends GraphObject> getURIClass() {
		return DialogChatbotRules.class
	}

}
