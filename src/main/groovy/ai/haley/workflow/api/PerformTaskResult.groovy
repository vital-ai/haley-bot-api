package ai.haley.workflow.api

import com.vitalai.haley.domain.HaleyTask;

class PerformTaskResult {

	boolean success
	
	HaleyTask task
	
	//implementation specific
	String childChannelURI
	
}
