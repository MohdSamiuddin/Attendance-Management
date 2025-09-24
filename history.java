import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/history_1")
public class history extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String eid = req.getParameter("eid");

        out.print("<h2 style='text-align:center'>Attendance History for EID: " + eid + "</h2>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Attendance","root","1234"); 
            PreparedStatement ps = con.prepareStatement("SELECT * FROM attendance_1 WHERE eid=?");
            ps.setString(1, eid);
            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) { // no records found
                out.print("<h3 style='color:red;text-align:center'>No attendance records found</h3>");
            } else {
                out.print("<table border='1' style='width:50%; margin:auto; text-align:center'>");
                out.print("<tr><th>EID</th><th>Attendance</th><th>Date & Time</th></tr>");

                while(rs.next()) {
                    String attend = rs.getString("attend");
                    String dateTime = rs.getString("Date_and_time");

                    out.print("<tr>");
                    out.print("<td>" + eid + "</td>");
                    out.print("<td>" + attend + "</td>");
                    out.print("<td>" + dateTime + "</td>");
                    out.print("</tr>");
                }

                out.print("</table>");
            }

            con.close();

            out.print("<br/><div style='text-align:center'><a href='attendance.html'>Back to Attendance</a></div>");

        } catch(Exception e) {
            e.printStackTrace();
            out.print("<h3 style='color:red;text-align:center'>Exception Occurred: " + e.getMessage() + "</h3>");
        }
    }
}
