package ai.haley.agent.domain

import java.text.DecimalFormat;

import ai.haley.agent.api.AgentContext
import ai.vital.vitalsigns.model.GraphObject
import ai.vital.vitalsigns.model.VitalApp
import ai.vital.vitalsigns.uri.URIGenerator
import groovy.lang.Closure;;;

abstract class DialogElement {

	static Random random = new Random()
	
	static DecimalFormat df = new DecimalFormat('00000')
	
	public DialogElement() {
		generateURI()
	}
	
	public void generateURI() {
		Class<? extends GraphObject> c = this.getURIClass()
		if(c != null) {
			this.URI = URIGenerator.generateURI((VitalApp) null, c)
		} else {
			this.URI = 'urn:' + this.getClass().getSimpleName() + "-" + System.currentTimeMillis() + df.format(random.nextInt(100000))
		}
	}
	
	//each element gets own URI, some elements may be tied to graph object class thus URI 
	//should be created with a generator
	//unique per element instance
	String URI
	
	//shared between all elements of the same kind, question definition etc
	String id
	
	//called when element must revert to the state before it was handler
	//a question removes assigned facts etc
	//{ DialogQuestion thisQuestion, AgentContext context ->
	Closure revert = null

	
	/**
	 * Stores arbitrary dialog element state. Useful when an element is to be reprocessed. 
	 */
	Map<String, Object> state = [:]
	
	abstract Object copy()

	//by default it would generate URI in form urn:classname-random
	protected Class<? extends GraphObject> getURIClass() {
		return null
	}
			
}
