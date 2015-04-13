package kafka;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * @author Pankaj
 *
 */
@Component
public class SimpleProducer {
	
	
	 private static Producer<Integer, String> producer;
	 
	 
	 private final Properties properties= new Properties();

	    public SimpleProducer() {
	       // properties.put("metadata.broker.list", "localhost:9092");
	    	 properties.put("metadata.broker.list", "54.149.84.25:9092");
	        properties.put("serializer.class", "kafka.serializer.StringEncoder");
	        properties.put("request.required.acks", "1");
	        producer = new Producer<>(new ProducerConfig(properties));
	    }

	  /* public static void main(String[] args) {
	        new SimpleProducer();
	        String topic = "cmpe273-topic";//args[0];
	        String msg = "pankajdighe77@gmail.com:010123451:Poll Result [Android=100,iPhone=200]";//args[1];
	        KeyedMessage<Integer, String> data = new KeyedMessage<>(topic, msg);
	        producer.send(data);
	       // producer.send(data);
	        producer.close();
	    }*/
	    
	    public static void send_message(String message) {
	    	new SimpleProducer();
	        String topic = "cmpe273-topic";//args[0];
	       // String msg = "pankajdighe77@gmail.com:010123451:Poll Result [Android=100,iPhone=200]";//args[1];
	        KeyedMessage<Integer, String> data = new KeyedMessage<>(topic, message);
	        producer.send(data);
	       // producer.send(data);
	        producer.close();
	    }

}
