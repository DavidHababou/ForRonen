package bank.test.david;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
	
	@Autowired
	RabbitTemplate rabbitTepmlate;
	public Band handleMessage(Request message) { 
		Band retVal = new Band("LedZepplin", "1969-1980", null);
		if (message.getIncludeMembers()) {
			retVal.getMembers().add("robert");
			retVal.getMembers().add("J.P");
			retVal.getMembers().add("John");
			retVal.getMembers().add("Jimmy");
		}
		return retVal;
	}
}