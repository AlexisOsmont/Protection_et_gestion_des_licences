package Controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.SoftwareDAO;
import model.Software;

public class ProductImageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProductImageController() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String url = request.getRequestURL().toString();
		int idx = url.indexOf("/product/");
		
		if (idx > 0) {
			int softwareId = Integer.valueOf(url.substring(idx+"/product/".length()));
			Software soft = SoftwareDAO.get(softwareId);
			if (soft != null) {
				byte[] imgData = soft.getImg();
				response.setContentType("image/jpg");
			    OutputStream o = response.getOutputStream();
			    o.write(imgData);
			    o.flush();
			    o.close();
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/home");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}
