package ai.haley.agent.api

import java.util.Map;

import ai.vital.vitalservice.VitalService
import ai.vital.vitalservice.VitalStatus
import ai.vital.vitalservice.query.ResultList
import ai.vital.vitalservice.query.VitalQuery
import ai.vital.vitalsigns.model.GraphObject
import ai.vital.vitalsigns.model.VitalApp
import ai.vital.vitalsigns.model.VitalSegment
import ai.vital.vitalsigns.model.property.URIProperty


interface IHaleyAgent {

	public VitalSegment getBaseSegment()
	
	public void shutdown()
	
	public VitalApp getApp()
	
	public ResultList query(VitalQuery q)
	
	public ResultList queryLocal(VitalQuery q)
	
	public ResultList save(VitalSegment segment, List<GraphObject> objectsToSave)
	
	public VitalSegment getSegment(String segment)
	
	public VitalStatus delete(List<URIProperty> objectsToDelete)
	
	//always use local script interface ?
	public ResultList callFunction(String functionName, Map<String, Object> params)
	
	public GraphObject getObject(URIProperty objectURI)
	
	public ResultList getExpanded(GraphObject g)
	
	public Class<? extends LocalDatascript> getLocalDatascript(String functionName)
	
	public Class<? extends LocalDatascript> registerLocalDatascript(String functionName, Class<? extends LocalDatascript> scriptClass)
	
	public Class<? extends LocalDatascript> unregisterLocalDatascript(String functionName)
	
	//get external vitalservice by name
	public VitalService getVitalService(String name)
	
	/**
	 * Returns master service name if runnning in prime agent instance or <code>null</code> if in prime master
	 */
	public String getMasterServiceName()
	
}
