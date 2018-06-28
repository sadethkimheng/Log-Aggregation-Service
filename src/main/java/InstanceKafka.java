
import java.net.InetAddress;
import java.util.logging.LogRecord;


public class InstanceKafka  {


    InetAddress addr;
    String ipAddress;
    String hostname;
    LogRecord record;
    String IP;


    public InetAddress getAddr() {
        return addr;
    }

    public void setAddr(InetAddress addr) {
        this.addr = addr;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public LogRecord getRecord() {
        return record;
    }

    public void setRecord(LogRecord record) {
        this.record = record;
    }
    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }


}


