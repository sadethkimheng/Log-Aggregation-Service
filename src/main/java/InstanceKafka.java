import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.net.InetAddress;
import java.net.UnknownHostException;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Handler;


public class InstanceKafka extends Handler{
    InetAddress addr;
    String ipAddress;
    String hostname;

    {
        try {
            addr = InetAddress.getLocalHost();
            ipAddress = addr.getHostAddress();
            hostname = addr.getHostName();
            System.out.println(ipAddress);
            System.out.println(addr.getHostName());


        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void publish(java.util.logging.LogRecord record) {


        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("HostName", addr.getHostName());
        map.put("HostAddress", addr.getHostAddress());
        map.put("Logger", record);
        String json = null;
        try {
            json = mapper.writeValueAsString(map);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        Properties properties = new Properties();

        //kafka bootstrap server

        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());


        Producer<String, String > producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(properties);

//        String value = test.ipAddress;
        ProducerRecord<String,String > productRecord = new ProducerRecord<>("testing1","3",json);

        producer.send(productRecord);

        producer.close();


    }



    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }





}


