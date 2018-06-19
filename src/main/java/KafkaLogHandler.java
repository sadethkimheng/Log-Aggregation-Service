
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class KafkaLogHandler extends Handler {





    public void publish(LogRecord logRecord) {

        List<String> handler = new ArrayList<String>();

        handler.add(logRecord.getLevel() + ":");
        handler.add(logRecord.getMillis()+ "@");
        handler.add(logRecord.getSourceClassName() + "*");
        handler.add(logRecord.getSourceMethodName() + "&");
        handler.add("<" + logRecord.getMessage() + ">");
        handler.add("\n");

        System.out.println(handler);

        Properties properties = new Properties();

        // kafka bootstrap server
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        // producer acks
        properties.setProperty("acks", "1");
        properties.setProperty("retries", "3");
        properties.setProperty("linger.ms", "1");
        Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(properties);
        for (String str : handler) {

            ProducerRecord<String, String> productRecord = new ProducerRecord<String, String>("testing1", "3", str);
            producer.send(productRecord);

        }
        producer.close();



    }

    public void flush() {
    }

    public void close() {

    }


}
