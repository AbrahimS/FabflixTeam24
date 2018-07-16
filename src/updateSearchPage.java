

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class updateSearchPage
 */
@WebServlet("/updateSearchPage")
public class updateSearchPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateSearchPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String URL = request.getParameter("url");
	String sortBy = request.getParameter("sortBy");
	String ascend = request.getParameter("ascend");
	String perPage = request.getParameter("perPage");
	
	String newString = URL + "&sortBy=" + sortBy + "&ascend=" + ascend + "&perPage="+ perPage;
	response.sendRedirect(newString);

}
}
