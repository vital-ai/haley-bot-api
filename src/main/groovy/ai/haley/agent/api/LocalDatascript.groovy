package ai.haley.agent.api;

import java.util.Map

import ai.vital.vitalservice.query.ResultList;

/**
 * Local interface that is the same as prime groovy script interface.
 * This allows for quick datascripts development without remote prime instance
 * The difference is is lack of script interface 
 * 
 * @author Derek
 *
 */
public interface LocalDatascript {

	/**
	 * @param scriptInterface
	 * @param parameters - the script input parameters
	 */
	public ResultList executeScript( IHaleyAgent agent, Map<String, Object> parameters);
	
}