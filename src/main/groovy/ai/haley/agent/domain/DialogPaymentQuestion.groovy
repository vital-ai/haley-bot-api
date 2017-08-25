package ai.haley.agent.domain

import com.vitalai.aimp.domain.QuestionMessage;
import com.vitalai.aimp.domain.StringPropertyFact;

import ai.vital.vitalsigns.model.GraphObject;

/**
 * payment question is a special question type which expects payment token as a response
 * the token is then validated using payment api datascript 
 * @author Derek
 *
 */
class DialogPaymentQuestion extends DialogQuestion {
	
	//public key
	String publishableKey

	PaymentService paymentService

	DialogPaymentQuestion() {
		this.factClass = StringPropertyFact.class
		this.multipleFacts = false
	}
	
	@Override	
	DialogPaymentQuestion copy() {
		
		DialogPaymentQuestion q = new DialogPaymentQuestion()
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
		
		q.publishableKey = publishableKey
		q.paymentService = paymentService
		
		return q
		
	}

	@Override
	protected Class<? extends GraphObject> getURIClass() {
		return QuestionMessage.class
	}
	
}
