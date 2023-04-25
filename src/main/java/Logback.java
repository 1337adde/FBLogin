import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logback {
    private static final Logger logger = LoggerFactory.getLogger(Logback.class);

    public static void iO() {
        logger.error("IOException when trying to input credentials.");
    }
    public static void nullPointer() {
        logger.error("NullPointerException when trying to input credentials.");
    }
    public static void loginSuccess() {logger.info("Login was successful.");}
    public static void loginFailure() {logger.info("Login failed.");}

}
