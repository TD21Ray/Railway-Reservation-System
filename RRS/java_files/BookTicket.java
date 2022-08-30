import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class BookTicket extends HttpServlet {
    public int addTicket(String pname, String trname, int trno, String src, String dest, String dot, int asno, int csno) {
        String url = "jdbc:mysql://localhost:3306/rrs";
        String sqluser = "root";
        String sqlpwd = "krnb0502";

        try {
            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("Hey I am after forname method in addTicket() - Bookticket class");
            
            Connection con = DriverManager.getConnection(url, sqluser, sqlpwd);

            Statement st = con.createStatement();

            System.out.println("Hey I am after create method in addTicket() - Bookticket class");

            String s = String.format("select pid from passenger where name='%s'", pname);
            ResultSet rs = st.executeQuery(s);
            if(rs.next()) {
                int pid = rs.getInt(1);
                s = String.format("insert into ticket(pid, tid, dot, ano, cno, ticstatus) values('%d', '%d', '%s', '%d', '%d', 'Waiting')", pid, trno, dot, asno, csno);
                st.executeUpdate(s);
                System.out.println("Ticket details inserted...");
            }
            else {
                System.out.println("Passenger doesn't exist");
                return 0;
            }
            return 1;
        }
        catch(Exception e) {
            System.out.println(e);
            return -1;
        }
    }
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pname = req.getParameter("spname");
        String trname = req.getParameter("stname");
        int trno = Integer.parseInt(req.getParameter("stno"));
        String src = req.getParameter("sfrom");
        String dest = req.getParameter("sto");
        String dot = req.getParameter("sdot");
        int asno = Integer.parseInt(req.getParameter("sasno"));
        int csno = Integer.parseInt(req.getParameter("scsno"));
        System.out.println("Ticket to be added...");
        System.out.println(pname + ", " + trname + ", " + trno + ", " + src + ", " + dest + ", " + dot + ", " + asno + ", " + csno);
        try {
            int result = addTicket(pname, trname, trno, src, dest, dot, asno, csno);
            if(result == 1) {
                System.out.println("Ticket Booked...");
                res.sendRedirect("payment.html");
            }
            else {
                System.out.println("Ticket not booked...");
                res.sendRedirect("bookTicket.html");
            }
            // res.sendRedirect("payment.html");
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}