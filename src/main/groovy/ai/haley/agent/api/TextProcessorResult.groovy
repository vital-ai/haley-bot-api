package ai.haley.agent.api

class TextProcessorResult {

	boolean ok = true
	
	String answer
	
	String error
	
	static TextProcessorResult okResult(String answer) {
		return new TextProcessorResult(answer: answer, error: null, ok: true)
	}
	
	static TextProcessorResult errorResult(String answer, String error) {
		return new TextProcessorResult(answer: answer, error: error, ok: false)
	}
	
}
