import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class DeleteTrain extends HttpServlet {
    public int deleteTrain(String tid) throws Exception{
        String url = "jdbc:mysql://localhost:3306/rrs";
        String sqluser = "root";
        String sqlpwd = "krnb0502";

        Class.forName("com.mysql.cj.jdbc.Driver");

        System.out.println("Hey I am after forname method in deleteTrain() - DeleteTrain class");
        
        Connection con = DriverManager.getConnection(url, sqluser, sqlpwd);

        Statement st = con.createStatement();

        System.out.println("Hey I am after create method in deleteTrain() - DeleteTrain class");
        int ti=Integer.parseInt(tid);
        try {
            String s = String.format("select count(*) from train where tid = '%d'", ti);
            ResultSet rs = st.executeQuery(s);
            rs.next();
            int count = rs.getInt(1);
            if(count == 0) {
                return 0;
            }

            s = String.format("delete from train where tid = '%d'", ti);
            st.executeUpdate(s);
            System.out.println("Train Record deleted...");
        }
        catch(Exception e) {
            System.out.println(e);
            return -1;
        }
        return 1;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        String id = req.getParameter("tid");
        System.out.println(id);
        try {
            int result = deleteTrain(id);
            if(result == 0) {
                System.out.println("Train Record does not exist...");
                res.sendRedirect("tnd.html");
            }
            else if(result == 1) {
                System.out.println("Train Record deleted...");
                res.sendRedirect("td.html");
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}