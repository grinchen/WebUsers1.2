package ua.org.oa.grinchenkoa.webusers.commands;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.org.oa.grinchenkoa.webusers.dao.Dao;
import ua.org.oa.grinchenkoa.webusers.dao.DaoRole;
import ua.org.oa.grinchenkoa.webusers.dao.DaoUser;
import ua.org.oa.grinchenkoa.webusers.entities.Adress;
import ua.org.oa.grinchenkoa.webusers.entities.Role;
import ua.org.oa.grinchenkoa.webusers.entities.User;
import ua.org.oa.grinchenkoa.webusers.entities.UserMusicType;
import ua.org.oa.grinchenkoa.webusers.managers.ConfigurationManager;
import ua.org.oa.grinchenkoa.webusers.managers.MessageManager;

/**
 * Class implements EditDelete command
 * 
 * @author Andrei Grinchenko
 */
public class EditDeleteCommand implements Command {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {
		String page = null;
		DaoUser<User> daoUser = new DaoUser<>();
		DaoRole<Role> daoRole = new DaoRole<>();
		/*getting user from DB with such id*/
		User editDeleteUser = daoUser.read(Integer.parseInt(request.getParameter("editdeleteuserid")), User.class);
		/*if not admin(unable deleting or updating admin)*/
		if (! editDeleteUser.getRole().equals(daoRole.read(
				ConfigurationManager.getInstance().getProperty(
						ConfigurationManager.ADMIN)))) {
			Dao<Adress> daoAdress = new Dao<>();
			Adress adress = editDeleteUser.getAdress(); //getting user's adress from DB
			/*creating record 'adress' in DB if doesn't exist*/
			if (adress == null) {
				adress = new Adress();
				adress.setUser(editDeleteUser);
				daoAdress.create(adress);
			}
			adress.setUser(editDeleteUser);
			/*if "delete" in request*/ 
			if (request.getParameter("delete") != null) {  
				Dao<UserMusicType> daoUserMusicType = new Dao<>();
				if (adress != null)
					daoAdress.delete(adress);  //deleting user's adress from DB
				for (UserMusicType userMusicType : editDeleteUser.getUserMusicTypes()) {
					daoUserMusicType.delete(userMusicType); //deleting user's music types from DB
				}
				daoUser.delete(editDeleteUser);  //deleting user
				/*getting new list of all information about users from DB and setting attribute into request*/
				request.setAttribute("userList", daoUser.readAllSorted());
				/*admin's page*/
				page = ConfigurationManager.getInstance().getProperty(  
						ConfigurationManager.MAIN_ADMIN_PAGE_PATH);
			/*if edit in request*/
			} else if (request.getParameter("edit") != null) {
				/*attribute ADMIN should be not null(setting admin's flag)*/
				request.getSession().setAttribute(ConfigurationManager.getInstance().getProperty(
						ConfigurationManager.ADMIN), new Object());
				request.getSession().setAttribute("user", editDeleteUser);
				request.getSession().setAttribute("adress", adress);
				page = ConfigurationManager.getInstance().getProperty(
						ConfigurationManager.UPDATE_PAGE_PATH);
			/*if neither "delete" no "edit" in request - setting errorMessage*/
			} else {  
				/*setting error message into request*/
				request.setAttribute("errorMessage", MessageManager.getInstance().getMessage(
						MessageManager.INVALID_REQUEST));
				/*error page*/
				page = ConfigurationManager.getInstance().getProperty(
						ConfigurationManager.ERROR_PAGE_PATH);
			}
		/*if admin*/
		} else {
			/*getting list of all information about users from DB and setting into request*/
			request.setAttribute("userList", daoUser.readAllSorted());
			/*admin's page*/
			page = ConfigurationManager.getInstance().getProperty(
						ConfigurationManager.MAIN_ADMIN_PAGE_PATH);
		}
		return page;
	}

}
