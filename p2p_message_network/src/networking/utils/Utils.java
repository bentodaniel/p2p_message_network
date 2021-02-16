package networking.utils;

import javax.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    /**
     * Converts a List of Strings into a String representation of its Json
     * @param list The list of strings to convert
     * @return A string representation of th json object with the list
     */
    public static String convertStringListToJsonString(List<String> list){
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (int i = 0; i < list.size(); i++) {
            builder.add(i, list.get(i));
        }
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("list", builder.build())
                .build();

        return jsonObject.toString();
    }

    /**
     * Converts a string representation of a json to a list of strings
     * If there are no lists, returns empty
     * @param jsonString The string representation of the json object
     * @return A list of strings existent in the json or empty if there are no lists
     */
    public static List<String> convertJsonStringToStringList(String jsonString){
        List<String> list = new ArrayList<>();

        JsonObject jsonObject = Json.createReader(new StringReader(jsonString)).readObject();

        if (!jsonObject.containsKey("list")){
            return list;
        }

        JsonArray jsonArray = jsonObject.get("list").asJsonArray();

        if (jsonArray != null){
            for (JsonString elm: jsonArray.getValuesAs(JsonString.class)){
                String content = elm.getString().replaceAll("\"", "");
                list.add(content);
            }
        }
        return list;
    }

    /**
     * Puts a value into a json with the key 'value' and returns it as a string
     * @param value The value to use
     * @return The string representation of the json obtained
     */
    public static String convertStringToValueJsonString(String value) {
        return Json.createObjectBuilder()
                .add("value", value)
                .build()
                .toString();
    }

    public static String getValueFromValueJsonString(String jsonString) {
        JsonObject jsonObject = Json.createReader(new StringReader(jsonString)).readObject();

        if (!jsonObject.containsKey("value")){
            return "";
        }

        return jsonObject.get("value").toString().replaceAll("\"", "");
    }

    public static String[] convertListToArray(List<String> list){
        if (list.size() == 0){
            return new String[]{};
        }
        return list.toArray(new String[0]);
    }

    public static String getPublicIP() throws IOException {
        // Use a webservice like AWS
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()));

        String ip = in.readLine(); //you get the IP as a String
        return ip;
    }
}
