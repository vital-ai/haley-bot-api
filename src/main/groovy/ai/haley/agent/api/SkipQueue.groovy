package ai.haley.agent.api


class SkipQueue {

	private List<DialogChunk> queue = new ArrayList()
	
	boolean isEmpty() {
		return queue.isEmpty()
	}
	
	
	public int size() {
		return queue.size();
	}
	
	DialogChunk popElement() {
		
		if( queue.isEmpty() ) {
			
			return null
		}
		
		
		return queue.remove(0)
		
		
	}
	
	DialogChunk peekElement() {
		
		if( queue.isEmpty() ) {
			
			return null
		}
		
		return queue.get(0)
		
	}
	
	
	void putOnTop(DialogChunk q) {
		
		queue.remove(q)
		
		queue.add(0, q)
		
	}
	
	void putAtEnd(DialogChunk q) {
		
		queue.remove(q)
		
		queue.add(q)
		
	}
	
	public List<DialogChunk> getList() {
		return Collections.unmodifiableList(queue);
	}
	
}