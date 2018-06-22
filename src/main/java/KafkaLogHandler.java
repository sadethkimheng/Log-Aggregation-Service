
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class KafkaLogHandler extends Handler  {


    public void publish(LogRecord logRecord)   {


        InstanceKafka instance = new InstanceKafka();
        instance.publish(logRecord);
       }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }


}