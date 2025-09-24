import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class Login extends HttpServlet{
	
  @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	PrintWriter out=resp.getWriter();
	String email=req.getParameter("email");
	String password=req.getParameter("password");
	
	try {
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Attendance","root","1234"); 
    	PreparedStatement ps=con.prepareStatement("select password from register where email=? ");
    	ps.setString(1,email);	
    	
    	ResultSet rs=ps.executeQuery();
    	
    	if(rs.next()) {
    		String db=rs.getString("password");
			
			if(db.equals(password)) {
			resp.setContentType("text/html");
    		out.print("<h3 style='color:green'>Login sucessfully</h3>");
    		RequestDispatcher rd=req.getRequestDispatcher("/Login.html"); 
    		rd.include(req, resp);
    	}
    	else {
    		resp.setContentType("text/html");
    		out.print("<h3 style='color:red'> Wrong email or password </h3>");
    		RequestDispatcher rd=req.getRequestDispatcher("/Login.html"); 
    		rd.include(req, resp);
    	}
    }
  }
    catch(Exception e) {
    	e.printStackTrace();
    	resp.setContentType("text/html");
		out.print("<h3 style='color:red,text-align:center'>Exception Occured"+e.getMessage()+"</h3>");
		RequestDispatcher rd=req.getRequestDispatcher("/Login.html"); 
		rd.include(req, resp);
    }
}
}
