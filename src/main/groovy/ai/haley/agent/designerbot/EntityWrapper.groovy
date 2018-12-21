package ai.haley.agent.designerbot

import com.vitalai.aimp.domain.Entity
import com.vitalai.aimp.domain.EntityProperty;
import com.vitalai.aimp.domain.EntitySet

import ai.haley.agent.api.AgentContext;

class EntityWrapper extends GroovyObjectSupport {

	private List<EntitySet> entitySets
	
	private Entity entity
	
	private Map<String, EntityProperty> propsMap
	public EntityWrapper(List<EntitySet> entitySets, Entity entity, Map<String, EntityProperty> propsMap) {
		super();
		this.entitySets = entitySets;
		this.entity = entity;
		this.propsMap = propsMap
	}
	
	public static EntityWrapper createNewWrappedEntity(AgentContext context, String entitySetName) {
		
		EntitySet es = context.getEntitySetByName(entitySetName)
		if(es == null) throw new Exception("Entity Set not found: " + entitySetName)
		
		Entity entity = new Entity()
		entity.generateURI(context.agentInstance.app)
		entity.entitySetURI = [es.URI]
		
		return createWrapped(context, entity)
		
		
	}
	
	public static EntityWrapper createWrapped(AgentContext context, Entity entity) {
		
		Collection<String> uris = entity.entitySetURI?.rawValue()
		if(uris == null || uris.size() == 0) throw new RuntimeException("Entity does not have entitySetURIs assigned")
		
		
		Map<String, EntitySet> esMap = context.getEntitySetsByURIs(uris)
		
		for(String esURI : uris) {
			if(!esMap.containsKey(esURI)) throw new RuntimeException("Entity set not found: ${esURI}")
		}
		
		Map<String, EntityProperty> propsMap = [:]
		
		for(EntitySet es : esMap.values()) {
			
			List<EntityProperty> props = context.getEntitySetProperties(es.URI)
			
							
			for(EntityProperty ep : props) {
				propsMap.put(ep.name.toString(), ep)
			}
			 
		}
		
		return new EntityWrapper(new ArrayList<EntitySet>(esMap.values()), entity, propsMap)
	}
	
	@Override
	public Object getProperty(String property) {
		
		if(property == 'URI') {
			return entity.URI
		}
		
		EntityProperty ep = propsMap.get(property)
		if(ep != null) {
//			throw new RuntimeException("Entity property not found: ${property}, entitySet: ${entitySets.name}")
			return entity.getProperty(ep.URI)
		} else {
			return entity.getProperty(property)
		}
		
		
	}
	@Override
	public void setProperty(String property, Object newValue) {

		if(property == 'URI') {
			entity.URI = newValue
			return
		}
		
		//check types ?
		EntityProperty ep = propsMap.get(property)
		if(ep != null) {
			entity.setProperty(ep.URI, newValue)
		} else {
			entity.setProperty(property, newValue)
		}
//		throw new RuntimeException("Entity property not found: ${property}, entitySet: ${entitySets.name}")
				
	}
	
	Entity unwrap() {
		return this.entity
	}

	
	List<EntitySet> entitySets() {
		return this.entitySets
	}
		
	
}
