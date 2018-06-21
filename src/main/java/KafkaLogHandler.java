
import com.google.gson.Gson;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.FileWriter;
import java.util.*;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jackson.JsonNode;




public class KafkaLogHandler extends Handler {



    public void publish(LogRecord logRecord) {

        List<String> handler = new ArrayList<String>();


        handler.add(logRecord.getLevel() + ":" + logRecord.getMillis() + "*" + logRecord.getSourceClassName() + "&" + logRecord.getSourceMethodName() + " <" + logRecord.getMessage() + ">");


//        System.out.println(handler);
        String res = String.join(",", handler);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<String, String>();
        map.put("status", "200");
        map.put("data", res);
        String json = null;
        try {
            json = mapper.writeValueAsString(map);

            Properties properties = new Properties();

             //kafka bootstrap server
            String TOPIC = "testing1";
            properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
            properties.setProperty("key.serializer", StringSerializer.class.getName());
            properties.setProperty("value.serializer", StringSerializer.class.getName());


            // producer acks
            properties.setProperty("acks", "1");
            properties.setProperty("retries", "3");
            properties.setProperty("linger.ms", "1");


            Producer<String, String > producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String >(properties);


            ProducerRecord<String, String > productRecord = new ProducerRecord<String, String>(TOPIC,"3",json);
            producer.send(productRecord);

            producer.close();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(json);

    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
