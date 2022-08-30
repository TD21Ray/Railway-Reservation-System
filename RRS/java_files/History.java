import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.io.*;
import java.lang.reflect.Executable;
import java.util.*;

public class History extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/rrs";
        String sqluser = "root";
        String sqlpwd = "krnb0502";

        String email = req.getParameter("email");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Hey I am after forname method in getTrains() - SearchTrain class");
            
            Connection con = DriverManager.getConnection(url, sqluser, sqlpwd);

            Statement st = con.createStatement();

            System.out.println("Hey I am after create method in getTrains() - SearchTrain class");

            res.setContentType("text/html");
            PrintWriter pw = res.getWriter();

            String s = String.format("select pid from passenger where email='%s'", email);
            ResultSet rs = st.executeQuery(s);
            rs.next();
            int pid = rs.getInt(1);
            s = String.format("select * from ticket where pid='%d'", pid);
            ResultSet rs1 = st.executeQuery(s);

            String histList = new String();
            histList = "<table border='2' cellspacing='3' cellpadding='30' bordercolor='Blue' align='center'><tr><th>Ticket Number</th><th>Train id</th><th>Date of Travel</th><th>No.of.Seats</th><th>Status</th></tr>";
            while(rs1.next()) {
                int ticno = rs1.getInt(1);
                int tid = rs1.getInt(3);
                String dot = rs1.getString(4);
                int asno = rs1.getInt(5);
                int csno = rs1.getInt(6);
                String status = rs1.getString(7);
    
                // s = String.format("select name, source, destination from train where tid='%d'", tid);
                // ResultSet rs2 = st.executeQuery(s);
                // rs2.next();

                histList += "<tr>";
                histList += "<td>" + Integer.toString(ticno) + "</td>"; //ticket no
                histList += "<td>"+ Integer.toString(tid)+"</td>"; // train id
                
                // histList += "<td>"+ st.executeQuery(String.format("select name, source, destination from train where tid='%d'", tid)).getString(1) +"</td>"; // train name
                // histList += "<td>"+ st.executeQuery(String.format("select name, source, destination from train where tid='%d'", tid)).getString(2) +"</td>"; // src
                // histList += "<td>"+ st.executeQuery(String.format("select name, source, destination from train where tid='%d'", tid)).getString(3) +"</td>"; // dest
                histList += "<td>"+ dot +"</td>"; // date of travel
                histList += "<td>"+ Integer.toString(asno+csno) +"</td>"; // no of seats
                histList += "<td>"+ status +"</td>"; // status
                histList += "</tr>";   
            }
            pw.println("</table>");
            System.out.println(histList);
            pw.println(histList);
            pw.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}