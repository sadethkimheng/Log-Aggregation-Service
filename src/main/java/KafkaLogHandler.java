
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.*;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;




public class KafkaLogHandler extends Handler {



    public void publish(LogRecord logRecord) {

        //  Convert String
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(logRecord);

            Properties properties = new Properties();

             //kafka bootstrap server
            String TOPIC = "testing1";
            properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
            properties.setProperty("key.serializer", StringSerializer.class.getName());
            properties.setProperty("value.serializer", StringSerializer.class.getName());

            Producer<String, String > producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String >(properties);


            ProducerRecord<String, String > productRecord = new ProducerRecord<String, String>(TOPIC,"3",json);
            producer.send(productRecord);

            producer.close();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
