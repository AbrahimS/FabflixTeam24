

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Cart
 */
@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("cart")== null)
		{
			session.setAttribute("cart", new HashMap<String,Integer>());
		}
		HashMap<String,Integer> myCart = (HashMap<String,Integer>)session.getAttribute("cart");
		String itemName = (String) request.getParameter("itemName");
		Integer quantity = Integer.parseInt(request.getParameter("quantity"));
		if(myCart.get(itemName) == null)
		{
			myCart.put(itemName, quantity);
		}
		else
		{
			int numOfItems = myCart.get(itemName);
			myCart.put(itemName, quantity);
		}
		if(quantity == 0)
		{
			myCart.remove(itemName);
		}
		session.setAttribute("cart", myCart);

		String refered = request.getHeader("Referer");
		response.sendRedirect(refered);
	}


}
