import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;

public class ThreadKafka implements Runnable {
    private BlockingQueue<Object> blockingQueue;
    Properties properties = new Properties();


    public ThreadKafka(BlockingQueue<Object> queue) {

        this.blockingQueue = queue;


    }
    public void run() {
        try {
            System.out.println(blockingQueue.take());
            properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
            properties.setProperty("key.serializer", StringSerializer.class.getName());
            properties.setProperty("value.serializer", StringSerializer.class.getName());
            Producer<String, Object> producer = new org.apache.kafka.clients.producer.KafkaProducer<String, Object>(properties);
            ProducerRecord<String, Object> productRecord = new ProducerRecord<String, Object>("testing1", "3","testing");
            producer.send(productRecord);
            producer.flush();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}