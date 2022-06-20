//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package driver;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
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
        if (pathDownload.isEmpty()) {
            WebDriverManager.chromedriver().config().setTargetPath(System.getProperty("user.dir") + "\\target\\download\\");
        } else {
            WebDriverManager.chromedriver().config().setTargetPath(pathDownload);
        }

        String webDriverVersion = Auxiliar.getProperties("webdriverversion");
        if (driverManagerType.equals(DriverManagerType.CHROME)) {
            if (!webDriverVersion.isEmpty()) {
                WebDriverManager.chromedriver().config().setChromeDriverVersion(Auxiliar.getProperties("webdriverversion"));
                WebDriverManager.chromedriver().setup();
            }
        } else if (driverManagerType.equals(DriverManagerType.EDGE) && !webDriverVersion.isEmpty()) {
            WebDriverManager.edgedriver().config().setEdgeDriverVersion(Auxiliar.getProperties("webdriverversion"));
            WebDriverManager.edgedriver().forceDownload().setup();
        }

    }
}
