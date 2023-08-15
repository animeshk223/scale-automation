package pageFactories.inventory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Reports;
import utilities.Utility;

import java.util.List;

public class PgCycleCountPlanInsight {
    public static WebDriver driver;
    static String actionDropDownMenu_xpath = "//*[text()='Actions']";

    public static void openFilterSection() throws Exception {
        String filterIcon_xpath = "//a[@data-toggle='search']";
        String filterPane_xpath = "//div[@data-name='SearchPane']";
        String basicCriteria_xpath = "//a[text()='Basic criteria']/..";


        try {
            if (Utility.getLocatorWithoutWait(filterPane_xpath, "xpath").
                    getAttribute("style").equalsIgnoreCase("display: none;")) {
                Utility.getLocator(filterIcon_xpath, "xpath").click();
            }
            if (Utility.getLocator(basicCriteria_xpath, "xpath").
                    getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                Utility.getLocator(basicCriteria_xpath, "xpath").click();
            }
            Reports.pass("Open Filter pane and Expand Basic Criteria",
                    "Basic Criteria expanded successfully.");
        } catch (Exception e) {
            Reports.fail("Basic Criteria is not expanded.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static void clickOnPlayButton() throws Exception {
        String playButton_id = "InsightMenuApply";
        try {
            Utility.getLocator(playButton_id, "id").click();
            Reports.pass("Click on Play button", "Clicked successfully.");
        } catch (Exception e) {
            Reports.fail("Play button is not clicked", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void enterPlanNumberInFilterPane(String itemNumber) throws Exception {
        String planNumberField_id = "BasicCriteriaPlanNumber";
        try {
            Utility.getLocator(planNumberField_id, "id").sendKeys(itemNumber);
            Reports.pass("Enter Plan number <b>" + itemNumber + "</b> in basic criteria", "Entered successfully.");
        } catch (Exception e) {
            Reports.fail("Plan number is not entered", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void selectPlan(String planNumber) throws Exception {
        //String checkboxOfInventory_xpath = "//*[text()='" + planNumber + "']/..//span[@name='chk']";
        String checkboxOfInventory_xpath = "//*[text()='" + planNumber + "']/..//..//span[@name='chk']";
        String nameOfInventory_xpath = "//*[text()='" + planNumber + "']";
        try {
            if (Utility.getLocator(checkboxOfInventory_xpath, "xpath").
                    getAttribute("data-chk").equalsIgnoreCase("off")) {
                Utility.getLocator(nameOfInventory_xpath, "xpath").click();
            }
            Reports.pass("Select checkbox of <b>" + planNumber + "</b> number.", "Selected successfully.");
        } catch (Exception e) {
            Reports.fail(planNumber + " plan is not selected.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }


    public static void clickOnRelease() throws Exception {
        String release_xpath = "//a[text()='Release']";
        try {
            Utility.getLocator(actionDropDownMenu_xpath, "xpath").click();
            Utility.getLocator(release_xpath, "xpath").click();
            Reports.pass("Go to Actions menu and click on Release.", "Clicked successfully.");

        } catch (Exception e) {
            Reports.fail("Release is not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void clickOnQuickPlan() throws Exception {
        String QuickPlan_xpath = "//a[text()='Quick plan']";
        try {
            Utility.getLocator(actionDropDownMenu_xpath, "xpath").click();
            Utility.getLocator(QuickPlan_xpath, "xpath").click();
            Reports.pass("Go to Actions menu and click on Quick plan.", "Clicked successfully.");

        } catch (Exception e) {
            Reports.fail("Quick plan is not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void ItemSelectionCriteria(String ItemToBeSelect) throws Exception {
        List<WebElement> listOfElements;
        String PlanPreferences_xpath = "//ul[@class='sidebar-name']//li//a";
        try {
            listOfElements=Utility.getLocatorList(PlanPreferences_xpath,"xpath");
            for(int i=0;i<listOfElements.size();i++){
                if(listOfElements.get(i).getText().equals(ItemToBeSelect)){
                   listOfElements.get(i).click();
                   Reports.pass("Click on item.", ItemToBeSelect+" clicked successfully.");
                   break;
                }
            }
        } catch (Exception e) {
            Reports.fail(ItemToBeSelect+" is not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }

    public static void enterDescriptionInPlanPreferences(String Loc) throws Exception {
        String Description_xpath = "//div[@data-name='PlanPreferencesInfoSubAccordion']//div[@data-name='PlanPreferencesDescriptionValue']//div//input[@id='PlanPreferencesDescriptionValueEditingInput']";
        try {
            Utility.getLocator(Description_xpath, "xpath").sendKeys(Loc);
            Reports.pass("Enter description in Plan preferences.", "Entered successfully.");

        } catch (Exception e) {
            Reports.fail("Description is not entered.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void enterMaxCountInPlanPreferences(String maxCount) throws Exception {
        String MaxCount_xpath = "//div[@data-name='PlanPreferencesInfoSubAccordion']//div[@data-name='PlanPreferencesDescriptionValue']//div//input[@id='PlanPreferencesDescriptionValueEditingInput']";
        try {
            Utility.getLocator(MaxCount_xpath, "xpath").clear();
            Utility.getLocator(MaxCount_xpath, "xpath").sendKeys(maxCount);
            Reports.pass("Enter max count in Plan preferences.", "Entered successfully.");

        } catch (Exception e) {
            Reports.fail("Max count is not entered.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void enterGroupCountInPlanPreferences(String groupCount) throws Exception {
        String GroupCount_xpath = "//div[@data-name='PlanPreferencesInfoSubAccordion']//div[@data-name='PlanPreferencesCountsPerGroupValue']//div//input[@id='PlanPreferencesCountsPerGroupValueEditingInput']";
        try {
            Utility.getLocator(GroupCount_xpath, "xpath").clear();
            Utility.getLocator(GroupCount_xpath, "xpath").sendKeys(groupCount);
            Reports.pass("Enter group count in Plan preferences.", "Entered successfully.");

        } catch (Exception e) {
            Reports.fail("Group count is not entered.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void enterLocationInLocationInventory(String InvLoc) throws Exception {
        String location_xpath = "//input[@id='LocationInventoryLocationValueEditingInput']";
        try {
            Utility.getLocator(location_xpath, "xpath").sendKeys(InvLoc);
            Reports.pass("Enter location in location inventory.", "Entered successfully.");

        } catch (Exception e) {
            Reports.fail("location is not entered.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void enterItemInLocationInventory(String Item) throws Exception {
        String location_xpath = "//input[@id='LocationInventoryItemValueEditingInput']";
        try {
            Utility.getLocator(location_xpath, "xpath").sendKeys(Item);
            Reports.pass("Enter Item in location inventory.", "Entered successfully.");

        } catch (Exception e) {
            Reports.fail("Item is not entered.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void enterCompanyInLocationInventory(String company) throws Exception {
        String company_xpath = "//span[@id='LocationInventoryCompanyValue']//input[@data-localeid='placeHolder']";
        String companyLabel_xpath = "//label[text()='Company']";
        try {

            Utility.getLocator(company_xpath, "xpath").sendKeys(company);
            Thread.sleep(2000);
            Utility.getLocator(companyLabel_xpath, "xpath").click();
            Reports.pass("Enter comapany in location inventory.", "Entered successfully.");

        } catch (Exception e) {
            Reports.fail("Company is not entered.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void clickOnCreate() throws Exception {
        String create_xpath = "//a[text()='Create']";
        try {
            Utility.getLocator(create_xpath, "xpath").click();
            Thread.sleep(12000);
            Reports.pass("Click on Create button.", "Clicked successfully.");

        } catch (Exception e) {
            Reports.fail("Create button is not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void verifyConfirmationAndClickOnYes() throws Exception {
        String confirmationDialog_xpath = "//div[@class='modal-content']//h4[text()='Confirmation']";
        String confirmationDialogYesButton_xpath = "//div[@class='modal-footer']//button[@id='confirmPositive']";
        try {
            if(Utility.getLocator(confirmationDialog_xpath, "xpath").isDisplayed()){
                Reports.pass("Visibility of confirmation box", "Visible.");
                Utility.getLocator(confirmationDialogYesButton_xpath, "xpath").click();
                Reports.pass("Click on yes button.", "Yes button clicked successfully.");
                Thread.sleep(8000);
            }
        } catch (Exception e) {
            Reports.info("Confirmation dialog box is not visible", e.toString());
            e.printStackTrace();
            //throw (new Exception(e));
        }
    }
    public static void WaitTillCycleCountMessageVisibility()  {
        String message_xpath = "//div[text()='Cycle count quick plans submitted for processing.']";
        try{
            if(Utility.getLocator(message_xpath, "xpath").isDisplayed()){
                WebDriverWait wait=new WebDriverWait(driver,500);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(message_xpath)));
            }
        }catch(Exception e){
            WebDriverWait wait=new WebDriverWait(driver,10);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(message_xpath)));
        }
    }
    public static void onZeroCycleCountToggle( ) throws Exception {
        String Toggle_xpath = "//label[@data-resourcevalue='Cycle Count Zero']//..//div//div[@class='toggle-group']//label[text()=' No']";
        try {
            Utility.getLocator(Toggle_xpath, "xpath").click();
            Reports.pass("Click on Zero cycle count toggle button", "Clicked successfully.");

        } catch (Exception e) {
            Reports.fail("Zero cycle count toggle button not clicked.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void enterLP(String LP) throws Exception {
        String location_xpath = "//input[@id='LocationInventoryLicensePlateValueEditingInput']";
        try {
            Utility.getLocator(location_xpath, "xpath").click();
            Utility.getLocator(location_xpath, "xpath").sendKeys(LP);
            Reports.pass("Enter Licence plate number  in location inventory.", "Entered successfully.");

        } catch (Exception e) {
            Reports.fail("Licence plate number is not entered.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
    }
    public static void waitForDisappearanceOfToastMsg(){
        String popup_id = "toast-container";
        try{
            while(Utility.getLocatorWithoutWait(popup_id,"id").isDisplayed()){
                Thread.sleep(500);
            }
        }catch(Exception e){
            System.out.println("toast disappeared");
        }

    }

}
