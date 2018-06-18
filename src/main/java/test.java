
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class test {

    public static Logger logger = LogManager.getLogManager().getLogger("");
    public static List<String> handler = new ArrayList<String>();

    public static void main(String [] agrs){


        LogManager.getLogManager().reset();

        logger.addHandler(new KafkaLogHandler());


        logger.info("Logging Info");



    }
}
