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
    String hostname;
    String IP = "";
    String json = null;
    InstanceKafka ik = new InstanceKafka();
    ObjectMapper mapper = new ObjectMapper();
    ProducerRecord<String,Object >  productRecord;
    Producer<String, Object > producer;




    public void NetworkInterface () {

        try {

            Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
            while (n.hasMoreElements()) {
                NetworkInterface e = n.nextElement();
                Enumeration<InetAddress> a = e.getInetAddresses();
                while (a.hasMoreElements()) {

                    InetAddress addrr = a.nextElement();
                    if (!addrr.isLoopbackAddress() && addrr.isSiteLocalAddress()) {
                        IP = addrr.getHostAddress();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public void publish(LogRecord record) {

        if (IP == null || IP == "") {
            NetworkInterface();
        }

        try {
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();

            ik.setHostname(hostname);
            ik.setRecord(record);
            ik.setIP(IP);
            json = mapper.writeValueAsString(ik);


        } catch (Exception e) {
            e.printStackTrace();
        }


        if (productRecord == null) {

            KafakaProducer();

        }
        productRecord = new ProducerRecord<String, Object>("testing2","3",json);
        producer.send(productRecord);
        producer.flush();






    }
    public void KafakaProducer (){

//        kafka bootstrap server
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());


        producer = new org.apache.kafka.clients.producer.KafkaProducer<String, Object>(properties);

    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }




}