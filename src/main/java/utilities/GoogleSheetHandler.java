package utilities;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GoogleSheetHandler {

    public static List<Sheet> workBook;
    public static Sheets.Spreadsheets spreadsheets;
    public static int columnCount = 0;
    public static int rowCount = 0;
    static String previousStatus = "";
    static int previousRow = 1;
    private static final String APPLICATION_NAME = "Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/client_id.json";
    private static final String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static Credential getCredentials(NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleSheetHandler.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private static Sheets.Spreadsheets accessWB() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        //final String range = "List!A2:A5";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        spreadsheets = service.spreadsheets();

        return spreadsheets;
    }

    private static String createRangeforCell(String sheetName, int rowNum, int colNum) {

        StringBuilder col= new StringBuilder();
        int extraCount=0;
        while((colNum>0)) {
            if(colNum>26) {
                extraCount++;
                colNum -=26;
            }else break;
        }
        if(extraCount>0) {
            col.append(alphabets.charAt(extraCount-1));
        }
        col.append(alphabets.charAt(colNum));

        return sheetName + "!" + col + (rowNum + 1);
    }

    private static String createRange(String sheetName, int fromRowNum, int fromColNum, int toRowNum, int toColNum) {

        StringBuilder fromCol= new StringBuilder();
        StringBuilder toCol= new StringBuilder();
        int extraCount=0;
        while((fromColNum>0)) {
            if(fromColNum>26) {
                extraCount++;
                fromColNum -=26;
            }else break;
        }
        if(extraCount>0) {
            fromCol.append(alphabets.charAt(extraCount-1));
        }
        fromCol.append(alphabets.charAt(fromColNum));
        extraCount=0;

        while((toColNum>0)) {
            if(toColNum>26) {
                extraCount++;
                toColNum -=26;
            }else break;
        }
        if(extraCount>0) {
            toCol.append(alphabets.charAt(extraCount-1));
        }
        toCol.append(alphabets.charAt(toColNum));

        return sheetName + "!" + fromCol + (fromRowNum + 1) + ":" + toCol + (toRowNum + 1);
    }

    public static Object[][] readData(String sheetId, String sheetName) throws IOException, GeneralSecurityException {

        if (workBook == null) {
            workBook = accessWB().get(sheetId).execute().getSheets();
        }
        int sheetIndex = 0;
        String range = null;
        for (Sheet sheet : workBook
        ) {
            if (sheet.getProperties().getTitle().equalsIgnoreCase(sheetName)) {
                sheetIndex = sheet.getProperties().getIndex();
                columnCount = sheet.getProperties().getGridProperties().getColumnCount();
                rowCount = sheet.getProperties().getGridProperties().getRowCount();
                break;
            }
        }

        range = createRange(sheetName, 1, 0, rowCount - 1, columnCount - 1);
        System.out.println(sheetName + " " + sheetIndex + " " + columnCount + " " + rowCount + " " + range);

        ValueRange response = spreadsheets.values()
                .get(sheetId, range)
                .execute();

        List<List<Object>> values = response.getValues();
        Object[][] data = new Object[rowCount - 1][columnCount];
        int i = 0;
        for (List a : values
        ) {
            for (int j = 0; j < columnCount; j++) {
                if (j < a.size()) {
                    data[i][j] = a.get(j);
                } else data[i][j] = "";

                System.out.print(data[i][j] + ", ");
            }
            System.out.println();
            i++;
        }

        return data;
    }

    public static Object[][] readData(String sheetId, String sheetName, int fromRow, int fromCol) throws IOException, GeneralSecurityException {

        if (workBook == null) {
            workBook = accessWB().get(sheetId).execute().getSheets();
        }
        int sheetIndex = 0;
        String range = null;
        for (Sheet sheet : workBook
        ) {
            if (sheet.getProperties().getTitle().equalsIgnoreCase(sheetName)) {
                sheetIndex = sheet.getProperties().getIndex();
                columnCount = sheet.getProperties().getGridProperties().getColumnCount();
                rowCount = sheet.getProperties().getGridProperties().getRowCount();
                break;
            }
        }

        range = createRange(sheetName, fromRow, fromCol, rowCount - 1, columnCount - 1);
        System.out.println(sheetName + " " + sheetIndex + " " + columnCount + " " + rowCount + " " + range);

        ValueRange response = spreadsheets.values()
                .get(sheetId, range)
                .execute();

        List<List<Object>> values = response.getValues();
        Object[][] data = new Object[rowCount - 1][columnCount];
        int i = 0;
        for (List a : values
        ) {
            for (int j = 0; j < columnCount; j++) {
                if (j < a.size()) {
                    data[i][j] = a.get(j);
                } //else data[i][j] = "";

                System.out.print(data[i][j] + ", ");
            }
            System.out.println();
            i++;
        }

        return data;
    }

    public static String getCellValue(String sheetId, String sheetName, int rowNum, int colNum) throws IOException, GeneralSecurityException {
        if (workBook == null) {
            workBook = accessWB().get(sheetId).execute().getSheets();
        }
        ValueRange response = spreadsheets.values()
                .get(sheetId, createRangeforCell(sheetName, rowNum, colNum))
                .execute();

        List<List<Object>> values = response.getValues();

        return values.get(0).get(0).toString();
    }

    public static String getCellValue(String sheetId, String sheetName, int rowNum, String colName) throws IOException, GeneralSecurityException {
        if (workBook == null) {
            workBook = accessWB().get(sheetId).execute().getSheets();
        }
        ValueRange response = spreadsheets.values()
                .get(sheetId, sheetName+"!"+colName+(rowNum+1))
                .execute();

        List<List<Object>> values = response.getValues();

        return values.get(0).get(0).toString();
    }

    public static void setCellValue(String sheetId, String sheetName, int rowNum, int colNum, String value) throws IOException, GeneralSecurityException {
        if (workBook == null) {
            workBook = accessWB().get(sheetId).execute().getSheets();
        }
        ValueRange content = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(value)));
        UpdateValuesResponse result = spreadsheets.values()
                .update(sheetId, createRangeforCell(sheetName, rowNum, colNum), content)
                .setValueInputOption("RAW")
                .execute();

    }

    public static void setCellValue(String sheetId, String sheetName, int rowNum, String colName, String value) throws IOException, GeneralSecurityException {
        if (workBook == null) {
            workBook = accessWB().get(sheetId).execute().getSheets();
        }
        ValueRange content = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(value)));
        UpdateValuesResponse result = spreadsheets.values()
                .update(sheetId, sheetName+"!"+colName+(rowNum+1), content)
                .setValueInputOption("RAW")
                .execute();
    }

    public static void setScriptStatus(String sheetId, String sheetName, int rowNum, int colNum, String status) throws IOException, GeneralSecurityException {

        if (!previousStatus.equalsIgnoreCase(status) || rowCount == (rowNum + 1)) {
            if (previousStatus.equalsIgnoreCase("")) {
                previousStatus = status;
            }

            String range = createRange(sheetName, previousRow, colNum, rowNum, colNum);

            if (workBook == null) {
                workBook = accessWB().get(sheetId).execute().getSheets();
            }

            List<Object>[] lists = new List[rowNum - previousRow + 1];
            for (int i = 0; i < lists.length; i++) {
                lists[i] = Arrays.asList(previousStatus);
            }
            lists[lists.length - 1] = Arrays.asList(status);

            List<List<Object>> values = Arrays.asList(lists);

            List<ValueRange> data = new ArrayList<>();
            data.add(new ValueRange()
                    .setRange(range)
                    .setValues(values));

            BatchUpdateValuesRequest body = new BatchUpdateValuesRequest();
            body.setData(data).setValueInputOption("RAW");

            BatchUpdateValuesResponse result =
                    spreadsheets.values().batchUpdate(sheetId, body).execute();
            previousRow = rowNum + 1;
            previousStatus = status;
        } else {
            System.out.println("No Change");
        }
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        String sheetID = "1YXZb-jfW3p8ITApXNVMaiwEGCWsaF7xTYsLDrbFCNZ4";
        String sheetName = "Inv Transfer";
        //readData(sheetID, sheetName);
        //System.out.println(getCellValue(sheetID, sheetName, 0, 0));
        setCellValue(sheetID, sheetName, 0, 0, "S.Noo.");

    }
}