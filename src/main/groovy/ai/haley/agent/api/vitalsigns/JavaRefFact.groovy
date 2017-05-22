package ai.haley.agent.api.vitalsigns

import java.util.List;

import com.vitalai.aimp.domain.PropertyFact
import com.vitalai.aimp.domain.ontology.Ontology;

import ai.vital.vitalsigns.VitalSigns;
import ai.vital.vitalsigns.classes.ClassMetadata
import ai.vital.vitalsigns.model.GraphObject;

/**
 * Special ephemeral fact type for storing java references
 *
 */
class JavaRefFact extends PropertyFact {

	static {
		ClassMetadata parent = VitalSigns.get().getClassesRegistry().getClassMetadata(PropertyFact.class)
		ClassMetadata cm = new ClassMetadata(Ontology.NS + JavaRefFact.class.getSimpleName(), Ontology.ONTOLOGY_IRI, JavaRefFact.class.getSimpleName(), JavaRefFact.class.getCanonicalName(), parent, false, false)
		VitalSigns.get().getClassesRegistry().addClass(parent, cm)
		VitalSigns.get().getPropertiesRegistry().addClass(cm)
	}
	
	//arbitrary 
	Object ref

	@Override
	public Object getProperty(String pname) {

		if(pname == 'ref') {
			return this.ref
		}
		
		return super.getProperty(pname);
	}

	@Override
	public void setProperty(String pname, Object newValue) {
		
		if(pname == 'ref') {
			this.ref = newValue
			return
		}
		
		super.setProperty(pname, newValue);
		
	}
	
	
	
	
}
