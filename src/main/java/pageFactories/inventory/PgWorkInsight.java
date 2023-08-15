package pageFactories.inventory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utilities.Reports;
import utilities.Utility;

import java.util.List;

public class PgWorkInsight {
    public static String getOpenWorkUnitData(String fetchItem) throws Exception {
        List<WebElement> listOfElements_Header;
        List<WebElement> listOfElements_Row;
        String returnItem = null;
        String workHeader_xpath = "//table[@id='ListPaneDataGrid_headers']//tr[1]//th[contains(@id,'ListPaneDataGrid_')]";
        String work_xpath = "//table[@data-dbtable='Metadata_Insight_Work_View']//tr";
        try {
            listOfElements_Header= Utility.getLocatorList(workHeader_xpath,"xpath");
            listOfElements_Row= Utility.getLocatorList(work_xpath,"xpath");

            for(int i=0;i<listOfElements_Header.size();i++){
                if(listOfElements_Header.get(i).getAttribute("aria-label").equals("Work unit") && fetchItem.equals("Work unit")){

                    for(int j=1;j<=listOfElements_Row.size();j++){
                        List<WebElement> ll=listOfElements_Row.get(j).findElements(By.xpath("//td[@aria-describedby='ListPaneDataGrid_WORK_UNIT']//a"));
                        returnItem=ll.get(1).getText();
                        Reports.pass("Work unit on open work.", "Work unit is "+returnItem);
                        if(returnItem!=null){
                            break;
                        }
                    }
                }
                if(listOfElements_Header.get(i).getAttribute("aria-label").equals("Reference id") && fetchItem.equals("Reference id") ){
                    for(int j=1;j<=listOfElements_Row.size();j++){
                        List<WebElement> ll=listOfElements_Row.get(j).findElements(By.xpath("//td[@aria-describedby='ListPaneDataGrid_REFERENCE_ID']"));
                        returnItem=ll.get(1).getText();
                        Reports.pass("Reference id  on open work.", "Reference id is "+returnItem);
                        if(returnItem!=null){
                            break;
                        }
                    }
                }
                    if(returnItem!=null){
                       break;
                    }
            }
        } catch (Exception e) {
            Reports.fail(fetchItem+" is not found.", e.toString());
            e.printStackTrace();
            throw (new Exception(e));
        }
        return returnItem;
    }
}