import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.io.*;
import java.lang.reflect.Executable;
import java.util.*;

public class Confirm extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/rrs";
        String sqluser = "root";
        String sqlpwd = "krnb0502";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Hey I am after forname method in get() - Confirm class");
            
            Connection con = DriverManager.getConnection(url, sqluser, sqlpwd);

            Statement st = con.createStatement();

            System.out.println("Hey I am after create method in get() - Confirm class");

            String s = String.format("update ticket set ticstatus='Confirmed' where ticstatus='Waiting'");
            st.executeUpdate(s);

            PrintWriter pw = res.getWriter();
            pw.println("OK");
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}