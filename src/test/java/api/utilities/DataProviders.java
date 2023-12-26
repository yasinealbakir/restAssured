package api.utilities;

import org.testng.annotations.DataProvider;
import java.nio.file.Paths;
import java.nio.file.Path;

import java.io.IOException;

public class DataProviders {

    public static String path = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "testData", "userData.xlsx").toString();

    public static String sheetName = "Sheet1";

    @DataProvider(name = "user-data")
    public String[][] getAllUserData() throws IOException {

        ExcelUtility excelUtility = new ExcelUtility(path);
        int rownum = excelUtility.getRowCount(sheetName);
        int colnum = excelUtility.getCellCount(sheetName, 1);

        String payload[][] = new String[rownum][colnum];
        for (int i = 1; i <= rownum; i++) {
            for (int j = 0; j < colnum; j++) {
                payload[i - 1][j] = excelUtility.getCellData(sheetName, i, j);
            }
        }
        return payload;
    }

}
