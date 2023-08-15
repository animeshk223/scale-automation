package utilities;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class Reports {

    public static String REPORT_HTML = System.getProperty("user.dir") +
            "\\src\\test\\test Output\\Execution Reports\\" + Utility.getDate() + "\\Reports" + Utility.getDatetime() + ".html";

    public static ExtentReports reports = new ExtentReports(REPORT_HTML, false);

    public static ExtentTest test;

    /**
     * @param testName This param will the test case name for particular test case.
     * @param desc     This param is the description of the test cases
     */
    public static void startTest(String testName, String desc) {
        test = reports.startTest(testName, desc);
        addSystemInfo();
        assignAuthor(System.getProperty("user.name"));
        TestScriptCategeory("Regression Suite");
    }

    /**
     * @param desc This param is description of info log that will be flushed in
     *             reports
     */
    public static void info(String Stepname, String desc) {
        //test.log(LogStatus.INFO, desc);
        test.log(LogStatus.INFO, Stepname, desc);
    }


    /**
     * This function distinguishes with same Test Script with different test data
     *
     * @param desc
     */
    public static void DifferentiateTestScipt(String desc) {
        test.log(LogStatus.INFO, "", "<span style='font-weight:bold;color:red'>" + desc + "</span>");
    }


    /**
     * @param desc This param is description of Pass log that will be flushed in
     *             reports This log will be used on success of any validation
     */
    public static void pass(String StepName, String desc) {
        test.log(LogStatus.PASS, StepName, desc);
    }


    /**
     * @param desc This param is description of Failure log that will be flushed
     *             in reports This log will be used on Failure of any validation
     *             or script
     */
    public static void fail(String pagename, String desc) {
        test.log(LogStatus.FAIL, pagename, desc);
    }


    /**
     * @param details
     */
    public static void warning(String details) {
        //test.log(LogStatus.WARNING, details,"p{font-size:20px;} .test{background-color:#000 !important;color:#fff !important;}");
        test.log(LogStatus.WARNING, "<span style='font-weight:bold;color:red'>" + details + "</span>");
    }


    /*
     * This function will end the test case
     */
    public static void endTest() {
        reports.endTest(test);

    }


    /**
     * This function will flush all logs in report
     */
    public static void flush() {
        reports.flush();
    }


    /**
     * This function will flush log with screenshot in report
     *
     * @param details   This param is the details of the log
     * @param imagePath this param will pass the path of the screenshot.
     */
    public static void attachScreenshot(String details, String imagePath) {
        test.log(LogStatus.PASS, details + test.addBase64ScreenShot("data:image/png;base64," + Utility.encodeFileToBase64Binary(imagePath)));
        File file = new File(imagePath);
        file.delete();
    }


    /**
     * This function will flush log with screenshot in report
     *
     * @param details   This param is the details of the log
     * @param imagePath this param will pass the path of the screenshot.
     */
    public static void attachfailScreenshot(String pagename, String details, String imagePath) {

        test.log(LogStatus.FAIL, pagename, details + test.addBase64ScreenShot("data:image/png;base64," + Utility.encodeFileToBase64Binary(imagePath)));
        File file = new File(imagePath);
        file.delete();
    }


    /**
     * @param testscriptType
     */
    public static void TestScriptCategeory(String testscriptType) {
        test.assignCategory(testscriptType);
    }


    /**
     * @param authorName
     */
    public static void assignAuthor(String authorName) {
        test.assignAuthor(authorName);
    }


    /**
     *
     */
    public static void addSystemInfo() {
        Map<String, String> systemInfo = new HashMap<String, String>();
        systemInfo.put("Selenium Version", "3.141.59");
        reports.addSystemInfo(systemInfo);
    }


}