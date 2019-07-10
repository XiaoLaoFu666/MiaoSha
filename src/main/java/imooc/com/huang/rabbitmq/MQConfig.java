package imooc.com.huang.rabbitmq;



import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MQConfig {

	public static final String QUEUE = "queue";
	public static final String TOPIC_QUEUE1 = "topic.queue1";
	public static final String TOPIC_QUEUE2 = "topic.queue2";
	public static final String HANDER_QUEUE = "handerQueue";

	public static final String TOPICEXCHANGE = "topicExchange";
	public static final String FANOUT_EXCHANGE = "fanoutExchange";
	public static final String HANDER_EXCHANGE = "handerExchange";
	public static final String MIAOSHA_QUEUE = "miaosha.queue";


	/*
	 * Driect 交换机模式
	 * */
	@Bean
	public Queue queue() {
		return new Queue(QUEUE,true);
	}
	
	/*
	 * topic 交换机模式
	 * */
	
	@Bean
	public Queue Topicqueue1() {
		return new Queue(TOPIC_QUEUE1,true);
	}
	
	@Bean
	public Queue Topicqueue2() {
		return new Queue(TOPIC_QUEUE2,true);
	}
	
	@Bean 
	public TopicExchange topicExchange() {
		return new TopicExchange(TOPICEXCHANGE);
	}
	
	@Bean
	public Binding binDing1() {
		return BindingBuilder.bind(Topicqueue1()).to(topicExchange()).with("topic.key1");
	}
	
	@Bean
	public Binding binDing2() {
		return BindingBuilder.bind(Topicqueue2()).to(topicExchange()).with("topic.#");
	}
	
	/*
	 * fanout交换机模式  广播
	 * */
	
	
	@Bean 
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(FANOUT_EXCHANGE);
	}
	
	@Bean
	public Binding FanoutbinDing1() {
		return BindingBuilder.bind(Topicqueue1()).to(fanoutExchange());
	}
	
	@Bean
	public Binding FanoutbinDing2() {
		return BindingBuilder.bind(Topicqueue2()).to(fanoutExchange());
	}
	
	/*
	 * hander交换机模式
	 * */
	@Bean 
	public Queue handerQueue() {
		return new Queue(HANDER_QUEUE);
	}
	@Bean
	public HeadersExchange headersExchange() {
		return new HeadersExchange(HANDER_EXCHANGE);
		
	}
	@Bean
	public Binding HeaderBinding() {
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("header1", "value1");
		map.put("header2", "value2");
		return BindingBuilder.bind( handerQueue()).to( headersExchange()).whereAll(map).match();
	}
}
