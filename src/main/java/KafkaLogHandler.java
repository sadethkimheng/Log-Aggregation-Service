import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class KafkaLogHandler extends Handler{


    InetAddress addr ;
    String ipAddress;
    String hostname;
    static String IP = "";
    String json = null;
    InstanceKafka ik = new InstanceKafka();
    ObjectMapper mapper = new ObjectMapper();
    NetworkInterface e;
    Enumeration<NetworkInterface> n;
    Enumeration<InetAddress> a;
    InetAddress addrr;
    Properties properties = new Properties();
    Producer<String, Object > producer;
    ProducerRecord<String,Object > productRecord;




    public void NetworkInterface (){

        try {
            n = NetworkInterface.getNetworkInterfaces();
            while (n.hasMoreElements())
            {
                e = n.nextElement();
                a = e.getInetAddresses();
                while (a.hasMoreElements())
                {

                    addrr = a.nextElement();
                    if (!addrr.isLoopbackAddress() && addrr.isSiteLocalAddress()) {
                        IP = addrr.getHostAddress();

                    }

                }


            }

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }


    }

    @Override
    public void publish(LogRecord record)  {

        if(IP == null || IP == "")
        {
            NetworkInterface();
        }


        // JSON file

        try {
            json = mapper.writeValueAsString(ik);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        {
            try {
                addr = InetAddress.getLocalHost();
                ipAddress = addr.getHostAddress();
                hostname = addr.getHostName();

                ik.setHostname(hostname);
                ik.setRecord(record);
                ik.setIP(IP);


            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        KafakaProducer();


    }


    public void KafakaProducer (){

        //kafka bootstrap server

        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());


        producer = new org.apache.kafka.clients.producer.KafkaProducer<String, Object>(properties);

        productRecord = new ProducerRecord<String, Object>("testing2","3",json);

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