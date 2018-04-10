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
		
		revert = { DialogChatbotRules thisDialogChatbotRules, AgentContext context ->
			
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
	Closure processMessage = null/*{ DialogChatbotRules questionData, AgentContext context, List<GraphObject> answerObjects ->
	
		return defaultProcessMessageDefaultQuestionAnswerHandler.processMessage(questionData, context, answerObjects)
		
	}*/
	
	DialogChatbotRules copy() {
		
		DialogChatbotRules r = new DialogChatbotRules()
		r.available = available
		r.factClass = factClass
		r.factPropertyName = factPropertyName
		r.factScope = factScope
//		q.factsURIs = factsURIs
		r.id = id
//		q.helpRequested = helpRequested
		r.multipleFacts = multipleFacts
		r.processMessage = processMessage
		r.revert = revert
		r.state = state
		return r
		
	}

	@Override
	protected Class<? extends GraphObject> getURIClass() {
		return DialogChatbotRules.class
	}

}
