package Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
 
public class RessourcePrinter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Controller
	public RessourcePrinter() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if (session != null) {
			printQrcode(request, response);
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	
	private void printQrcode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String resId = request.getParameter("id");
		if (resId != null) {
		
			ByteArrayOutputStream out = QRCode.from(request.getContextPath() + "/ressource?id=" + resId)
					.to(ImageType.PNG).stream();
			 
			response.setContentType("image/png");
			response.setContentLength(out.size());
			 
			OutputStream outStream = response.getOutputStream();
			 
			outStream.write(out.toByteArray());
			 
			outStream.flush();
			outStream.close();
		}
	}
}
