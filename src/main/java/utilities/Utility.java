package utilities;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.*;

public class Utility extends LaunchEnvironment {

    public static Properties prop;
    public static InputStream file;
    public static String value, data, filepath;
    public static String loctype;
    public static WebElement loct;
    public static Properties stbConfigRepository;
    public static Properties stbLocRepository;
    static int waiting = 0;


    // ***************************************************************************
    /**
     * This function loads the property file in buffer
     *
     * @param fileLocation Enter the file location of the property file as parameter.
     * @return Properties. This function will return the object of the loaded
     *         property file.
     * @exception FileNotFoundException,
     *                IOException
     */
    // ***************************************************************************
	
	public static String loadProperty(String fileLocation, String var) {
		prop = new Properties();
		try {
			file = new FileInputStream(fileLocation);
			prop.load(file);
			prop.getProperty(var);
		} catch (IOException e) {
			Reports.fail("Load Property",e.toString());
			e.printStackTrace();
		}
        return String.valueOf(prop);
	}


    /************************************************************************************************
     * This function will define specific locater type for keys in Locators
     * property file
     *
     * @param key Pass the value of the locator
     * @param type pass the type of locator like xpath, lnktxt, id, name
     * @return WebElement This function will return WebElement based on specific
     *         locator type.
     *************************************************************************************************/
    public static WebElement getLocator(String key, String type){
        fluentWait(10, 500, key, type);

        if (type.equalsIgnoreCase("id")) {
            loct = driver.findElement(By.id(key));
            ElementHighlight(loct);
        } else if (type.equalsIgnoreCase("xpath")) {
            loct = driver.findElement(By.xpath(key));
            ElementHighlight(loct);
        } else if (type.equalsIgnoreCase("lnktxt")) {
            loct = driver.findElement(By.linkText(key));
            ElementHighlight(loct);
        } else if (type.equalsIgnoreCase("name")) {
            loct = driver.findElement(By.name(key));
            ElementHighlight(loct);
        } else if (type.equalsIgnoreCase("partlnktxt")) {
            loct = driver.findElement(By.partialLinkText(key));
            ElementHighlight(loct);
        } else if (type.equalsIgnoreCase("css")) {
            loct = driver.findElement(By.cssSelector(key));
            ElementHighlight(loct);
        } else if (type.equalsIgnoreCase("tagname")) {
            loct = driver.findElement(By.tagName(key));
            ElementHighlight(loct);
        } else {
            loct = driver.findElement(By.xpath(key));
            ElementHighlight(loct);
            //System.out.println("Locators not matched");
        }

        return loct;

    }

    public static WebElement getLocatorWithoutWait(String key, String type) {

        //fluentWait(10, 500, key, type);

        if (type.equalsIgnoreCase("id")) {
            loct = driver.findElement(By.id(key));
            //ElementHighlight(loct);
        } else if (type.equalsIgnoreCase("xpath")) {
            loct = driver.findElement(By.xpath(key));
            //ElementHighlight(loct);
        } else if (type.equalsIgnoreCase("lnktxt")) {
            loct = driver.findElement(By.linkText(key));
            //ElementHighlight(loct);
        } else if (type.equalsIgnoreCase("name")) {
            loct = driver.findElement(By.name(key));
            //ElementHighlight(loct);
        } else if (type.equalsIgnoreCase("partlnktxt")) {
            loct = driver.findElement(By.partialLinkText(key));
            //ElementHighlight(loct);
        } else if (type.equalsIgnoreCase("css")) {
            loct = driver.findElement(By.cssSelector(key));
            //ElementHighlight(loct);
        } else if (type.equalsIgnoreCase("tagname")) {
            loct = driver.findElement(By.tagName(key));
            //ElementHighlight(loct);
        } else {
            loct = driver.findElement(By.xpath(key));
            //ElementHighlight(loct);
            //System.out.println("Locators not matched");
        }
        return loct;
    }


    /************************************************************************
     * @param key Pass locator value
     * @param type pass the type of locators like xpath, lnktxt, id.
     * @return List of Web Elements.
     ************************************************************************/
    public static List<WebElement> getLocatorList(String key, String type) {
        List<WebElement> listOfElements = null;
        try {
            fluentWait(5, 500, key, type);

            if (type.equalsIgnoreCase("id")) {
                listOfElements = driver.findElements(By.id(key));

            } else if (type.equalsIgnoreCase("xpath")) {
                listOfElements = driver.findElements(By.xpath(key));

            } else if (type.equalsIgnoreCase("lnktxt")) {
                listOfElements = driver.findElements(By.linkText(key));

            } else if (type.equalsIgnoreCase("name")) {
                listOfElements = driver.findElements(By.name(key));

            } else if (type.equalsIgnoreCase("partlnktxt")) {
                listOfElements = driver.findElements(By.partialLinkText(key));

            } else if (type.equalsIgnoreCase("css")) {
                listOfElements = driver.findElements(By.cssSelector(key));

            } else if (type.equalsIgnoreCase("tagname")) {
                listOfElements = driver.findElements(By.tagName(key));

            } else {
                listOfElements = driver.findElements(By.xpath(key));

            }
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
        }
        return listOfElements;

    }

    /*******************************************************
     * @param key pass the value of locator
     * @param type pass the type locator like xpath, ID, name, Lnktxt
     * @param timeoutInSecond enter the time of wait till the element found.
     ******************************************************/
    public static void waitExplicitly(String key, String type, int timeoutInSecond) {

        System.out.println("Waiting Explicitly");

        WebDriverWait explicitWait = new WebDriverWait(driver, timeoutInSecond);

        if (type.equalsIgnoreCase("id")) {
            explicitWait.until(ExpectedConditions.elementToBeClickable(By.id(key)));
        } else if (type.equalsIgnoreCase("xpath")) {
            explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath(key)));
        } else if (type.equalsIgnoreCase("lnktxt")) {
            explicitWait.until(ExpectedConditions.elementToBeClickable(By.linkText(key)));
        } else if (type.equalsIgnoreCase("name")) {
            explicitWait.until(ExpectedConditions.elementToBeClickable(By.name(key)));
        } else if (type.equalsIgnoreCase("partlnktxt")) {
            explicitWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(key)));
        } else if (type.equalsIgnoreCase("css")) {
            explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(key)));
        } else if (type.equalsIgnoreCase("tagname")) {
            explicitWait.until(ExpectedConditions.elementToBeClickable(By.tagName(key)));
        } else {
            explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath(key)));
        }

    }


    /*************************************************************************************
     * This function will get screenshot on Success of executed Test Cases.
     *
     * @return filepath This function will return the String path of the
     *         screenshot.
     * @exception IOException
     **************************************************************************************/
    public static String getSuccessScreenshot() {
        try {
            filepath = null;
            File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            filepath = System.getProperty("user.dir") + "\\Screenshots\\SuccessScreenshot\\"
                    + System.currentTimeMillis() + ".png";
            FileUtils.copyFile(file, new File(filepath));
        } catch (IOException e) {
            Reports.fail("Get Success Screenshot", e.toString());
            e.printStackTrace();
        }
        return filepath;
    }

    /***************************************************************************************
     * This function will close the current session
     *
     * @throws IOException
     **************************************************************************************/
    public static void closeBrowser() throws IOException {
        try {
            System.out.println("Closing Browser");
            System.out.println("**************************************************************************************************");
            driver.close();

        } catch (Exception e) {
            Reports.fail("Close Browser", e.toString());
            e.printStackTrace();

        }

    }

    /***************************************************************************************
     * This function will take screenshot on failure of test cases.
     *
     * @param errorMessage pass the message which will be include in the name of taken screenshot
     * @return method will return the path of the file created.
     *
     **************************************************************************************/
    public static String getfailScreenshot(String errorMessage) {
        try {
            filepath = null;
            File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            filepath = System.getProperty("user.dir") + "\\Screenshots\\FailedScreenshot\\" + errorMessage + System.currentTimeMillis()
                    + ".png";
            FileUtils.copyFile(file, new File(filepath));
        } catch (Exception e) {
            Reports.fail("get Fail ScreenShot", e.toString());
            e.printStackTrace();
        }
        return filepath;
    }

    /***************************************************************************************
     * This function is used to highlight element on GUI where the current and
     * previous action has been performed.
     *
     * @param element pass the web element which need to be highlighted.
     **************************************************************************************/
    public static void ElementHighlight(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('style', 'background:yellow; border:2px solid red;');", element);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Reports.fail("Element HighLight", e.toString());
            e.printStackTrace();
        }
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', 'border: solid 2px green ');",
                element);
    }

    /*******************************************************************************************
     * This Function will click hidden elements too.
     * @param element pass the web element
     ******************************************************************************************/
    public static void clickByJavaScript(WebElement element) {
        ElementHighlight(element);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", element);

    }


    /***************************************************************************************
     * This function will give you System date time in string format
     *
     * @return This function will return date time in String format.
     **************************************************************************************/
    public static String getDatetime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String founddate = dateFormat.format(date);
        String[] parts = founddate.split(" ");
        String[] appenderpart1 = parts[0].split("/");
        String[] appenderpart2 = parts[1].split(":");
        String appender = appenderpart1[1] + appenderpart1[2] + appenderpart2[0] + appenderpart2[1] + appenderpart2[2];
        return appender;
    }

    /***************************************************************************************
     * This function will give you System date time in string format
     *
     * @return This function will return date time in String format.
     **************************************************************************************/
    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String founddate = dateFormat.format(date);
        String[] parts = founddate.split(" ");
        String[] appenderpart1 = parts[0].split("/");
        String appender = appenderpart1[1] + "-" + appenderpart1[2] + "-"
                + appenderpart1[0];
        return appender;
    }

    /**
     * this function is used to put the wait till the element become clickable.
     *
     * @param duration          total time to wait in seconds.
     * @param pollingInMilliSec to wait before next polling in milli-second.
     * @param key               location of the element to be clickable.
     * @param type              type of the locator like xpath, id, lnktxt.
     */
    public static void fluentWait(int duration, int pollingInMilliSec, String key, String type) {

        System.out.println("Under fluent wait for: " + key);
        Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(duration))
                .pollingEvery(Duration.ofMillis(pollingInMilliSec)).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class).ignoring(WebDriverException.class)
                .ignoring(ElementClickInterceptedException.class);

        if (type.equalsIgnoreCase("id")) {
            fluentWait.until(ExpectedConditions.elementToBeClickable(By.id(key)));
        } else if (type.equalsIgnoreCase("xpath")) {
            fluentWait.until(ExpectedConditions.elementToBeClickable(By.xpath(key)));
        } else if (type.equalsIgnoreCase("lnktxt")) {
            fluentWait.until(ExpectedConditions.elementToBeClickable(By.linkText(key)));
        } else if (type.equalsIgnoreCase("name")) {
            fluentWait.until(ExpectedConditions.elementToBeClickable(By.name(key)));
        } else if (type.equalsIgnoreCase("partlnktxt")) {
            fluentWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(key)));
        } else if (type.equalsIgnoreCase("css")) {
            fluentWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(key)));
        } else if (type.equalsIgnoreCase("tagname")) {
            fluentWait.until(ExpectedConditions.elementToBeClickable(By.tagName(key)));
        } else {
            fluentWait.until(ExpectedConditions.elementToBeClickable(By.xpath(key)));
        }
    }

    /*******************************************************************************************
     * This Function used to select drop down options by value.
     * @param value
     * @param key
     ******************************************************************************************/

    public static void selectDropDown(String key, String type, String value) {

        Select drpByValue = new Select(getLocator(key, type));
        drpByValue.selectByVisibleText(value);

    }

    /*******************************************************************************************
     * This Function used to select drop down options by Index.
     * @param index
     * @param key
     ******************************************************************************************/

    public static void selectDropDown(String key, String type, int index) {

        Select drpByValue = new Select(getLocator(key, type));
        drpByValue.selectByIndex(index);

    }

    /*******************************************************************************************
     * This Function will scroll page till the element is visible
     * @author abdul.samad
     * @param elem pass the element
     ******************************************************************************************/
    public static void scrollTillView(WebElement elem) {

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);
    }

    /*******************************************************************************************
     * This Function will drag and element from key1 and will drop to key2
     * @author abdul.samad
     * @param elem , loc2
     ******************************************************************************************/
    public static void dragAndDrop(WebElement elem, String loc2, String type) {
        scrollTillView(elem);
        Actions act = new Actions(driver);

        act.dragAndDrop(elem, getLocator(loc2, type)).build().perform();

    }

    /********************************************************
     * This function is used to convert a file into Base64 format.
     * @param imagePath
     * @return encoded base64 string of image
     ****************************************************/

    public static String encodeFileToBase64Binary(String imagePath) {
        File f = new File(imagePath);
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(f);
            byte[] bytes = new byte[(int) f.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return encodedfile;
    }

    /**
     * This method is used to hover the mouse to the element.
     *
     * @param key  Pass the value of the locator
     * @param type pass the type of locator like xpath, lnktxt, id, name
     */
    public static void hoverMouseToElement(String key, String type) {
        Actions actions = new Actions(driver);
        actions.moveToElement(getLocator(key, type)).build().perform();
        System.out.println("Element move to the element: " + key);
    }

    /**
     * This method is used to switch between two tabs only.
     *
     */
    public static String switchTab(){
        String parent = driver.getWindowHandle();
        Set<String> tabs = driver.getWindowHandles();
        for (String tab: tabs
             ) {
            if(!tab.equalsIgnoreCase(parent)){
                driver.switchTo().window(tab);
            }
        }
        if(driver.getWindowHandle().equalsIgnoreCase(parent)){
            switchTab();
        }
        return parent;
    }
}
