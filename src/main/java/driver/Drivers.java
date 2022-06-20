//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package driver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import utils.Auxiliar;

public abstract class Drivers {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal();
    public static ThreadLocal<String> processDriverName = new ThreadLocal();
    private static String pathDownload = Auxiliar.getProperties("pathDownload");

    public Drivers() {
    }
    public static String getProcessDriverName() {
        return (String)processDriverName.get();
    }
    public void setProcessDriverName(String processDriverName) {
        this.processDriverName.set(processDriverName);
    }
    public static WebDriver getDriver() {
        return (WebDriver)driver.get();
    }
    public void setDriver(WebDriver driver) {
        this.driver.set(driver);
    }
    public static void setConfigurationDownload(DriverManagerType driverManagerType) {
        if (driverManagerType.equals(WebDriverManager.chromedriver().getDriverManagerType())) {
                WebDriverManager.chromedriver().setup();
        } else if (driverManagerType. equals(WebDriverManager.edgedriver())) {
          //  WebDriverManager.edgedriver().config().setEdgeDriverVersion(Auxiliar.getProperties("webdriverversion"));
            WebDriverManager.edgedriver().forceDownload().setup();
        }

    }
}
