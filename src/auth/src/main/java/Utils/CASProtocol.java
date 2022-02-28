package Utils;

import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CASProtocol {

	public static String send(Map<String, String> attrs) {
		if (attrs == null) {
			throw new AssertionError("Invalid parameters");
		}
		JSONObject obj = new JSONObject();
		for (String key : attrs.keySet()) {
			String value = attrs.get(key);
			obj.put(key, value);
		}
		return obj.toString();
	}

	public static Map<String, String> recieved(String text) {
		if (text == null) {
			throw new AssertionError("Invalid parameters");
		}
		JSONParser parser = new JSONParser();
		Map<String, String> result = null;
		try {
			result = (Map<String, String>) parser.parse(text);
		} catch (ParseException e) {
			result = null;
		}
		return result;
	}
}

