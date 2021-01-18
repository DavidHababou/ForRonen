package bank.test.david;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {
	@Value("${requestsQ}")
	private String requestsQ;
	@Value("${responsesQ}")
	private String responsesQ;
	@Value("${hostname}")
	private String hostname;
	@Value("${vhost}")
	private String vhost;
	@Value("${user}")
	private String user;
	@Value("${password}")
	private String password;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Bean
	  Queue requestQueue() {
	    return new Queue(requestsQ, false);
	  }
	@Bean
	  Queue responseQueue() {
		    return new Queue(responsesQ, false);
		  }
	 @Bean
	 SimpleMessageListenerContainer bandServiceContainer(ConnectionFactory connectionFactory,
	      MessageListenerAdapter listenerAdapter) {
	    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	    container.setConnectionFactory(connectionFactory);
	    container.setQueueNames(requestsQ);
	    container.setMessageListener(listenerAdapter);
	    return container;
	  }
	  @Bean
	  SimpleMessageListenerContainer templateContainer(ConnectionFactory connectionFactory,
	      MessageListenerAdapter listenerAdapter) {
	    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	    container.setConnectionFactory(connectionFactory);
	    container.setQueueNames(responsesQ);
	    container.setMessageListener(rabbitTemplate);
	    return container;
	  }

	  @Bean
	  MessageListenerAdapter listenerAdapter(Receiver receiver) {
	    return new MessageListenerAdapter(receiver, new Jackson2XmlMessageConverter());
	  }
	  @Bean
	  public ConnectionFactory connectionFactory() {
	      CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
	      connectionFactory.setAddresses(hostname);
	      connectionFactory.setVirtualHost(vhost);
	      connectionFactory.setUsername(user);
	      connectionFactory.setPassword(password);
	      return connectionFactory;
	  }
	  @Bean
	  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		  RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		  rabbitTemplate.setMessageConverter(new Jackson2XmlMessageConverter());
		  rabbitTemplate.setReceiveTimeout(15000);
		  rabbitTemplate.setReplyAddress(responsesQ);
		  return rabbitTemplate;
	  }
}
