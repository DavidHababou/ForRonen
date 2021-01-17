package bank.test.david;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class DavidController {
	
	@Autowired
	RabbitTemplate rabbitTepmlate;
	
	@RequestMapping(value = "/getBandDetails", method = RequestMethod.POST)
	public Band getDetails(@RequestBody Request request) throws Exception{
		Band returnedMessage = (Band) rabbitTepmlate.convertSendAndReceive("requests", request, m -> {
	     m.getMessageProperties().setReplyTo("responses");
	     //m.getMessageProperties().setCorrelationId(UUID.randomUUID().toString());
	     return m;
	 });
	return returnedMessage;
	}
}
