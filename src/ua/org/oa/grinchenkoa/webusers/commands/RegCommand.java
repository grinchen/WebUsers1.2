package ua.org.oa.grinchenkoa.webusers.commands;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.org.oa.grinchenkoa.webusers.dao.Dao;
import ua.org.oa.grinchenkoa.webusers.dao.DaoRole;
import ua.org.oa.grinchenkoa.webusers.dao.DaoUser;
import ua.org.oa.grinchenkoa.webusers.entities.Adress;
import ua.org.oa.grinchenkoa.webusers.entities.Role;
import ua.org.oa.grinchenkoa.webusers.entities.User;
import ua.org.oa.grinchenkoa.webusers.entities.MusicType;
import ua.org.oa.grinchenkoa.webusers.entities.UserMusicType;
import ua.org.oa.grinchenkoa.webusers.managers.ConfigurationManager;
import ua.org.oa.grinchenkoa.webusers.managers.MessageManager;
import ua.org.oa.grinchenkoa.webusers.validservice.Validations;

/**
 * Class implements Registration command
 * 
 * @author Andrei Grinchenko
 * 
 */
public class RegCommand implements Command {
	
	/**
	 * 
	 * @param request HttpServletRequest object reference
	 * @return created user with data from the form
	 * @throws IOException 
	 * @throws SQLException 
	 */
	private User createUser(HttpServletRequest request) throws SQLException, IOException {
		User user = new User();
		DaoRole<Role> daoRole = new DaoRole<>();
		user.setRole(daoRole.read(ConfigurationManager.getInstance().getProperty(
						ConfigurationManager.USER)));
		user.setLogin(request.getParameter("login"));
		user.setPassword(request.getParameter("password"));
		user.setBirthDate(new Date(new GregorianCalendar(Integer.valueOf(request.getParameter("birthYear")),
														 Integer.valueOf(request.getParameter("birthMonth")) - 1,
														 Integer.valueOf(request.getParameter("birthDay"))).getTimeInMillis()));
		user.setEmail(request.getParameter("email"));
		return user;
	}
	
	/**
	 * 
	 * @param request HttpServletRequest object reference
	 * @return created adress with data from the form
	 */
	private Adress createAdress(HttpServletRequest request) {
		Adress adress = new Adress();
		adress.setCountry(request.getParameter("country"));
		adress.setRegion(request.getParameter("region"));
		adress.setCity(request.getParameter("city"));
		adress.setStreet(request.getParameter("street"));
		adress.setBuilding(request.getParameter("building"));
		adress.setApp(request.getParameter("app"));
		return adress;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {
		String page = null;
		DaoUser<User> daoUser = new DaoUser<>();
		User user = createUser(request);  //creating user object
		Adress adress = createAdress(request);  //creating adress object
		Map<String,String> errors = new LinkedHashMap<>();  //errors' Map(key - reason, value - message)
		/*if user exists in database(not admin) return to registration page and report the message,
		 * admin can update existing users*/
		if (daoUser.contains(user.getLogin()) &&
				request.getSession().getAttribute(
						ConfigurationManager.getInstance().getProperty(ConfigurationManager.ADMIN)) == null) {
			errors.put("Exists", MessageManager.getInstance().getMessage(
					MessageManager.LOGIN_EXISTS_ERROR_MESSAGE));
			request.setAttribute("errors", errors);
			page = ConfigurationManager.getInstance().getProperty(
					ConfigurationManager.REG_PAGE_PATH);
		} else {
			errors = Validations.errorsMap(user, request.getParameter("conPassword")); //filling errors' map
			/*if validation is OK*/
			if (errors.isEmpty()) {
				Dao<Adress> daoAdress = new Dao<>();
				Dao<MusicType> daoMusicType = new Dao<>();
				Dao<UserMusicType> daoUserMusicType = new Dao<>();
				/*if not admin - registration new user in DB*/
				if (request.getSession().getAttribute(
						ConfigurationManager.getInstance().getProperty("ADMIN")) == null) {
					daoUser.create(user);  //creating user in database
					adress.setUser(user);
					daoAdress.create(adress);  //creating adress in database
					/*getting music types from the request(checkboxes) and filling UserMusicType table in DB*/
					for (MusicType musicType : daoMusicType.readAll(MusicType.class)) {
						if ("on".equals(request.getParameter(musicType.getMusicType()))) {
							UserMusicType userMusicType = new UserMusicType();
							userMusicType.setUser(user);
							userMusicType.setMusicType(musicType);
							daoUserMusicType.create(userMusicType);
						}
					}
					request.getSession().setAttribute("user", user);  //setting "user" object into request
					/*user's page*/
					page = ConfigurationManager.getInstance().getProperty(
							ConfigurationManager.MAIN_PAGE_PATH);
				} 
				/*if admin - updating existing user's information in all tables*/
				else {
					User updatingUser = (User)request.getSession().getAttribute("user");
					user.setId(updatingUser.getId());
					user.setRole(updatingUser.getRole());
					user.setUserMusicTypes(updatingUser.getUserMusicTypes());
					Adress updatingAdress = (Adress)request.getSession().getAttribute("adress");
					adress.setId(updatingAdress.getId());
					adress.setUser(updatingAdress.getUser());
					daoUser.update(user);
					daoAdress.update(adress);
					for (UserMusicType userMusicType : user.getUserMusicTypes()) {
						daoUserMusicType.delete(userMusicType);
					}
					for (MusicType musicType : daoMusicType.readAll(MusicType.class)) {
						if ("on".equals(request.getParameter(musicType.getMusicType()))) {
							UserMusicType userMusicType = new UserMusicType();
							userMusicType.setUser(user);
							userMusicType.setMusicType(musicType);
							daoUserMusicType.create(userMusicType);
						}
					}
					/*getting list of all information about users from DB and setting into request*/
					request.setAttribute("userList", daoUser.readAllSorted());
					/*admin's page*/
					page = ConfigurationManager.getInstance().getProperty(
							ConfigurationManager.MAIN_ADMIN_PAGE_PATH);
				}
			} 
			/*if validation failed - setting attributes for JSP*/
			else { 
				request.setAttribute("user", user);
				request.setAttribute("adress", adress);
				request.setAttribute("errors", errors);
				/*registration page*/
				page = ConfigurationManager.getInstance().getProperty(
						ConfigurationManager.REG_PAGE_PATH);
			}
		}
		return page;
	}
}
