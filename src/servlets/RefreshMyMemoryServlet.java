package servlets;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.ApplicationController;
import control.CreateNewAccountHandler;

/**
 * Servlet implementation class RefreshMyMemoryServlet
 */
@WebServlet(name = "RefreshMyMemoryServlet", urlPatterns = {"/RefreshMyMemoryServlet"})
public class RefreshMyMemoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ApplicationController appController;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefreshMyMemoryServlet() {
        super();

        // Initialize Application Controller
        appController = new ApplicationController();
        
        // Add Application Commands to Controller
        appController.addCommand("createAccount", new CreateNewAccountHandler());
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Execute the Appropriate Action and Receive Response
		String result = callAction(request);
		
		// Set Response Content Type (JSON, UTF-8)
		response.setContentType("application/json;charset=UTF-8");
		
		// Allocate an Output Writer to write a message to the network socket
		OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());

		// Write Message
		try {

			writer.write(result);
			writer.flush();
			System.out.println("Returned Result:  " + result);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Did Not Print Result");
		} finally {
			writer.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	protected String callAction(HttpServletRequest request) {
		// Extract Parameters
		String command = null;
		Map<String, Object> commandParameters = new HashMap<>();
		Enumeration<String> requestParameters = request.getParameterNames();
		
		// Extract the Command and Command Parameters
		while (requestParameters.hasMoreElements()) {
			// Get the Next Parameter
			String insertParameter = requestParameters.nextElement();
			
			if (insertParameter.equals("requestType")) {
				// Set the Command
				command = request.getParameter(insertParameter);
			} else {
				commandParameters.put(insertParameter, request.getParameter(insertParameter));
			}
		}
		
		
		// Action To Take if No Command or Parameters have been found
		if (command == null || commandParameters == null || commandParameters.size() < 1) {
			// *******************************************
			return "{\"status\":\"ERROR\"}";
		}
		
		// Append a return value to the object to be passed
		commandParameters.put("returnJSON", new String());
		
		// Run the appropriate Application Control
		appController.handleRequest(command, commandParameters);
		
		// Extract the Return Value
		String returnJSON = null;
		if (commandParameters.containsKey("returnJSON")) {
			returnJSON = (String) commandParameters.get("returnJSON");
		}
		
		if (returnJSON == null || returnJSON.isEmpty()) {
			returnJSON = "{\"status\":\"ERROR\"}";
		}
		
		
		// Extract and Return the Return Value (JSON String)
		return returnJSON;
	}
}
