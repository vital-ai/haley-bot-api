package ai.haley.agent.api

import java.util.List;

import ai.haley.agent.domain.DialogElement
import ai.haley.agent.domain.DialogGenerator
import ai.haley.agent.domain.DialogPageEnd
import ai.haley.agent.domain.DialogPageStart;
import ai.haley.agent.domain.DialogQuestion
import ai.haley.agent.domain.DialogQuestionEnd
import ai.haley.agent.domain.DialogQuestionStart

class DialogHistory {

	private List<DialogElement> queue = new ArrayList()
	
	boolean isEmpty() {
		
		return queue.isEmpty()
		
	}
	
	
	public int size() {
		return queue.size();
	}
	
	DialogElement peekElement() {
		
		
		if( queue.isEmpty() ) {
			
			return null
		}
		
		return queue.get(0)
		
	}
	
	//the only mutable operation
	void putAtEnd(DialogElement q) {
		
		queue.remove(q)
		
		queue.add(q)
		
	}
	
	//get full history stack up to particular question 
	public List<DialogElement> getHistoryQuestionStack(List<String> remainingQuestionIDs, String questionID) {

		List<DialogElement> previousQuestionStack = []
		
		List<DialogElement> results = []
		
		for(int j = queue.size() - 1 ; j >= 0; j--) {
			
			DialogElement el = queue.get(j)
			
			previousQuestionStack.add(0, el)
			
			if(el instanceof DialogQuestion) {
				
				//decision
				if( ! remainingQuestionIDs.contains(el.id) ) {
					//match

					results.addAll(0, previousQuestionStack)
										
					//valid for history but if it ends
					
					if(el.skipped || el.handled) {
						//valid may return results
						
						if(questionID == el.id) {
							return results
						}
						
					}
					
				}
				
				//purge the stack and keep looking
				previousQuestionStack = []
				
			}
			
		}
		
		throw new Exception("Previous question with id: ${questionID} not found")
		
	}
	
	
	public List<DialogElement> getList() {
		return Collections.unmodifiableList(queue);
	}
	
	
	/**
	 * Returns part of history where first element is previous question optional subsequent elements are non-question ones
	 * A previous question is a question with id not contained in input questiond ids 
	 * if no previous question is available the list is empty 
	 * @return
	 */
	public List<DialogElement> getPreviousQuestionStack(List<String> remainingQuestionIDs) {
		
		List<DialogElement> previousQuestionStack = []
		
		List<DialogElement> results = []
		
		boolean insideQMarkers = false 
		
		for(int j = queue.size() - 1 ; j >= 0; j--) {
			
			DialogElement el = queue.get(j)
			
			//do not replicate dialog generators
			if(el instanceof DialogGenerator && el.id != null) {
				if(remainingQuestionIDs.contains(el.id)) {
					continue
				}
			}
			
			previousQuestionStack.add(0, el)
			
			if(el instanceof DialogQuestionEnd) {
				insideQMarkers = true
			}  else if(el instanceof DialogQuestionStart) {
				insideQMarkers = false
			}
			
			if(el instanceof DialogQuestion) {
				
				//decision
				if( ! remainingQuestionIDs.contains(el.id) ) {
					//match

					results.addAll(0, previousQuestionStack)
										
					//valid for history but if it ends
					
					if(el.skipped || el.handled) {
						//valid may return results
						
						if(insideQMarkers) {
							boolean qStartFound = false
							//also scan to DialogQ
							while(!qStartFound) {
								j--
								if(j < 0) throw new Exception("No DialogQuestionBeginElement found")
								el = queue.get(j)
								results.add(0, el)
								if(el instanceof DialogQuestionStart) {
									qStartFound = true
								}
							}
						}
						
						return results
						
					}
					
				}
				
				//purge the stack and keep looking
				previousQuestionStack = []
				
			}
			
		}
		
		return []
		
	}

	/**
	 * Returns current question processed elements, the scan should hit DialogQuestionStart element 
	 * For now is not an error when DialogQuestion or DialogQuestionEnd is spotted 
	 * @return
	 */
	public List<DialogElement> getCurrentQuestionElements() {
		
		List<DialogElement> results = []
		
		for(int j = queue.size() - 1 ; j >= 0; j--) {
			
			DialogElement el = queue.get(j)
			
			if(el instanceof DialogQuestion || el instanceof DialogQuestionEnd) {
				break;
			}
			
			if(el instanceof DialogQuestionStart) {
				results.add(0, el);
				break;
			}
			
			results.add(0, el)
			
		}
		
		return results
		
	}	
	
	public List<DialogElement> getPreviousPage(boolean removeFromStack) {
		
//		if(queue.size() == 0 || !( queue.get(queue.size() - 1) instanceof DialogPageEnd)) {
//
//		}
		
		//if( ! ( peekElement() instanceof DialogPageStart) ) throw new Exception("No active dialog question page on top of the queue")
		
		List<DialogElement> previousPage = null

		boolean inPage = false
		
		for(int j = queue.size() - 1 ; j >= 0; j--) {
			
			DialogElement el = queue.get(j)
			
			if(el instanceof DialogPageEnd) {
				inPage = true
				previousPage = []
			}
			
			if(inPage) {
				if(removeFromStack) queue.remove(j)
				previousPage.add(0, el)
			}
			
			if(el instanceof DialogPageStart) {
				break
			}
			
		}
		
		return previousPage
		
	}
}
