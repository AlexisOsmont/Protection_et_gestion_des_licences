package Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserUtils {
	
	public static Map<String, String> readConfigFile(String pathToFile) {
		Map<String, String> attrs = new TreeMap<String, String>();
		try {
	      File f = new File(pathToFile);
	      Scanner reader = new Scanner(f);
	      
	      // read each line
	      while (reader.hasNextLine()) {
	        String data = reader.nextLine();
	        
	        // use regexp for parsing the line
			Pattern pt = Pattern.compile("([^=\s]+)[\s]*=[\s]*(.*)");
			Matcher m = pt.matcher(data);
			// ignore section and comment 
			if (m.find()) { 
				String key = m.group(1);
				String value = m.group(2).replaceAll("\"","");
				
				attrs.put(key, value);
			}
	        
	      }
	      reader.close();
	    } catch (FileNotFoundException e) {
	    	attrs = null;
	    }
		return attrs;
	}

	public static String escapeHtml(String str) {
		String result = null;
		if (str != null) {
			StringBuilder escapedTxt = new StringBuilder();
			for (int i = 0; i < str.length(); i++) {
				char tmp = str.charAt(i);
				switch (tmp) {
				case '<':
					escapedTxt.append("&lt;");
					break;
				case '>':
					escapedTxt.append("&gt;");
					break;
				case '&':
					escapedTxt.append("&amp;");
					break;
				case '"':
					escapedTxt.append("&quot;");
					break;
				case '\'':
					escapedTxt.append("&#x27;");
					break;
				case '/':
					escapedTxt.append("&#x2F;");
					break;
				default:
					escapedTxt.append(tmp);
				}
			}
			result = escapedTxt.toString();
		}
		
		return result;
	}
}
