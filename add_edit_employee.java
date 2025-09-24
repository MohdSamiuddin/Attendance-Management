import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/employee")
public class add_edit_employee extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String eid = req.getParameter("eid");
        String name = req.getParameter("name");
        

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Attendance","root","1234");

            // Check if employee exists
            PreparedStatement check = con.prepareStatement("SELECT * FROM register WHERE eid=?");
            check.setString(1, eid);
            ResultSet rs = check.executeQuery();

            if(rs.next()) {
                // Update existing employee
                PreparedStatement ps = con.prepareStatement("UPDATE register SET name=? WHERE eid=?");
                ps.setString(1, name);
                ps.setString(2, eid);
                ps.executeUpdate();
                out.print("<h3 style='text-align:center;color:green'>Employee Updated Successfully</h3>");
            } 

            con.close();
            out.print("<br/><div style='text-align:center'><a href='add_edit_employee.html'>Back</a></div>");

        } catch(Exception e) {
            e.printStackTrace();
            out.print("<h3 style='text-align:center;color:red'>Exception: " + e.getMessage() + "</h3>");
        }
    }
}

