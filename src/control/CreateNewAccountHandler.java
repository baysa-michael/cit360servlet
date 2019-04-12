package control;

import java.util.HashMap;
import java.util.Map;

import model.DataAccessObject;

public class CreateNewAccountHandler implements Handler {

	@Override
	public void execute(Map<String, Object> data) {
		String username = null;
		String hashedPassword = null;
		String email = null;
		String displayName = null;
		int userSalt = 0;
		
		if (data != null) {
			// Extract Information
			username = (String) data.get("username");
			hashedPassword = (String) data.get("hashedPassword");
			email = (String) data.get("email");
			displayName = (String) data.get("displayName");
			System.out.println("Username:  " + username + 
					" - Hashed Password:  " + hashedPassword + 
					" - E-mail:  " + email + 
					" - Display Name:  " + displayName);
			userSalt = Integer.parseInt(data.get("userSalt").toString());
		}
		
		// Check to make sure that none of the fields are null
		if (username == null || hashedPassword == null || email == null || displayName == null) {
			data.replace("returnJSON", "{\"status\":\"ERROR\"}");
			return;
		}

		// Create a new DAO instance
		DataAccessObject dao = DataAccessObject.getInstance();
		
		boolean isSuccessful = dao.addNewUser(username, hashedPassword, email, displayName, userSalt);
		
		if (isSuccessful) {
			data.replace("returnJSON", "{\"status\":\"SUCCESS\"}");
		} else {
			data.replace("returnJSON", "{\"status\":\"ERROR\"}");
		}
	}
}
