package control;

import java.util.HashMap;
import java.util.Map;

public class ApplicationController {
    // Create a Hash Map with a String key and a Handler Object
    // First string is to identify what Handler will be implemented from the map
    // Second string is to pass key data needed to the settled-upon handler
    private Map<String, Handler> handlerMap = new HashMap();

    public void handleRequest(String command, Map<String, Object> data) {
        // Retrieve the requested command-handler pair
        Handler commandHandler = handlerMap.get(command);

        // If not null, launch the handler with the returned map
        if (commandHandler != null) {
            commandHandler.execute(data);
        }
    }

    public void addCommand(String command, Handler action) {
        handlerMap.put(command, action);
    }
}