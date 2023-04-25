import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

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
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        // Create an instance of ChromeDriver with the options
        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

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

            Thread.sleep(5000);

            // Verify and logg successful login by navigating to user wall
            try {
                driver.get("https://www.facebook.com/profile");
                Thread.sleep(3000);
                WebElement onYourMind = driver.findElement(By.xpath("//*[contains(text(), 'on your')]"));
                Logback.loginSuccess();
            } catch (Exception allExcecptions) {
                Logback.loginFailure();
            }

        } catch (IOException e) {
            Logback.iO();
        } catch (NullPointerException e) {
            Logback.nullPointer();
        }
    }
}
