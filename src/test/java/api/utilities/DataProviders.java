package api.utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    public static String path = System.getProperty("user.dir") + "//src//test//resources//testData//userData.xlsx";
    public static String userSheet = "Sheet1";
    public static String postSheet = "Sheet2";

    @DataProvider(name = "user-data")
    public String[][] getAllUserData() throws IOException {

        ExcelUtility excelUtility = new ExcelUtility(path);
        int rownum = excelUtility.getRowCount(userSheet);
        int colnum = excelUtility.getCellCount(userSheet, 1);

        String payload[][] = new String[rownum][colnum];
        for (int i = 1; i <= rownum; i++) {
            for (int j = 0; j < colnum; j++) {
                payload[i - 1][j] = excelUtility.getCellData(userSheet, i, j);
            }
        }
        return payload;
    }

    @DataProvider(name = "post-data")
    public String[][] getAllPostData() throws IOException {

        ExcelUtility excelUtility = new ExcelUtility(path);
        int rownum = excelUtility.getRowCount(postSheet);
        int colnum = excelUtility.getCellCount(postSheet, 1);

        String payload[][] = new String[rownum][colnum];
        for (int i = 1; i <= rownum; i++) {
            for (int j = 0; j < colnum; j++) {
                payload[i - 1][j] = excelUtility.getCellData(postSheet, i, j);
            }
        }
        return payload;
    }


    public void setUserId(String id) throws IOException {
        ExcelUtility excelUtility = new ExcelUtility(path);
        int rownum = excelUtility.getRowCount(userSheet);
        int colnum = excelUtility.getCellCount(userSheet, 1);

        for (int i = 1; i <= rownum; i++) {
            excelUtility.setCellData(userSheet, i, 4, id);

        }
    }


}
