import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class SigningUpServlet
 */
@WebServlet("/Signup")
public class SigningUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SigningUpServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.println("Send a post request to sign up a student in the database.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String location = request.getParameter("location");
		String gender = request.getParameter("gender");
		String experience = request.getParameter("experience");
		
		gender = getNormalizedGender(gender);
		location = getNormalizedLocation(location);

		request.setAttribute("name", name);
		request.setAttribute("email", email);
		request.setAttribute("location", location);
		request.setAttribute("gender", gender);
		request.setAttribute("experience", experience);

		String serverName = request.getServerName().toString();
		String scheme = request.getScheme().toString();
		
		String responseFile = "/response.jsp";
		request.setAttribute("imageLink", scheme + "://" + serverName + "/3TierWebSystem/Thanksgiving.gif");

		// request.setAttribute("imageLink", scheme + "://" + serverName +
		// "/3TierWebSystem/Thanksgiving.gif");
		// request.getRequestDispatcher("/response.jsp").forward(request,
		// response);

		// request.getRequestDispatcher(scheme+"://"+serverName +
		// "/3TierWebSystem/response.jsp").forward(request, response);
		// response.sendRedirect(scheme+"://"+serverName +
		// "/3TierWebSystem/response.jsp");
		// response.sendRedirect("https://google.com");

		// JDBC driver name and database URL
		String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		// String DB_URL = "jdbc:mysql://52.26.86.130:3306/student";
		String DB_URL = "jdbc:mysql://localhost:3306/testDB";

		// Database credentials
		String USER = "root";
		String PASS = "";

		Connection conn = null;
		Statement stmt = null;
		
		// STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = (Statement) conn.createStatement();
			String sql;
			sql = String.format("INSERT INTO student (name, email, location, gender, experience) "
					+ "VALUES ('%s', '%s', '%s', '%s', '%s')", name, email, location, gender, experience);
			System.out.println(sql);
			stmt.executeUpdate(sql);
			// STEP 5: Extract data from result set
//			while (rs.next()) {
//				// Retrieve by column name
//				String myName = rs.getString("name");
//				String Myexperience = rs.getString("experience");
//				// Display values
//				System.out.print("name: " + myName);
//				System.out.println(", experience: " + Myexperience);
//				// return the query results to client
////				name = name + "-" + myName;
////				experience = experience + "-" + Myexperience;
//				writer.println("name: " + myName);
//				writer.println(", experience: " + Myexperience);
//			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));
			request.setAttribute("error_title", "Error adding student to the database");
			request.setAttribute("error_subtitle", "Below is the stack trace for more error information");
			request.setAttribute("error", writer.toString());
			request.setAttribute("common_error", "Have you selected a name that has "
					+ "already been saved? Name is a primary key and hence must be unique.");
			responseFile = "/error.jsp";
		}
		
		request.getRequestDispatcher(responseFile).forward(request,response);
	}
	
	String getNormalizedGender(String gender) {
		if (gender == null) {
			gender = "";
		} else if (gender.compareTo("male") == 0) {
			gender = "m";
		} else {
			gender = "f";
		}
		return gender;
	}
	
	String getNormalizedLocation(String location) {
		if (location.compareTo("sf") == 0) {
			location = "San Francisco";
		} else {
			location = "New York";
		}
		return location;
	}
}
