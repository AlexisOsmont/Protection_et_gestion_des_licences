package Utils;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class DynamicTableHelper {

	private static final int ROW_NUMBER = 10;

	private static final String PAGE_ATTRIBUTE = "page";
	private static final String CURRENT_PAGE_ATTRIBUTE = "current-page";

	public static <T> List<T> getPageList(List<T> l, HttpServletRequest request) {

		int cut        = 0;
		int fromIndex  = 0;
		int toIndex    = 0;
		int listSize   = 0;
		String page    = request.getParameter(PAGE_ATTRIBUTE);
		List<T> result = null;

		if (l != null) {

			listSize = l.size();
			// check for the page number, if no page number are supplied return the first
			// one
			if (page != null) {
				// check for the page number (a page contains ROW_NUMBER result)
				try {
					cut = Integer.valueOf(page);
				} catch (NumberFormatException e) {
					// if the conversion failed, the default value is zero
					cut = 0;
				}
				cut = cut < 0 ? 0 : cut * ROW_NUMBER;
			}

			fromIndex = Math.min(cut, listSize);
			toIndex = Math.min(cut + ROW_NUMBER, listSize);

			// check if the interval is empty
			if (fromIndex == toIndex) {
				// show the first match
				fromIndex = 0;
				toIndex = Math.min(ROW_NUMBER, listSize);
			}

			// set the current page number
			request.setAttribute(CURRENT_PAGE_ATTRIBUTE, fromIndex / ROW_NUMBER);

			// get the sublist corresponding to the page asked
			result = l.subList(fromIndex, toIndex);
		}

		return result;
	}
}
