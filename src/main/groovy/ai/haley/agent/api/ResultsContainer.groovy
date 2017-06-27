package ai.haley.agent.api

import ai.vital.vitalsigns.model.GraphObject

/**
 * Used in assign step to create a new ResultList fact
 * @author Derek
 *
 */
class ResultsContainer {

	List<Result> results = []
	
	Integer offset
	
	Integer limit
	
	Integer totalResults
	
	public void add(Result result) {
		results.add(result)
	}
	
	public void add(GraphObject graphObject, Double score) {
		results.add(new Result(graphObject: graphObject, score: score))
	}
	
}
