import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.io.*;
import java.lang.reflect.Executable;
import java.util.*;

public class GetStatus extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/rrs";
        String sqluser = "root";
        String sqlpwd = "krnb0502";

        int ticno = Integer.parseInt(req.getParameter("ticno"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Hey I am after forname method in get() - GetStatus class");
            
            Connection con = DriverManager.getConnection(url, sqluser, sqlpwd);

            Statement st = con.createStatement();

            System.out.println("Hey I am after create method in get() - GetStatus class");

            res.setContentType("text/html");
            PrintWriter pw = res.getWriter();

            String s = String.format("select * from ticket where ticno='%d'", ticno);
            ResultSet rs = st.executeQuery(s);
            rs.next();
            int pid = rs.getInt(2);
            // System.out.println("pid: "+ pid);
            int tid = rs.getInt(3);
            // System.out.println("tid: "+ tid);
            String dot = rs.getString(4);
            int asno = rs.getInt(5);
            int csno = rs.getInt(6);
            String status = rs.getString(7);
            s = String.format("select name from passenger where pid='%d'", pid);
            ResultSet rs1 = st.executeQuery(s);
            rs1.next();
            String pname = rs1.getString(1);
            System.out.println("pname: "+ pname);
            
            s = String.format("select name,source,destination from train where tid='%d'", tid);
            ResultSet rs2 = st.executeQuery(s);
            rs2.next();
            // System.out.println("pid: "+ pid);
            System.out.println("executed sql");


            String statusList = new String();
            statusList = pname + "*";
            statusList += dot + "*";
            statusList += rs2.getString(2) + "*";
            statusList += rs2.getString(3) + "*";
            statusList += Integer.toString(tid) + "*";
            statusList += rs2.getString(1) + "*";
            statusList += Integer.toString(asno) + "*";
            statusList += Integer.toString(csno) + "*";
            statusList += status;
            System.out.println(statusList);
            pw.println(statusList);
            pw.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}