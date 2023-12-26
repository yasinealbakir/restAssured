package api.utilities;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.nio.file.Path;

import static java.lang.System.out;

public class JsonUtility {

    public static String path = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "JsonFiles").toString();

    public static JSONObject readJsonFile(String file) {
        JSONObject jsonData = null;

        try (FileReader fileReader = new FileReader(new File(Paths.get(path, file).toString()))) {
            JSONTokener jsonTokener = new JSONTokener(fileReader);
            jsonData = new JSONObject(jsonTokener);
        } catch (FileNotFoundException e) {
            System.out.println("File not found --> " + file);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred while reading the JSON file.");
            e.printStackTrace();
        }

        return jsonData;
    }
}
