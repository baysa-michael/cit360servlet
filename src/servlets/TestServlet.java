package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet(name = "TestServlet", urlPatterns = {"/TestServlet"})
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TestServlet() {

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		// Allocate an Output Writer to write message into network socket
		PrintWriter out = response.getWriter();
		
		// Write the message in an HTML page
		try {
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Simple Servlets</title>");
			out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Simple Servlet Output</h1>");
			out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
			out.println("<p>Request Protocol: " + request.getProtocol() + "</p>");
			out.println("<p>Path Info: " + request.getPathInfo() + "</p>");
			out.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>");
			out.println("<p>Prepared By:  Michael Baysa</p>");
			out.println("</body>");
			out.println("</html>");
		} finally {
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
