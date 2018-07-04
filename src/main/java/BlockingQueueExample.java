import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class BlockingQueueExample {
    LogRecord record;
    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        Logger logger = LogManager.getLogManager().getLogger("");
        LogManager.getLogManager().reset();

        logger.addHandler(new KafkaLogHandler());
        logger.info("Logging Info");


        BlockingQueue<Object> blockingQueue = new ArrayBlockingQueue<Object>(10);
        KafkaLogHandler queueProducer = new KafkaLogHandler(blockingQueue);
        ThreadKafka queueConsumer = new ThreadKafka(blockingQueue);

        new Thread(queueProducer).start();
        new Thread(queueConsumer).start();

        long end = System.currentTimeMillis();
        System.out.println("Counting KafkaLogHandler takes 1" + (end - start) + "ms");
    }


}