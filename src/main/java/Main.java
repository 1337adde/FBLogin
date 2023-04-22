import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        /* Här slutade vi sist (fredag 21/4)
        // logger-kod från chat GPT
        private static final Logger logger = LoggerFactory.getLogger(Main.class);

        public void test() {
            logger.info("Starting test...");
            // Perform test steps here
            logger.info("Test complete.");
        }
        */

        WebDriverManager.chromedriver().setup();
        // Create an instance of ChromeOptions and add the desired option
        ChromeOptions options = new ChromeOptions();
        /* This option opens the Chrome window with specified user profile.
        It is useful for when you wish to already be logged in.
        Replace the dir path with the path to your own user.
        options.addArguments("--user-data-dir=C:\\Users\\adamj\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 2");
        */
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        // Create an instance of ChromeDriver with the options
        ChromeDriver driver = new ChromeDriver(options);

        // Use the driver to navigate to a webpage and perform actions
        driver.get("https://www.facebook.com");

        // Accept cookies
        WebElement acceptCookies = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div/div/div[3]/button[2]"));
        acceptCookies.click();

        // Creates a new object from .JSON-file containing Facebook credentials
        File jsonFile = new File("C:\\temp\\facebook.json");
        String email;
        String password;

        try {
            // Reads the JSON-file and writes the values to variables
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);
            email = jsonNode.get("facebookCredentials").get("email").asText();
            password = jsonNode.get("facebookCredentials").get("password").asText();

            // Input text into the email and password fields
            WebElement emailField = driver.findElement(By.id("email"));
            emailField.sendKeys(email);
            WebElement passwordField = driver.findElement(By.id("pass"));
            passwordField.sendKeys(password);
            // Click log in button
            WebElement loginButton = driver.findElement(By.name("login"));
            loginButton.click();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
