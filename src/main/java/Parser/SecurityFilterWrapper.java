package Parser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class SecurityFilterWrapper extends HttpServletRequestWrapper {

	private static Pattern[] patterns = new Pattern[] { Pattern.compile("", Pattern.CASE_INSENSITIVE),
			Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("", Pattern.CASE_INSENSITIVE),
			Pattern.compile("", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
			Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
			Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL) };

	public SecurityFilterWrapper(ServletRequest request) {
		super((HttpServletRequest) request);
	}

	@Override
	public String getQueryString() {
		return stripXSSAttack(super.getQueryString());
	}

	// tools

	private String stripXSSAttack(String value) {
		String result = null;
		if (value != null) {

			try {
				// decode the string string
				result = URLDecoder.decode(value, "UTF-8");

				// replace null characters
				result = result.replaceAll("\0", "");

				// Remove all sections that match a pattern
				for (Pattern scriptPattern : patterns) {
					result = scriptPattern.matcher(result).replaceAll("");
				}

			} catch (UnsupportedEncodingException e) {
				result = null;
			}
		}
		return result;
	}
}
