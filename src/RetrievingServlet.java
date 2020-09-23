

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
 * Servlet implementation class RetrievingServlet
 */
@WebServlet("/Retrieve")
public class RetrievingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetrievingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.println("Send a post request to retrieve a student's information from the database.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String name = request.getParameter("name");
		String email = null;
		String location = null;
		String gender = null;
		String experience = null;
		
		// JDBC driver name and database URL
		String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		// String DB_URL = "jdbc:mysql://52.26.86.130:3306/student";
		String DB_URL = "jdbc:mysql://localhost:3306/testDB";

		// Database credentials
		String USER = "root";
		String PASS = "";

		Connection conn = null;
		Statement stmt = null;
		
		String serverName = request.getServerName().toString();
		String scheme = request.getScheme().toString();
		
		String responseFile = "response.jsp";
		request.setAttribute("imageLink", scheme + "://" + serverName + "/3TierWebSystem/Thanksgiving.gif");
		
		int size = 0;
				
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
			sql = String.format("SELECT * FROM student WHERE name='%s'", name);
			System.out.println(sql);
			// STEP 5: Extract data from result set
			ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			while (rs.next()) {
				size++;
				// Retrieve by column name
//				String myName = rs.getString("name");
				email = rs.getString("email");
				location = rs.getString("location");
				gender = rs.getString("gender");
				experience = rs.getString("experience");
				// Display values
//				System.out.print("name: " + myName);
//				System.out.println(", experience: " + Myexperience);
				// return the query results to client
//				name = name + "-" + myName;
//				experience = experience + "-" + Myexperience;
//				writer.println("name: " + myName);
//				writer.println(", experience: " + Myexperience);
					}
			
				request.setAttribute("name", name);
				request.setAttribute("email", email);
				request.setAttribute("location", location);
				request.setAttribute("gender", gender);
				request.setAttribute("experience", experience);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				StringWriter writer = new StringWriter();
				e.printStackTrace(new PrintWriter(writer));
				request.setAttribute("error_title", "Couldn't find a student with the entered name.");
				request.setAttribute("common_error", "Have you entered a student name that doesn't exist in the database?");
				request.setAttribute("error", writer.toString());
				responseFile = "/error.jsp";
			}
		
		if (size == 0) {
			request.setAttribute("error_title", "Couldn't find a student with the entered name.");
			request.setAttribute("common_error", "Have you entered a student name that doesn't exist in the database?");
			request.setAttribute("error", "");
			responseFile = "/error.jsp";
		}
		request.getRequestDispatcher(responseFile).forward(request,response);
	}

}
