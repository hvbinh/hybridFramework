package common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.time.Duration;
import java.util.Random;

public class BaseTest {
    private WebDriver driver;
    private String projectFolder = System.getProperty("user.dir");
    private String osName = System.getProperty("os.name");
    protected final Logger log;

    protected BaseTest() {
        log = LogManager.getLogger(getClass());
    }

    protected synchronized WebDriver getBrowserDriver(String browserName, String url) {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        Browser browser = Browser.valueOf(browserName.toUpperCase());
        switch (browser)
        {
            case CHROME_UI:
            {
                driver = new ChromeDriver();
                break;
            }
            case FIREFOX_UI:
            {
                driver = new FirefoxDriver();
                break;
            }
            case EDGE_CHROMIUM:
            {
                driver = new EdgeDriver();
                break;
            }
            case CHROME_HEADLESS:
            {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("headless");
                options.addArguments("window-size=1920x1080");
                driver = new ChromeDriver(options);
                break;
            }
            case FIREFOX_HEADLESS:
            {
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("window-size=1920x1080");
                options.addArguments("-headless");
                driver = new FirefoxDriver(options);
                break;
            }
            default:
                throw new RuntimeException("please input valid browser name");

        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.getGlobalConstants().getSHORT_TIME()));
        driver.get(url);
        return driver;
    }

    public String getDirectorySlash(String folderName) {
        if (isMac() || isUnix() || isSolaris()) {
            folderName = "/" + folderName + "/";
        } else {
            folderName = "\\" + folderName + "\\";
        }
        return folderName;
    }

    public boolean isWindows() {
        return (osName.toLowerCase().indexOf("win") >= 0);
    }

    public boolean isMac() {
        return (osName.toLowerCase().indexOf("mac") >= 0);
    }

    public boolean isUnix() {
        return (osName.toLowerCase().indexOf("nix") >= 0 || osName.toLowerCase().indexOf("nux") >= 0 || osName.toLowerCase().indexOf("aix") > 0);
    }

    public boolean isSolaris() {
        return (osName.toLowerCase().indexOf("sunos") >= 0);
    }

    protected int randomNumber() {
        Random random = new Random();
        return random.nextInt(999999);
    }


    public WebDriver getDriver() {
        return driver;
    }

    protected void closeBrowserAndDriver(WebDriver driver) {
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            String cmd = "";
            if (driver != null) {
                driver.quit();
            }

            if (driver.toString().toLowerCase().contains("chrome")) {
                if (osName.toLowerCase().contains("mac")) {
                    cmd = "pkill chromedriver";
                } else if (osName.toLowerCase().contains("windows")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq chromedriver*\"";
                }
            } else if (driver.toString().toLowerCase().contains("internetexplorer")) {
                if (osName.toLowerCase().contains("window")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq IEDriverServer*\"";
                }
            } else if (driver.toString().toLowerCase().contains("firefox")) {
                if (osName.toLowerCase().contains("mac")) {
                    cmd = "pkill geckodriver";
                } else if (osName.toLowerCase().contains("windows")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq geckodriver*\"";
                }
            } else if (driver.toString().toLowerCase().contains("edge")) {
                if (osName.toLowerCase().contains("mac")) {
                    cmd = "pkill msedgedriver";
                } else if (osName.toLowerCase().contains("windows")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq msedgedriver*\"";
                }
            }

            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();

        } catch (Exception e) {
        }
    }

}
