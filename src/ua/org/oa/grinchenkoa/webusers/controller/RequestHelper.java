package ua.org.oa.grinchenkoa.webusers.controller;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import ua.org.oa.grinchenkoa.webusers.commands.*;

/**
 * Class-container of commands
 * 
 * @author Andrei Grinchenko
 */

public class RequestHelper {
	
	/**
	 * RequestHelper instance
	 */
	private static RequestHelper instance;
	
	/**
	 * Container
	 */
	private HashMap<String, Command> commands = new HashMap<>();
	
	/**
	 * Private constructor
	 * Filling the table with commands
	 */
	private RequestHelper() {
		commands.put("login", new LoginCommand());
		commands.put("registration", new RegCommand());
		commands.put("editdelete", new EditDeleteCommand());
	}
	
	/**
	 * 
	 * @param request HttpServletRequest object reference
	 * @return Command from the request or No Command
	 */
	public Command getCommand(HttpServletRequest request) {
		Command command = commands.get(request.getParameter("command"));
		/*if command doesn't exists return NoCommand*/
		if (command == null) {
			command = new NoCommand();
		}
		return command;
	}
	
	/**
	 * Singleton of creating RequestHelper instance 
	 * 
	 * @return Single instance
	 */
	public static RequestHelper getInstance() {
		if (instance == null) {
			instance = new RequestHelper();
		}
		return instance;
	}
}
