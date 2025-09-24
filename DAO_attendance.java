import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/regform")
public class DAO_attendance extends HttpServlet{
  @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	PrintWriter out=resp.getWriter();
	String eid=req.getParameter("eid");
	String name=req.getParameter("name");
	String email=req.getParameter("email");
	String phone=req.getParameter("phone");
	String age=req.getParameter("age");
    String gender=req.getParameter("Gender");
    String password=req.getParameter("password");
    
    try {
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Attendance","root","1234"); 
    	PreparedStatement ps=con.prepareStatement("insert into register (eid, name, email, phone, age, gender,password)"
    			+ " values(?,?,?,?,?,?,?)");
    	ps.setString(1,eid);
    	ps.setString(2, name);
    	ps.setString(3, email);
    	ps.setString(4,phone);
    	ps.setString(5, age);
    	ps.setString(6, gender);
    	ps.setString(7, password);
    	
    	int i=ps.executeUpdate();
    	
    	if(i>0) {
    		resp.setContentType("text/html");
    		out.print("<h3 style='color:green,align:center'>User created sucessfully</h3>");
    		RequestDispatcher rd=req.getRequestDispatcher("/index.html"); 
    		rd.include(req, resp);
    	}
    	else {
    		resp.setContentType("text/html");
    		out.print("<h3 style='color:red,text-align:center'>User not created</h3>");
    		RequestDispatcher rd=req.getRequestDispatcher("/index.html"); 
    		rd.include(req, resp);
    	}
    }
    catch(Exception e) {
    	e.printStackTrace();
    	resp.setContentType("text/html");
		out.print("<h3 style='color:red,text-align:center'>Exception Occured"+e.getMessage()+"</h3>");
		RequestDispatcher rd=req.getRequestDispatcher("/index.html"); 
		rd.include(req, resp);
    }
}
}
