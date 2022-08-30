import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.io.*;
import java.lang.reflect.Executable;
import java.util.*;

public class GetRecord extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/rrs";
        String sqluser = "root";
        String sqlpwd = "krnb0502";

        int ticno = Integer.parseInt(req.getParameter("ticno"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Hey I am after forname method in get() - GetRecord class");
            
            Connection con = DriverManager.getConnection(url, sqluser, sqlpwd);

            Statement st = con.createStatement();

            System.out.println("Hey I am after create method in get() - GetRecord class");

            res.setContentType("text/html");
            PrintWriter pw = res.getWriter();

            String s = String.format("select * from ticket where ticno='%d'", ticno);
            ResultSet rs = st.executeQuery(s);
            rs.next();
            int pid = rs.getInt(2);
            int tid = rs.getInt(3);
            String dot = rs.getString(4);
            int asno = rs.getInt(5);
            int csno = rs.getInt(6);
            String status = rs.getString(7);

            s = String.format("select name from passenger where pid='%d'", pid);
            ResultSet rs1 = st.executeQuery(s);
            rs1.next();
            String pname = rs1.getString(1);
            // System.out.println("pname: "+ pname);
            
            s = String.format("select name,source,destination from train where tid='%d'", tid);
            ResultSet rs2 = st.executeQuery(s);
            rs2.next();
            System.out.println("executed sql");


            String record = new String();
            record = "<table border='2' cellspacing='3' cellpadding='30' bordercolor='Blue' style='margin-left:420px;margin-top: 30px;'><tr><th>Ticket No.</th><th>Passenger Name</th><th>Train Number</th><th>Train Name</th><th>Source</th><th>Destination</th><th>Date of Travel</th><th>No of Seats</th><th>Cancel Now</th></tr>";
            record += "<tr>";
            record += "<td>" + Integer.toString(ticno) + "</td>"; //ticket no
            record += "<td>" + pname + "</td>"; // passenger name
            record += "<td>" + Integer.toString(tid) + "</td>"; // train id
            record += "<td>"+ rs2.getString(1) +"</td>"; // train name
            record += "<td>"+ rs2.getString(2) +"</td>"; // src
            record += "<td>"+ rs2.getString(3) +"</td>"; // dest
            record += "<td>"+ dot +"</td>"; // date of travel
            record += "<td>"+ Integer.toString(asno + csno) +"</td>"; // no of seats
            record += "<td><button class='open-button' onclick='cancel()'>Cancel Ticket</button></td>";
            record += "</tr>";
            System.out.println(record);
            pw.println(record);
            pw.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}