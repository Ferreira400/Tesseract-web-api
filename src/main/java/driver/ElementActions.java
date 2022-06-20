//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package driver;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import driver.Drivers;

public class ElementActions {
    public ElementActions() {
    }
    public static WebElement getElement(By by, int timeout) {
        WebElement element = (WebElement)(new WebDriverWait(Drivers.getDriver(), (long)timeout)).until(ExpectedConditions.elementToBeClickable(by));
        if (element == null) {
            Assert.fail("Elemento não encontrado na página: " + element);
        }
        return element;
    }
    public static void click(By by, int timeout) {
        getElement(by, timeout).click();
    }
    public static void sendKeys(By by, int timeout, String text) {
        WebElement element = getElement(by, timeout);
        element.click();
        element.clear();
        element.sendKeys(new CharSequence[]{text});
    }
    public static boolean isAlertPresent() {
        try {
            Drivers.getDriver().switchTo().alert();
            return true;
        } catch (Exception var1) {
            return false;
        }
    }
    public static void waitElement(int timeout, By by) {
        int time = 0;
        boolean flag = false;

        while(time < timeout * 1000) {
            try {
                Drivers.getDriver().findElement(by);
                flag = true;
                break;
            } catch (Exception var6) {
                time += 100;

                try {
                    Thread.sleep(100L);
                } catch (InterruptedException var5) {
                    var5.printStackTrace();
                }
            }
        }

        if (time >= timeout * 1000 && !flag) {
            Assert.fail("Elemento não encontrado na página");
        }

    }
}
