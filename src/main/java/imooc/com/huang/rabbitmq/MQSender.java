package imooc.com.huang.rabbitmq;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imooc.com.huang.redis.RedisService;

@Service
public class MQSender {

	private static org.slf4j.Logger log = LoggerFactory.getLogger(MQSender.class);

	@Autowired
	AmqpTemplate amqpTemplate;
	
	public void sendMiaoShaMessage(MiaoshaMessage miaoshaMessage) {
		// TODO Auto-generated method stub
		String msg = RedisService.BeanToString(miaoshaMessage);
		log.info("send message" + msg);
		amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, msg);
	}
	/*
	public void send(Object message) {
		String msg = RedisService.BeanToString(message);
		log.info("send message" + msg);
		amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
		
	}
	public void sendTopic(Object message) {
		String msg = RedisService.BeanToString(message);
		log.info("send topic message" + msg);
		amqpTemplate.convertAndSend(MQConfig.TOPICEXCHANGE,"topic.key1",msg+"1");
		amqpTemplate.convertAndSend(MQConfig.TOPICEXCHANGE,"topic.key2",msg+"2");
		
	}
	
	public void sendFanout(Object message) {
		String msg = RedisService.BeanToString(message);
		log.info("send fanout message" + msg);
		amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE,"",msg+"1");
		
	}
	
	
	public void sendHeader(Object message) {
		String msg = RedisService.BeanToString(message);
		log.info("send Header message" + msg);
		MessageProperties properties = new MessageProperties();
		properties.setHeader("header1", "value1");
		properties.setHeader("header2", "value2");
		Message obj = new Message(msg.getBytes(),properties);
		amqpTemplate.convertAndSend(MQConfig.HANDER_EXCHANGE,"",obj);
		
	}
	*/

}
