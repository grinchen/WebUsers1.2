package ua.org.oa.grinchenkoa.webusers.controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.org.oa.grinchenkoa.webusers.commands.Command;
import ua.org.oa.grinchenkoa.webusers.managers.ConfigurationManager;
import ua.org.oa.grinchenkoa.webusers.managers.MessageManager;

/**
 * Servlet implementation class Controller
 * 
 * @author Andrei Grinchenko
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * Object contains map of all the possible commands
	 */
    private RequestHelper requestHelper = RequestHelper.getInstance();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	/**
	 * 
	 * @param request HttpServletRequest object reference
	 * @param response HttpServletResponse object reference
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			Command command = requestHelper.getCommand(request);  //defining the command from JSP
			page = command.execute(request, response);  //getting the page for response
		} catch (SQLException e) {
			e.printStackTrace();
			/*setting SQL Error Message into request*/
			request.setAttribute("errorMessage", MessageManager.getInstance().getMessage(
					MessageManager.SQL_EXCEPTION_ERROR_MESSAGE));
			page = ConfigurationManager.getInstance().getProperty(
					ConfigurationManager.ERROR_PAGE_PATH);
		} catch (ServletException e) {
			e.printStackTrace();
			/*setting Servlet Error Message into request*/
			request.setAttribute("errorMessage", MessageManager.getInstance().getMessage(
					MessageManager.SERVLET_EXCEPTION_ERROR_MESSAGE));
			page = ConfigurationManager.getInstance().getProperty(
					ConfigurationManager.ERROR_PAGE_PATH);
		} catch (IOException e) {
			e.printStackTrace();
			/*setting IO Error Message into request*/
			request.setAttribute("errorMessage", MessageManager.getInstance().getMessage(
							MessageManager.IO_EXCEPTION_ERROR_MESSAGE));
			page = ConfigurationManager.getInstance().getProperty(
					ConfigurationManager.ERROR_PAGE_PATH);
		} 
		/*calling a response page*/
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}

}
