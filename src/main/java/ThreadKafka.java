import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;


public class ThreadKafka extends Thread {
    Producer<String, Object > producer;
    private String json;


    public ThreadKafka( Producer<String, Object > producer, String json) {
        this.json = json;
        this.producer = producer;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getId()+"test");

        ProducerRecord<String,Object > productRecord = new ProducerRecord<String, Object>("testing2","3",json);
        producer.send(productRecord);
        producer.flush();
    }
}
