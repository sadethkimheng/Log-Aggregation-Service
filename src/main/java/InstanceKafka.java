
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class InstanceKafka extends Handler  {


    public void publish(LogRecord logRecord)   {


        KafkaLogHandler instance = new KafkaLogHandler();
        instance.publish(logRecord);
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }


}
