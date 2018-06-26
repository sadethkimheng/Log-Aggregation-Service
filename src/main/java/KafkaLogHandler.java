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
    String IP = null;



    @Override
    public void publish(LogRecord record)  {


            try {
                Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
                while (n.hasMoreElements())
                {
                    NetworkInterface e = n.nextElement();
                    System.out.println("Interface: " + e.getName());
                    Enumeration<InetAddress> a = e.getInetAddresses();
                    while (a.hasMoreElements())
                   {

                        InetAddress addr = a.nextElement();
                        System.out.println(addr.getHostAddress() + "\n");
                        if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()) {
                            IP = addr.getHostAddress();
                        }

                    }


                }

            }
            catch (Exception e)
            {

            }


        {
            try {
                addr = InetAddress.getLocalHost();
                ipAddress = addr.getHostAddress();
                hostname = addr.getHostName();



            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }



        InstanceKafka ik = new InstanceKafka();

        ik.setHostname(hostname);
        ik.setRecord(record);
        ik.setIP(IP);



        System.out.println(ik.getHostname());
        System.out.println(ik.getRecord());
        System.out.println(ik.getIP());




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


