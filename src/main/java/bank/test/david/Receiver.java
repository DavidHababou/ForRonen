package bank.test.david;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
	
	@Autowired
	RabbitTemplate rabbitTepmlate;
	public void handleMessage(Request message) {
		rabbitTepmlate.convertAndSend("responses", new Band("LedZepplin", "1969-1980", null));
	}
}