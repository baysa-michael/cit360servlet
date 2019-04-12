package control;

import java.util.Map;

import model.DataAccessObject;

public class LoginHandler implements Handler {
	@Override
	public void execute(Map<String, Object> data) {
		String username = null;
		String hashedPassword = null;
		
		if (data != null) {
			// Extract Information
			username = (String) data.get("username");
			hashedPassword = (String) data.get("hashedPassword");
		}
		
		// Check to make sure that none of the fields are null
		if (username == null || hashedPassword == null) {
			data.replace("returnJSON", "{\"status\":\"ERROR\"}");
			return;
		}

		// Create a new DAO instance
		DataAccessObject dao = DataAccessObject.getInstance();
		
		boolean isSuccessful = dao.login(username, hashedPassword);
		
		if (isSuccessful) {
			data.replace("returnJSON", "{\"status\":\"SUCCESS\"}");
		} else {
			data.replace("returnJSON", "{\"status\":\"ERROR\"}");
		}
	}
}
