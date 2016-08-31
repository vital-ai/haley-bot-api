package ai.haley.agent.api

import ai.haley.agent.domain.DialogElement
import ai.haley.agent.domain.DialogGenerator
import ai.haley.agent.domain.DialogPageEnd
import ai.haley.agent.domain.DialogPageQuestionsEnd;
import ai.haley.agent.domain.DialogPageStart;
import ai.haley.agent.domain.DialogQuestion
import ai.haley.agent.domain.DialogQuestionEnd;;

class DialogQueue {

	private List<DialogElement> queue = new ArrayList()
	
	boolean isEmpty() {
		
		return queue.isEmpty()
		
	}
	
	
	public int size() {
		return queue.size();
	}
	
	DialogElement popElement() {
		
		if( queue.isEmpty() ) {
			
			return null
		}
		
		
		return queue.remove(0)
		
		
	}
	
	DialogElement peekElement() {
		
		
		if( queue.isEmpty() ) {
			
			return null
		}
		
		return queue.get(0)
		
	}
	
	
	void putOnTop(DialogElement q) {
		
		queue.remove(q)
		
		queue.add(0, q)
		
	}
	
	
	/**
	 * nicer method to put a list elements on top in this order
	 * this prevents the reveresed order single element 
	 * @param elements
	 */
	void putOnTopElements(List<DialogElement> elements) {
		
		
		for(int i = elements.size() - 1; i >= 0; i--) {
		
			DialogElement q = elements.get(i)
			
			queue.remove(q)
				
			queue.add(0, q)
			
		}
		
	}
	
	void putAtEnd(DialogElement q) {
		
		queue.remove(q)
		
		queue.add(q)
		
	}
	
	public List<DialogElement> getList() {
		return Collections.unmodifiableList(queue);
	}
	

	public List<DialogElement> listWithID(String id) {
		List l = []
		for(DialogElement el : queue) {
			if(el.id == id) {
				l.add(el)
			}
		}
		return l
	}
	
	//returns the list of all questionIDs
	public List<String> getAllQuestionIDs() {
		
		List<String> ids = []
		
		for(DialogElement el : queue) {
			
			if(el instanceof DialogQuestion) {
				
				String qid = el.id
				
				if(qid != null) {
					
					ids.add(qid)
					
				}
			}
		}
		
		return ids
	}
	
	public List<String> getDialogGeneratorIDs() {

		List<String> ids = []
		
		for(DialogElement el : queue) {
			
			if(el instanceof DialogGenerator) {
				
				String dgid = el.id
				
				if(dgid != null) {
					
					ids.add(dgid)
					
				}
			}
		}
		
		return ids
				
	}
	
	public int removeElementsWithIDPrefix(String idPrefix) {
		
		int c = 0
		
		for( Iterator<DialogElement> iter = queue.iterator(); iter.hasNext(); ) {
			
			DialogElement de = iter.next()
			
			if(de.id != null && de.id.startsWith(idPrefix)) {
				c++
				iter.remove();
			}
			
		}
		
		return c

	}
	
	public int removeElementsByID(String id) {
		
		int c = 0
		
		for( Iterator<DialogElement> iter = queue.iterator(); iter.hasNext(); ) {
			
			DialogElement de = iter.next()
			
			if(de.id != null && de.id == id) {
				c++
				iter.remove();
			}
			
		}
		
		return c
		
	}

	public List<DialogElement> getCurrentQuestionWithElements() {
		
		List<DialogElement> els = []
		if(queue.size() == 0) throw new Exception("Queue is empty")
		if(!(peekElement() instanceof DialogQuestion)) throw new Exception("not a question on top of the queue: ${peekElement().getClass().getSimpleName()}")

		els.add(peekElement())
		
		for(int i = 1 ; i < queue.size(); i++) {
			
			DialogElement el = queue.get(i)

			if(el instanceof DialogQuestionEnd) {
				els.add(el)
				break
			}			
			
			
			if(el instanceof DialogQuestion) {
				break
			}
			
			els.add(el)
			
		}	
		
		return els	
		
		
	}
	
	/**
	 * Returns current dialog page elements, only valid if peek element is a question 
	 * @return
	 */
	public List<DialogElement> getCurrentPage() {
		
		if( ! ( peekElement() instanceof DialogPageStart) ) throw new Exception("No active dialog question page on top of the queue")
		
		List<DialogElement> pageEls = []

		int i = 0
		
		while(i >= 0) {
			
			if(i >= queue.size()) {
				throw new Exception("No DialogPageEnd found!")
			}
			
			DialogElement el = queue.get(i)
			
			pageEls.add(el)
			
			if(el instanceof DialogPageEnd) {
				i = -1
			} else {
				i++
			}
			
		}
		
		return pageEls
				
		
	}
	
	public List<DialogElement> getNextPage() {
		
		if( ! ( peekElement() instanceof DialogPageStart) ) throw new Exception("No active dialog question page on top of the queue")
		
		List<DialogElement> nextPageEls = null
		
		boolean inNextPage = false
		
		for(int i = 1 ; i < queue.size(); i++) {
			
			DialogElement el = queue.get(i)
			
			if(el instanceof DialogPageStart) {
				nextPageEls = []
				inNextPage = true
			}
			
			if(inNextPage) nextPageEls.add(el)
			
			if(inNextPage && el instanceof DialogPageEnd) {
				break
			}

		}
		
		return nextPageEls
		
	}

	void removePageElement(DialogElement el) {
		
		if( ! ( peekElement() instanceof DialogPageStart) ) throw new Exception("No active dialog question page on top of the queue")
		
		if(el instanceof DialogPageStart || el instanceof DialogPageEnd || el instanceof DialogPageQuestionsEnd) {
			throw new Exception("Cannot remove page frame element from question page: " + el.getClass().getSimpleName());
		}
		
		for(int i = 1; i < queue.size(); i++) {
			
			DialogElement d = queue.get(i)
			
			if( d == el || (d.id != null && el.id != null && d.id == el.id) ) {
				
				queue.remove(i)
				return
				
			}
			
		}
		
	}
}
