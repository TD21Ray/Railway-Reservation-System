import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class Signup extends HttpServlet {
    public int addPassenger(String name, String email, String pwd, char gender, String city, String contact) throws Exception{
        String url = "jdbc:mysql://localhost:3306/rrs";
        String sqluser = "root";
        String sqlpwd = "krnb0502";

        Class.forName("com.mysql.jdbc.Driver");

        System.out.println("Hey I am after forname method in addUser() - Signup class");
        
        Connection con = DriverManager.getConnection(url, sqluser, sqlpwd);

        Statement st = con.createStatement();

        System.out.println("Hey I am after create method in addUser() - Signup class");

        try {
            String s = String.format("select count(*) from passenger where email = '%s'", email);
            ResultSet rs = st.executeQuery(s);
            rs.next();
            int count = rs.getInt(1);
            if(count > 0) {
                return 0;
            }
            s = String.format("insert into passenger(email, pwd, name, gender, city, contact) values('%s','%s','%s','%s','%s','%s')", email, pwd, name, gender, city, contact);
            st.executeUpdate(s);
            System.out.println("Passenger record inserted...");
        }
        catch(Exception e) {
            System.out.println(e);
            return -1;
        }
        return 1;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String name = req.getParameter("fullname");
        String email = req.getParameter("email");
        String pwd = req.getParameter("password");
        String gender = req.getParameter("gender");
        String city = req.getParameter("city");
        String contact = req.getParameter("contact");
        System.out.println(name + ", " + email + ", " + pwd + ", " + gender + ", " + city + ", " + contact);

        try {
            int result = addPassenger(name, email, pwd, gender.charAt(0), city, contact);
            if(result == 0) {
                System.out.println("Username already exists...");
                Cookie c = new Cookie("InvalidSignup", "invalid");
                c.setMaxAge(2);
                res.addCookie(c);
                res.sendRedirect("signup.html");
            }
            else if(result == 1) {
                System.out.println("Passenger Signed Up");
                Cookie c = new Cookie("InvalidSignup", "valid");
                c.setMaxAge(2);
                res.addCookie(c);
                Cookie c1 = new Cookie("email", email);
                res.addCookie(c1);
                res.sendRedirect("home.html");
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}