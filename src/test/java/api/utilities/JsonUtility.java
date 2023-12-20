package api.utilities;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static java.lang.System.out;

public class JsonUtility {

    public static String path = System.getProperty("user.dir") + "//src//test//resources//JsonFiles//";

    public static JSONObject readJsonFile(String file) {
        File jsonFile = new File(path + file);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(jsonFile);
        } catch (FileNotFoundException e) {
            out.println("File not found --> " + file);
        }
        JSONTokener jsonTokener = new JSONTokener(fileReader);
        JSONObject jsonData = new JSONObject(jsonTokener);
        return jsonData;
    }
}
