import org.junit.Test;

import java.util.logging.LogManager;
import java.util.logging.Logger;


public class KafkaLogHandlerTest {

    @Test
    public void publish() {
        Logger logger = LogManager.getLogManager().getLogger("");
        LogManager.getLogManager().reset();

        logger.addHandler(new KafkaLogHandler());

        logger.info("Logging Info");

    }

}