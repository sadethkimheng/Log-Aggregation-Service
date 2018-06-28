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
    String IP = "";
    String json = null;
    InstanceKafka ik = new InstanceKafka();
    ProducerRecord<String,Object >  productRecord;




    public void NetworkInterface (){

        long start = System.currentTimeMillis();
        try {
            Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
            while (n.hasMoreElements())
            {
                NetworkInterface e = n.nextElement();
                Enumeration<InetAddress> a = e.getInetAddresses();
                while (a.hasMoreElements())
                {

                    InetAddress addrr = a.nextElement();
                    if (!addrr.isLoopbackAddress() && addrr.isSiteLocalAddress())
                    {
                        IP = addrr.getHostAddress();
                    }

                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

        long end = System.currentTimeMillis();
        System.out.println("Counting From NetworkInterface takes " + (end - start) + "ms");
    }

    @Override
    public void publish(LogRecord record) {

        long start = System.currentTimeMillis();

        if (IP == null || IP == "") {
            NetworkInterface();
        }

        try {
            addr = InetAddress.getLocalHost();
            ipAddress = addr.getHostAddress();
            hostname = addr.getHostName();

            ik.setHostname(hostname);
            ik.setRecord(record);
            ik.setIP(IP);
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(ik);


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        if (productRecord == null) {

            KafakaProducer();

        }

        long end = System.currentTimeMillis();
        System.out.println("Counting Publish takes " + (end - start) + "ms");


    }



    public void KafakaProducer (){

//        kafka bootstrap server
        long start = System.currentTimeMillis();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());


        Producer<String, Object > producer = new org.apache.kafka.clients.producer.KafkaProducer<String, Object>(properties);

        productRecord = new ProducerRecord<String, Object>("testing2","3",json);

        producer.send(productRecord);
        producer.close();
        long end = System.currentTimeMillis();
        System.out.println("Counting Productreord takes 1" + (end - start) + "ms");

    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }





}