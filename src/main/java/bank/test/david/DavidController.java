package bank.test.david;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class DavidController {
	
	@Autowired
	RabbitTemplate rabbitTepmlate;
	@Value("${requestsQ}")
	String requestsQ = "requests";
	private static final Logger logger = (Logger) LoggerFactory.getLogger(DavidController.class);
	
	@RequestMapping(value = "/getBandDetails", method = RequestMethod.POST)
	public CalcResult getDetails(@RequestBody Request request) throws Exception{
		logger.info("RESTfull Calc request received. sending to queue");
		CalcResult returnedMessage = (CalcResult) rabbitTepmlate.convertSendAndReceive(requestsQ, request);
		logger.info("Calc response received from queue. sending http response");
		return returnedMessage;
	}
}