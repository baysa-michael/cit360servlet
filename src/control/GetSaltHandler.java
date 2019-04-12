package control;

import java.util.Map;

import model.DataAccessObject;

public class GetSaltHandler implements Handler {
	@Override
	public void execute(Map<String, Object> data) {
		String username = null;
		
		if (data != null) {
			// Extract Information
			username = (String) data.get("username");
		}
		
		// Check to make sure that none of the fields are null
		if (username == null) {
			data.replace("returnJSON", "{\"status\":\"ERROR\"}");
			return;
		}

		// Create a new DAO instance
		DataAccessObject dao = DataAccessObject.getInstance();
		
		int salt = dao.getSalt(username);

		data.replace("returnJSON", "{\"status\":\"SUCCESS\",\"salt\":\"" + Integer.toString(salt) + "\"}");
	}
}
