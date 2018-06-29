import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.LogRecord;




public class KafkaLogHandler extends Handler{


    InetAddress addr ;
    String hostname;
    String IP = "";
    Producer<String, Object > producer;





    public KafkaLogHandler(){
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
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        producer = new org.apache.kafka.clients.producer.KafkaProducer<String, Object>(properties);
    }

    @Override
    public void publish(LogRecord record) {

        String json = null;

        try {
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
            InstanceKafka ik = new InstanceKafka();
            ik.setHostname(hostname);
            ik.setRecord(record);
            ik.setIP(IP);
            ObjectMapper mapper = new ObjectMapper();

            json = mapper.writeValueAsString(ik);


        } catch (Exception e) {
            e.printStackTrace();
        }

            KafakaProducer(json);

    }

    public void KafakaProducer (String json) {

//        kafka bootstrap server
        ProducerRecord<String,Object >  productRecord = new ProducerRecord<String, Object>("testing2","3",json);
            producer.send(productRecord);
            producer.flush();

    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }




}




