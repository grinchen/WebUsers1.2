package ua.org.oa.grinchenkoa.webusers.validservice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ua.org.oa.grinchenkoa.webusers.entities.User;
import ua.org.oa.grinchenkoa.webusers.managers.MessageManager;

/**
 * Util for user validation
 * 
 * 
 * @author Andrei Grinchenko
 *
 */

public class Validations {
	
	/**
	 * private Constructor, unable to create instance outside
	 */
	private Validations() {}

		/**
		 * 
		 * @param login User's login
		 * @return true if login valid
		 */
		private static boolean validLogin(String login) {
			Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9_-]{5,14}");
			Matcher matcher = pattern.matcher(login.trim());
			return matcher.matches();
		}
		
		/**
		 * 
		 * @param email User's e-mail
		 * @return true if e-mail valid
		 */
		private static boolean validEmail(String email) {
			Pattern pattern = Pattern.compile("\\w+@[a-zA-Z_-]+?\\.[a-zA-Z]{2,6}");
			Matcher matcher = pattern.matcher(email);
			return matcher.matches();
		}
		
		/**
		 * 
		 * @param user User
		 * @param conPassword inputed password confirmation
		 * @return errors' Map after validation
		 */
		public static Map<String,String> errorsMap(User user, String conPassword){
		         Map<String,String> errors = new LinkedHashMap<>();
		         if ("".equals(user.getLogin().trim())){
		             errors.put("emptyLogin", MessageManager.getInstance().getMessage(
		            		 MessageManager.EMPTY_LOGIN));
		         } else if (!validLogin(user.getLogin())) {
		        	 	errors.put("invalidLogin", MessageManager.getInstance().getMessage(
			            		 MessageManager.INVALID_LOGIN));
		         		}
		         
		         if ("".equals(user.getPassword())){
		             errors.put("emptyPassword", MessageManager.getInstance().getMessage(
		            		 MessageManager.EMPTY_PASSWORD));
		         } else if (!user.getPassword().equals(conPassword)) {
		        	 	errors.put("conPasswordFailed", MessageManager.getInstance().getMessage(
			            		 MessageManager.CONFIRMATION_PASSWORD_FAILED));
		         }
		        
		         if ("".equals(user.getEmail())){
		             errors.put("emptyEmail", MessageManager.getInstance().getMessage(
		            		 MessageManager.EMPTY_EMAIL));
		         } else if (!validEmail(user.getEmail())) {
		        	 	errors.put("invalidEmail", MessageManager.getInstance().getMessage(
			            		 MessageManager.INVALID_EMAIL));
		         }
		         return errors;
		     }
}
