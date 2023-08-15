package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;

public class ExcelSheetHandler {

    public static XSSFWorkbook workbook;
    public static XSSFSheet sheet;
    public static XSSFCell excelData;
    public static XSSFRow row;
    public static XSSFCell cell;
    public static InputStream file;
    public static String value, data, filepath;
    public static FileOutputStream fout;
    static HashMap<String, Integer> columnIndex = new HashMap<String, Integer>();

    /*************************************************************************************************************
     * This function will set status of Test Execution status in Excel sheet
     * @sheetname pass the sheet name
     * @rownum enter row number
     * @status enter status Passed, Failed, Skipped
     *************************************************************************************************************/
    public static void setScriptStatus(String sheetName, int rowNum, String status) {
        file = null;
        try {
            file = new FileInputStream(filepath);
        } catch (FileNotFoundException e) {
            Reports.fail("", e.toString());
            e.printStackTrace();

        }
        try {
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet(sheetName);
        } catch (IOException e) {
            Reports.fail("", e.toString());
            e.printStackTrace();
        }
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells() - 1;

        row = sheet.getRow(rowNum);
        cell = row.createCell(colCount);
        cell.setCellValue(status);

        CellStyle borderStyle = workbook.createCellStyle();
        if (status.equalsIgnoreCase("Passed")) {
            borderStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            borderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(borderStyle);
        } else if (status.equalsIgnoreCase("Failed")) {
            borderStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            borderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(borderStyle);
        } else if (status.equalsIgnoreCase("Skipped")) {
            borderStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            borderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(borderStyle);
        } else {
            System.out.println("Unable to set background color for cell.");
        }

        try {
            fout = new FileOutputStream(filepath);

        } catch (FileNotFoundException e) {
            Reports.fail("", e.toString());
            System.out.println("Unable to locate Excel ");
            e.printStackTrace();

        }
        try {
            workbook.write(fout);
        } catch (IOException e) {
            Reports.fail("", e.toString());
            System.out.println("unable to set Excel Data");
        }
    }

    /**
     * This function will initialize the excel file to use.
     *
     * @param excelFilePath      Pass the path of the Excel file.
     * @param scenariosSheetName Pass the sheet name to be accessed.
     */
    public static void InitializeExcel(String excelFilePath, String scenariosSheetName) {
        filepath = excelFilePath;
        try {
            file = new FileInputStream(excelFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sheet = workbook.getSheet(scenariosSheetName);
    }

    /**
     * This method can be used to fetch data from current sheet of the Excel by passing Row and Column number.
     *
     * @param rowNum pass the row number
     * @param colNum pass column number
     * @return The data of the cell in String
     * @throws IOException
     */
    public static String getExcelDataFromCurrentSheet(int rowNum, int colNum) throws IOException {
        data = null;
        try {
            if(sheet.getRow(rowNum)!=null){
                excelData = sheet.getRow(rowNum).getCell(colNum);
                data = excelData.getStringCellValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            DataFormatter formatter = new DataFormatter(); // creating formatter
            // using the default
            // locale
            Cell cell = sheet.getRow(rowNum).getCell(colNum);
            data = formatter.formatCellValue(cell);
        }

        return data;
    }

    /***************************************************************************************************************
     * This function will write data in particular cell defined by specific row
     * number and column number
     *
     * @param value
     *            this is Result that needs to be write in sheet number
     * @param rowNum
     *            This param is specific rowNum where result need to be set
     * @param colNum
     *            This param is specific column number where result needs to be
     *            set
     * @exception FileNotFoundException,
     *                IOException
     **************************************************************************************************************/
    public static void SetExcelDataInCurrentSheet(String value, int rowNum, int colNum) {
        row = sheet.getRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue(value);
        try {
            fout = new FileOutputStream(filepath);

        } catch (FileNotFoundException e) {
            Reports.fail("", e.toString());
            System.out.println("Unable to locate Excel ");
            e.printStackTrace();

        }
        try {
            workbook.write(fout);
        } catch (IOException e) {
            Reports.fail("", e.toString());
            System.out.println("unable to set Excel Data");
        }
    }

    /***************************************************************************************************************
     * This function will write data in particular cell defined by specific row
     * number and column number
     *
     * @param Result
     *            this is Result that needs to be write in sheet number
     * @param rowNum
     *            This param is specific rowNum where result need to be set
     * @param colName
     *            This param is specific column name in which the result needs to be
     *            set
     * @exception FileNotFoundException,
     *                IOException
     **************************************************************************************************************/
    public static void SetExcelDataInCurrentSheet(String Result, int rowNum, String colName) {
        int colNum = getColumnIndexByColumnName(colName);
        row = sheet.getRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue(Result);
        try {
            fout = new FileOutputStream(filepath);

        } catch (FileNotFoundException e) {
            Reports.fail("", e.toString());
            System.out.println("Unable to locate Excel ");
            e.printStackTrace();

        }
        try {
            workbook.write(fout);
        } catch (IOException e) {
            Reports.fail("", e.toString());
            System.out.println("unable to set Excel Data");
        }
    }

    /*****************************************************************************************************************
     * This function will return entire data set from particular excel sheet
     *
     * @filepath
     *            This param is the filepath of the excel file in which read
     *            operation will be performed
     * @sheetname
     *            This param is the sheetname of the excel file in whic read
     *            operation will be performed
     * @return
     * @throws IOException,
     *             FileNotFoundException
     * function will be called for data-provider
     ****************************************************************************************************************/
    public static Object[][] readData(String sheetName) {

        Object[][] Exceldata = null;
        try {
            sheet = workbook.getSheet(sheetName);
            int startRow = 1;
            int startCol = 0;
            int totalRow = sheet.getLastRowNum();
            int totalCol = 0;
            for(int i=0; i<totalRow; i++){
                if(sheet.getRow(i)!=null){
                    int colCount = sheet.getRow(i).getPhysicalNumberOfCells();
                    if(totalCol<colCount){
                        totalCol=colCount;
                    }
                }

            }
            System.out.println(totalRow);
            System.out.println(totalCol);
            int ci, cj;
            Exceldata = new Object[totalRow][totalCol];
            ci = 0;
            for (int i = startRow; i < totalRow; i++, ci++) {
                cj = 0;
                for (int j = startCol; j < totalCol; j++, cj++) {

                    Exceldata[ci][cj] = getExcelDataFromCurrentSheet(i, j);
                    System.out.println("Data store at index-- " + "Data[" + ci + "]" + "[" + cj + "]==>>" + "[" + i
                            + "]" + "[" + j + "]" + "--->" + Exceldata[ci][cj]);
                }
            }
            System.out.println("*************** Test Data Loaded *******************");
            //mapColumnNameIndex(totalCol);
        } catch (Exception e) {
            //Reports.fail("", e.toString());
            e.printStackTrace();
        }
        return (Exceldata);

    }

    static void mapColumnNameIndex(int totalCol) throws IOException {
        for (int i = 0; i < totalCol; i++) {
            columnIndex.put(getExcelDataFromCurrentSheet(0, i), i);
        }
    }

    public static int getColumnIndexByColumnName(String columnName) {
        return columnIndex.get(columnName);
    }

    /**
     * This method is used to set data in a specific sheet.
     *
     * @param sheetName pass the sheet name of current workbook.
     * @param rowNum    pass the row number.
     * @param colNum    pass the column number
     * @param value     pass the value to write in the sheet.
     */
    public static void setDataInSpecificSheet(String sheetName, int rowNum, int colNum, String value) {
        file = null;
        try {
            file = new FileInputStream(filepath);
        } catch (FileNotFoundException e) {
            Reports.fail("", e.toString());
            e.printStackTrace();

        }
        try {
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet(sheetName);
        } catch (IOException e) {
            Reports.fail("", e.toString());
            e.printStackTrace();
        }
        row = sheet.getRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue(value);

        try {
            fout = new FileOutputStream(filepath);

        } catch (FileNotFoundException e) {
            Reports.fail("", e.toString());
            System.out.println("Unable to locate Excel ");
            e.printStackTrace();
        }
        try {
            workbook.write(fout);
        } catch (IOException e) {
            Reports.fail("", e.toString());
            System.out.println("unable to set Excel Data");
        }
    }
}