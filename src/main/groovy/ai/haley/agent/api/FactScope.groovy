package ai.haley.agent.api

class FactScope {
	
	public final static FactScope channel = new FactScope(FactScopeType.channel)
	
	//current profile
	public final static FactScope profile = new FactScope(FactScopeType.current_profile) 
	 
	public final static FactScope dialog = new FactScope(FactScopeType.dialog)
	
	
	FactScopeType scopeType
	
	String profileURI
	
	public static FactScope valueOf(String value) {
		
		if(value.startsWith("profile:")) {
			return FactScope.fromFactScopeType(value.substring("profile:".length()))
		}
		
		FactScopeType fct = null
		if(value == 'profile') {
			fct = FactScopeType.current_profile
		} else {
			fct = FactScopeType.valueOf(value)
		}
		
		return fromFactScopeType(fct)
		
	}
	
	private static FactScope[] _values = [channel, profile, dialog] as FactScope[] 
	
	public static FactScope[] values() {
		return _values
	}
	
	public static FactScope fromFactScopeType(FactScopeType factScopeType) {
		
		if(factScopeType == FactScopeType.channel) {
			return channel	
		} else if(factScopeType == FactScopeType.dialog) {
			return dialog
		} else if(factScopeType == FactScopeType.current_profile) {
			return profile
		} else if(factScopeType == FactScopeType.other_profile) {
			throw new RuntimeException("Cannot init other_profile scope from enum")
		} else {
			throw new RuntimeException("Unhandled fact scope type: " + factScopeType)
		}
		
	}

	private FactScope(FactScopeType scopeType) {
		super();
		this.scopeType = scopeType;
	}
	
	public static FactScope profileScope(String profileURI) {
		if(profileURI == null) throw new NullPointerException("FactScope profileURI must not be null")
		FactScope fs = new FactScope(FactScopeType.other_profile)
		fs.profileURI = profileURI
		return fs
	}
	
	@Override
	public String toString() {
		if(scopeType == FactScopeType.other_profile) {
			return "FactScope: ${scopeType} profileURI: ${profileURI}"
		} else {
			return "FactScope: ${scopeType}"
		}
	}
	
	public String name() {
		if( scopeType == FactScopeType.dialog || scopeType == FactScopeType.channel ) {
			return scopeType.name()
		} else if(scopeType == FactScopeType.current_profile) {
			return 'profile'
		} else {
			return 'profile:' + profileURI
		}
	}
	
}
