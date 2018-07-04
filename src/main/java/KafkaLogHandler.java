import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class KafkaLogHandler extends Handler implements Runnable {

    InetAddress addr ;
    String hostname;
    static String IP = "";
    static String json = null;

    protected BlockingQueue<Object> blockingQueue;

    public KafkaLogHandler(BlockingQueue<Object> queue) {
        this.blockingQueue = queue;

    }

    public KafkaLogHandler() {
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
        try {

            System.out.println(IP);
            InstanceKafka ik = new InstanceKafka();
            addr = InetAddress.getLocalHost();
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


    }
    public void run() {
        try {
            System.out.println(json.getClass());
            blockingQueue.put(json);
        } catch (InterruptedException e) {
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