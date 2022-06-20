//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package driver;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.Collections;
import java.util.HashMap;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.Auxiliar;

public class AppWeb extends Drivers {
    String sistemaOperacional = System.getProperty("os.name").toLowerCase();
    private String url = Auxiliar.getProperties("url");
    public static final String HEADLESS = "headless";
    public static final String BROWSER = "browser";

    public AppWeb() {
    }

    public void setUpDriver(DriverManagerType driverManagerType, String headless) {
        if (getDriver() == null || getDriver().toString().contains("null")) {
            switch(driverManagerType) {
                case CHROME:
                    setConfigurationDownload(DriverManagerType.CHROME);
                    byte var4 = -1;
                    switch(headless.hashCode()) {
                        case -1115062407:
                            if (headless.equals("headless")) {
                                var4 = 0;
                            }
                            break;
                        case 150940456:
                            if (headless.equals("browser")) {
                                var4 = 1;
                            }
                    }

                    switch(var4) {
                        case 0:
                            this.initChromeHeadless();
                            return;
                        case 1:
                            this.initChromeDriver();
                            return;
                        default:
                            return;
                    }
                case FIREFOX:
                    setConfigurationDownload(DriverManagerType.FIREFOX);
                    this.initFirefoxDriver();
                    break;
                case EDGE:
                    setConfigurationDownload(DriverManagerType.EDGE);
                    this.initEdgeDriver();
            }
        }

    }

    public void initChromeDriver() {

        HashMap<String, Object> chromePrefs = new HashMap();
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        if (Auxiliar.getProperties("userAgent").isEmpty() ==true){
        }else{
            options.addArguments("user-agent='"+ Auxiliar.getProperties("userAgent")+"'");
        }
        options.addArguments(new String[]{"start-maximized", "--ignore-ssl-errors", "–-no-sandbox", "ignore-certicate-errors"});
        options.addArguments(new String[]{"--disable-extensions"});
        options.addArguments("--disable-infobars");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("profile.default_content_setting_values.media_stream_mic",1);
        chromePrefs.put("profile.default_content_setting_values.media_stream_camera",1);
//        Auxiliar.getProperties("geo");
//        if (Auxiliar.getProperties("geo").equals("1")){
//            chromePrefs.put("profile.default_content_setting_values.geolocation",1);
//        }else{
//            chromePrefs.put("profile.default_content_setting_values.geolocation",2);
//
//        }
        chromePrefs.put("profile.default_content_setting_values.notifications",1);
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("credentials_enable_service", false);
        options.setExperimentalOption("prefs", chromePrefs);
        setDriver(this.setUrl(new ChromeDriver(options)));
        setProcessDriverName(Auxiliar.getProcessName(DriverManagerType.CHROME));
    }

    public void initChromeHeadless() {
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        options.addArguments(new String[]{"window-size=1366,768", "--disable-dev-shm-usage", "--no-sandbox", "disable-extensions", "--ignore-ssl-errors", "disable-gpu", "headless"});
        setDriver(this.setUrl(new ChromeDriver(options)));
        setProcessDriverName(Auxiliar.getProcessName(DriverManagerType.CHROME));
    }


    public void initFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.download.manager.showWhenStarting", false);
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");
        options.addPreference("browser.helperApps.neverAsk.openFile", "");
        options.addPreference("browser.helperApps.alwaysAsk.force", false);
        options.addPreference("browser.download.manager.alertOnEXEOpen", false);
        options.addPreference("browser.download.manager.focusWhenStarting", false);
        options.addPreference("browser.download.manager.useWindow", false);
        options.addPreference("browser.download.manager.showAlertOnComplete", false);
        options.addPreference("browser.download.manager.closeWhenDone", true);
        options.addPreference("pdfjs.disabled", true);
        setDriver(this.setUrl(new FirefoxDriver(options)));
        setProcessDriverName(Auxiliar.getProcessName(DriverManagerType.FIREFOX));
    }

    public void initEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeDriver edgeDriver = new EdgeDriver();
        edgeDriver.manage().window().maximize();
        setDriver(this.setUrl(edgeDriver));
    }

    private WebDriver setUrl(WebDriver driver) {
        try {
            if (!this.url.isEmpty() && this.url != null) {
                driver.get(this.url);
            }
        } catch (Exception var3) {
            Assert.fail("Não foi possível carregar a url do arquivo de configuração");
        }

        return driver;
    }

    public static void quitDriver() throws InterruptedException {
            getDriver().quit();
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                Auxiliar.killProcess(getProcessDriverName());
            }

        }
    }

