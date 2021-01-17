package bank.test.david;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class DavidController {
	
	@Autowired
	RabbitTemplate rabbitTepmlate;
	@Value("${requestsQ}")
	String requestsQ = "requests";
	
	@RequestMapping(value = "/getBandDetails", method = RequestMethod.POST)
	public Band getDetails(@RequestBody Request request) throws Exception{
		Band returnedMessage = (Band) rabbitTepmlate.convertSendAndReceive(requestsQ, request);
	return returnedMessage;
	}
}