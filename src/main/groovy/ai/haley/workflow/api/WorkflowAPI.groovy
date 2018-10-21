package ai.haley.workflow.api

import com.vitalai.aimp.domain.Entity
import com.vitalai.haley.domain.HaleyTask

interface WorkflowAPI {
	
	
	// force release task (data modification queue notification)
	
	
	
	// call workflow-step (used by the dialog-designer "Workflow" step)
	// a workflow-step may directly do something and return success/failure
	// a workflow-step may return dialog-steps which the dialog may
	// then "run" as a subroutine
	
	// in the case of formbot, the workflow may return a list of form elements
	// to send to the user, where an input may be a form event or a tick
	
	// the call-workflow-step method provides access to the reasoning engine such
	// that it can be integrated with dialogs and forms to determine what haley does

	
	/**
	 * inserts a new task into database
	 * The task priority and name have to be set
	 * @param task
	 * @param paramsEntity, only as URI reference, not stored with this object
	 * @param notify if <code>true</code> sends instant notification (realtimetick intent)
	 */
	HaleyTask createTask(HaleyTask task, Entity paramsEntity, String dialogName, boolean notify)
		
	
	/**
	 * delete task by URI, the task must not be locked by another agent
	 */
	boolean deleteTaskByURI(String taskURI)

	HaleyTask getTask(String taskURI)
		
	/**
	 * locks a task
	 * @returns null if locked already by someone else, task object on success / already locked by this channel
	 */
	HaleyTask lockTask(HaleyTask task)
	
	/**
	 * release task
	 * @return updated task if the task was locked and was released, null if not locked
	 * @throws exception when locked by someone else
	 */
	HaleyTask releaseTask(HaleyTask task)
	
	
	
	/**
	 * Performs a task that should be locked already
	 * The task may fail immediately, its status should be checked
	 * @param task
	 * @return result
	 */
	PerformTaskResult performTask(HaleyTask task)
	
	
	/**
	 * Updates task status and status message
	 */
	HaleyTask updateTaskStatus(HaleyTask task)
	
	
	
	/**
	 * list tasks on queue, populates the queue if it wasn't already
	 * @return queue
	 */
	List<HaleyTask> listTasksQueue()
	
	
	/**
	 * Re-populates the queue and returns it
	 * @return queue
	 */
	List<HaleyTask> repopulateQueue()
	
	/**
	 * Purges the queue
	 * @return size of the queue 
	 */
	int purgeQueue()
	

	/**
	 * Adds a new task to the queue. The task priority and name has to be set
	 * @param task
	 * @return true if it was added to the queue, false if queue full and element didn't make it
	 */
	boolean addTaskToQueue(HaleyTask task)
	
	/**
	 * Removes an task from the queue (URI)
	 * @param task
	 * @return
	 */
	boolean removeTaskFromQueue(HaleyTask task)

	
}
