import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.io.*;
import java.lang.reflect.Executable;
import java.util.*;

public class WaitingList extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/rrs";
        String sqluser = "root";
        String sqlpwd = "krnb0502";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Hey I am after forname method in get() - WaitingList class");
            
            Connection con = DriverManager.getConnection(url, sqluser, sqlpwd);

            Statement st = con.createStatement();

            System.out.println("Hey I am after create method in get() - WaitingList class");

            res.setContentType("text/html");
            PrintWriter pw = res.getWriter();

            String wlist = new String();

            String s = String.format("select * from ticket where ticstatus='Waiting'");
            ResultSet rs = st.executeQuery(s);
            wlist = "<table border='2' cellspacing='3' cellpadding='30' bordercolor='Blue' style='margin-left: 320px; margin-top: 30px;'><tr><th>Ticket No.</th><th>Passenger Name</th><th>Train Number</th><th>Train Name</th><th>Source</th><th>Destination</th><th>Date of Travel</th><th>No of Seats</th></tr>";
            while(rs.next()) {
                int ticno = rs.getInt(1);
                int pid = rs.getInt(2);
                int tid = rs.getInt(3);
                String dot = rs.getString(4);
                int asno = rs.getInt(5);
                int csno = rs.getInt(6);
                String status = rs.getString(7);
                System.out.println(">> WaitingList.java: Read columns");

                Statement st1 = con.createStatement();
                s = String.format("select name from passenger where pid='%d'", pid);
                ResultSet rs1 = st1.executeQuery(s);
                rs1.next();
                String pname = rs1.getString(1);
                // System.out.println("pname: "+ pname);
                rs1.close();
                System.out.println(">> WaitingList.java: Passenger Query Executed");
                
                Statement st2 = con.createStatement();
                s = String.format("select name,source,destination from train where tid='%d'", tid);
                ResultSet rs2 = st2.executeQuery(s);
                rs2.next();
                System.out.println(">> WaitingList.java: Train Query Executed");


                String record = new String();
                record += "<tr>";
                record += "<td>" + Integer.toString(ticno) + "</td>"; //ticket no
                record += "<td>" + pname + "</td>"; // passenger name
                record += "<td>" + Integer.toString(tid) + "</td>"; // train id
                record += "<td>" + rs2.getString(1) + "</td>"; // train name
                record += "<td>" + rs2.getString(2) + "</td>"; // src
                record += "<td>" + rs2.getString(3) + "</td>"; // dest
                record += "<td>" + dot + "</td>"; // date of travel
                record += "<td>" + Integer.toString(asno + csno) + "</td>"; // no of seats
                record += "</tr>";
                rs2.close();
                wlist += record;
                System.out.println(">> WaitingList.java: Table Row Generated");
            }
            wlist += "</table>";
            System.out.println(">> WaitingList.java: Table Generated");
            
            System.out.println(wlist);
            pw.println(wlist);
            pw.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}