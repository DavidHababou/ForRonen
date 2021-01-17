package bank.test.david;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
public class DavidApplication {

	public static void main(String[] args) {
		SpringApplication.run(DavidApplication.class, args);
	}
	static final String queueName = "requests";
	//static final String topicExchangeName = "david-test-exchange";
	
	@Value("${hostname}")
	String hostname;
	@Value("${vhost}")
	String vhost;
	@Value("${user}")
	String user;
	@Value("${password}")
	String password;
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Bean
	  Queue requestQueue() {
	    return new Queue(queueName, false);
	  }

	  /*@Bean
	  TopicExchange exchange() {
	    return new TopicExchange(topicExchangeName);
	  }*/

	  /*@Bean
	  Binding binding(Queue queue, TopicExchange exchange) {
	    return BindingBuilder.bind(queue).to(exchange).with("band.request.#");
	  }*/

	  @Bean
	  SimpleMessageListenerContainer BandServiceContainer(ConnectionFactory connectionFactory,
	      MessageListenerAdapter listenerAdapter) {
	    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	    container.setConnectionFactory(connectionFactory);
	    container.setQueueNames(queueName);
	    container.setMessageListener(listenerAdapter);
	    return container;
	  }
	  @Bean
	  SimpleMessageListenerContainer templateContainer(ConnectionFactory connectionFactory,
	      MessageListenerAdapter listenerAdapter) {
	    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	    container.setConnectionFactory(connectionFactory);
	    container.setQueueNames("responses");
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
		  rabbitTemplate.setReplyAddress("responses");
		  return rabbitTemplate;
	  }
}
