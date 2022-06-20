package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.cucumber.listener.ExtentProperties;

import driver.Drivers;
import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Auxiliar {

    public Auxiliar() {
    }

    public static String getProperties(String propertie) {
        try {
            InputStream inputStream = new FileInputStream("setup.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty(propertie);
        } catch (IOException var3) {
            var3.printStackTrace();
            Assert.fail("Erro ao carregar propriedade");
            return "";
        }
    }

        public static void setProperties(String propertie, String valor) {
            try {
                FileInputStream in = new FileInputStream("setup.properties");
                Properties props = new Properties();
                props.load(in);
                in.close();

                FileOutputStream out = new FileOutputStream("setup.properties");
                props.setProperty(propertie, valor);
                props.store(out, "Configurações Essenciais");
                out.close();

            } catch (IOException var4) {
                var4.printStackTrace();
                Assert.fail("Erro ao carregar propriedade");
            }

        }
        public static void killProcess(String process) {
            try {
                Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

                String line;
                while ((line = input.readLine()) != null) {
                    if (!line.trim().equals("") && line.substring(1, line.indexOf("\"", 1)).equalsIgnoreCase(process)) {
                        Runtime.getRuntime().exec("taskkill /F /IM " + line.substring(1, line.indexOf("\"", 1)));
                    }
                }

                input.close();
            } catch (Exception var4) {
                System.out.println(var4.getMessage());
            }

        }
        public static String getProcessName(DriverManagerType type) {
            List<String> list = new ArrayList();
            switch (type) {
                case CHROME:
                    list = Arrays.asList(WebDriverManager.chromedriver().getBinaryPath().split("\\\\"));
                    break;
                case FIREFOX:
                    list = Arrays.asList(WebDriverManager.firefoxdriver().getBinaryPath().split("\\\\"));
            }

            return (String) ((List) list).get(((List) list).size() - 1);
        }
        public static void sleep(int timeout) {
            try {
                Thread.sleep((long) (timeout * 1000));
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }

        }
        public static WebElement getElementIfVisible(By by, int timeout) throws InterruptedException {
            for (int i = 0; i < 10; i++) {
                if (((JavascriptExecutor) Drivers.getDriver()).executeScript("return document.readyState").equals("complete")) {
                    break;
                } else {
                    Thread.sleep(100);
                }
            }
            Wait wait = new FluentWait(Drivers.getDriver())
                    .withTimeout(Duration.ofSeconds(timeout))
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(Exception.class);

            WebElement element = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(by));
            if (element == null) {
                Assert.fail("Elemento não encontrado na página: " + element);
            }

            return element;
        }
        public static void setup() {
            ExtentProperties extentProperties = ExtentProperties.INSTANCE;
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String userDir = System.getProperty("user.dir");
            extentProperties.setReportPath(userDir +
                    "/target/output/" + timeStamp + "/report.html");
        }
        private static int randomiza(int n ) {
            int ranNum = (int) (Math.random() * n);
            return ranNum;
        }
        private static int mod(int dividendo, int divisor) {
            return (int) Math.round(dividendo - (Math.floor(dividendo / divisor) * divisor));
        }
        public static String gerarCPF(boolean comPontos, int iInicial) {
            int n = 9;
            int n1 = iInicial;
            int n2 = randomiza(n);
            int n3 = randomiza(n);
            int n4 = randomiza(n);
            int n5 = randomiza(n);
            int n6 = randomiza(n);
            int n7 = randomiza(n);
            int n8 = randomiza(n);
            int n9 = randomiza(n);
            int d1 = n9 * 2 + n8 * 3 + n7 * 4 + n6 * 5 + n5 * 6 + n4 * 7 + n3 * 8 + n2 * 9 + n1 * 10;

            d1 = 11 - (mod(d1, 11));

            if (d1 >= 10)
                d1 = 0;

            int d2 = d1 * 2 + n9 * 3 + n8 * 4 + n7 * 5 + n6 * 6 + n5 * 7 + n4 * 8 + n3 * 9 + n2 * 10 + n1 * 11;

            d2 = 11 - (mod(d2, 11));

            String retorno = null;

            if (d2 >= 10)
                d2 = 0;
            retorno = "";

            if (comPontos)
                retorno = "" + n1 + n2 + n3 + '.' + n4 + n5 + n6 + '.' + n7 + n8 + n9 + '-' + d1 + d2;
            else
                retorno = "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + d1 + d2;

            return retorno;


        }
        public static String getDataNow (String format){
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            Date x1 = new Date();

            return sdf.format(x1);
        }
        public static String getDataFixa (String format, String Data) throws ParseException{
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            Date x1 = sdf.parse(Data);

            return sdf.format(x1);
        }
        public static String getDataHorasParaFrente (String format, int quantHoras){
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            Date x1 = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(x1);
            cal.add(Calendar.HOUR_OF_DAY, quantHoras);
            x1 = cal.getTime();

            return sdf.format(x1);
        }
        public static String getDataDiasParaFrente(String format, int quantDias) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            Date x1 = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(x1);
            cal.add(Calendar.DAY_OF_WEEK, quantDias);
            x1 = cal.getTime();

            return sdf.format(x1);
        }
        public static String getDataSemanasParaFrente(String format, int quantSemanas) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            Date x1 = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(x1);
            cal.add(Calendar.WEEK_OF_MONTH, quantSemanas);
            x1 = cal.getTime();

            return sdf.format(x1);
        }
        public static String getDataMesesParaFrente(String format, int quantMeses) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            Date x1 = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(x1);
            cal.add(Calendar.MONTH, quantMeses);
            x1 = cal.getTime();

            return sdf.format(x1);

        }
        public static String getDataAnosParaFrente(String format, int quantAnos) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            Date x1 = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(x1);
            cal.add(Calendar.YEAR, quantAnos);
            x1 = cal.getTime();

            return sdf.format(x1);
        }
        public static String getDataHorasParaTras (String format, int quantHoras){
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            Date x1 = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(x1);
            cal.add(Calendar.HOUR_OF_DAY, -quantHoras);
            x1 = cal.getTime();

            return sdf.format(x1);
        }
        public static String getDataDiasParaTras (String format, int quantDias) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            Date x1 = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(x1);
            cal.add(Calendar.DAY_OF_WEEK, -quantDias);
            x1 = cal.getTime();

            return sdf.format(x1);
        }
        public static String getDataSemanasParaTras (String format, int quantSemanas) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            Date x1 = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(x1);
            cal.add(Calendar.WEEK_OF_MONTH, -quantSemanas);
            x1 = cal.getTime();

            return sdf.format(x1);
        }
        public static String getDataMesesParaTras (String format, int quantMeses) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            Date x1 = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(x1);
            cal.add(Calendar.MONTH, -quantMeses);
            x1 = cal.getTime();

            return sdf.format(x1);

        }
        public static String getDataAnosParaTras (String format, int quantAnos) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            Date x1 = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(x1);
            cal.add(Calendar.YEAR, -quantAnos);
            x1 = cal.getTime();

            return sdf.format(x1);
        }
        public static void tempo(int tempo) {
        int temposeg = tempo;
        temposeg = temposeg * 1000;
        try {
            Thread.sleep(temposeg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
            }
