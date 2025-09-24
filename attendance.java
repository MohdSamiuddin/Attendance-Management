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

@WebServlet("/attend")
public class attendance extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out=resp.getWriter();
		String eid=req.getParameter("eid");
		String attend=req.getParameter("mark");
		
		 try {
		    	Class.forName("com.mysql.cj.jdbc.Driver");
		    	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Attendance","root","1234"); 
		    	PreparedStatement ps=con.prepareStatement("insert into attendance_1 (eid,attend) "
		    			+ " values(?,?)");
		    	ps.setString(1,eid);
		    	ps.setString(2, attend);
		    	
		    	int i=ps.executeUpdate();
		    	
		    	if(i>0) {
		    		resp.setContentType("text/html");
		    		out.print("<h3 style='color:green'>Attendance Marked</h3>");
		    		RequestDispatcher rd=req.getRequestDispatcher("/attendance.html"); 
		    		rd.include(req, resp);
		    	}
		    	else {
		    		resp.setContentType("text/html");
		    		out.print("<h3 style='color:red'>Not marked</h3>");
		    		RequestDispatcher rd=req.getRequestDispatcher("/attendance.html"); 
		    		rd.include(req, resp);
		    	}
		    }
		    catch(Exception e) {
		    	e.printStackTrace();
		    	resp.setContentType("text/html");
				out.print("<h3 style='color:red,text-align:center'>Exception Occured"+e.getMessage()+"</h3>");
				RequestDispatcher rd=req.getRequestDispatcher("/attendance.html"); 
				rd.include(req, resp);
		    }
	}

}
