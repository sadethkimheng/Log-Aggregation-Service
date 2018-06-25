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
import java.util.logging.LogRecord;


public class KafkaLogHandler extends Handler{


    InetAddress addr ;
    String ipAddress;
    String hostname;
    {
        try {
            addr = InetAddress.getLocalHost();
            ipAddress = addr.getHostAddress();
            hostname = addr.getHostName();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void publish(LogRecord record) {


        InstanceKafka ik = new InstanceKafka();

        ik.setAddr(addr);
        ik.setHostname(hostname);
        ik.setIpAddress(ipAddress);
        ik.setRecord(record);


        System.out.print(ik.getHostname());
        System.out.print(ik.getIpAddress());
        System.out.print(ik.getRecord());


        // JSON file

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(ik);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        Properties properties = new Properties();

        //kafka bootstrap server

        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());


        Producer<String, Object > producer = new org.apache.kafka.clients.producer.KafkaProducer<String, Object>(properties);

        ProducerRecord<String,Object > productRecord = new ProducerRecord<String, Object>("testing1","3",json);

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


