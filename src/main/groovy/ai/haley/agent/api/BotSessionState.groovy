package ai.haley.agent.api

import java.util.Set
import org.slf4j.Logger
import org.slf4j.LoggerFactory;

import com.vitalai.aimp.domain.AIMPMessage
import com.vitalai.aimp.domain.DialogSession
import com.vitalai.aimp.domain.Edge_hasDialogSession
import com.vitalai.aimp.domain.GetFactsMessage;
import com.vitalai.aimp.domain.SetFactMessage;
import com.vitalai.aimp.domain.UnsetFactMessage

import ai.haley.agent.builder.BotBuilder
import ai.haley.agent.domain.DialogSerialize;
import ai.vital.vitalsigns.model.GraphObject
import ai.vital.vitalsigns.model.VITAL_Container;;

interface BotSessionState {

	DialogMode getMode()
	
	DialogQueue getQueue()

	DialogHistory getHistory()

	SkipQueue getSkipQueue()
		
	DialogSession getDialogSession()
	
	String getBotID()
	
	String getBotURI()
	
	BotBuilder getBotBuilder()
	
	void clearDialogFacts() 
	

}
