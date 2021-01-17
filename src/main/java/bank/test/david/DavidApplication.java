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

@SpringBootApplication
public class DavidApplication {

	public static void main(String[] args) {
		SpringApplication.run(DavidApplication.class, args);
	}
	static final String queueName = "requests";
	//static final String topicExchangeName = "david-test-exchange";

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
	  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
	      MessageListenerAdapter listenerAdapter) {
	    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	    container.setConnectionFactory(connectionFactory);
	    container.setQueueNames(queueName);
	    container.setMessageListener(listenerAdapter);
	    return container;
	  }

	  @Bean
	  MessageListenerAdapter listenerAdapter(Receiver receiver) {
	    return new MessageListenerAdapter(receiver, new Jackson2XmlMessageConverter());
	  }
	  @Bean
	  public ConnectionFactory connectionFactory() {
	      CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
	      connectionFactory.setAddresses("rattlesnake.rmq.cloudamqp.com");
	      connectionFactory.setVirtualHost("moafgngt");
	      connectionFactory.setUsername("moafgngt");
	      connectionFactory.setPassword("XPTR_5nPEYsvfysy2ittrc33QCvIpaV6");
	      connectionFactory.setPublisherReturns(true);
	      return connectionFactory;
	  }
	  @Bean
	  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		  RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		  rabbitTemplate.setMessageConverter(new Jackson2XmlMessageConverter());
		  rabbitTemplate.setReceiveTimeout(15000);
		  return rabbitTemplate;
	  }
}
