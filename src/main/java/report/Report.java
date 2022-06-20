//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package report;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.cucumber.listener.Reporter;

import driver.Drivers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;


public class Report {
    public Report() {
    }

    public static String takeScreenshot() {
        return (String)((TakesScreenshot)Drivers.getDriver()).getScreenshotAs(OutputType.BASE64);
    }

    public static void appendToReport() {
        Reporter.addStepLog("<div align=\"right\"><ul class='screenshots right'><li><img data-featherlight=\"image\" href=\"data:image/png;base64, " + takeScreenshot() + "\"  src=\"data:image/png;base64, " + takeScreenshot() + "\" alt=\"Red dot\" width=\"5%\" /></img></li></ul></div>");
    }

    public static String appendToJUnitReport() {
        return "<div align=\"left\"><ul style=\"margin: 0px 0px\" class='screenshots right' ><li><img data-featherlight=\"image\" href=\"data:image/png;base64, " + takeScreenshot() + "\"  src=\"data:image/png;base64, " + takeScreenshot() + "\" alt=\"Red dot\" width=\"8%\" /></img></li></ul>";
    }

    public static void appendToReportElementHighlight(WebElement element) {
        ((JavascriptExecutor)Drivers.getDriver()).executeScript("arguments[0].style.border='3px solid red'", new Object[]{element});
        appendToReport();
        ((JavascriptExecutor)Drivers.getDriver()).executeScript("arguments[0].setAttribute('style', arguments[1]);", new Object[]{element, ""});
    }
}
