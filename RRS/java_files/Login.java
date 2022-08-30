import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class Login extends HttpServlet {
    public int loginUser(String email, String password) throws Exception{
        String url = "jdbc:mysql://localhost:3306/rrs";
        String sqluser = "root";
        String sqlpwd = "krnb0502";

        Class.forName("com.mysql.jdbc.Driver");

        System.out.println("Hey I am after forname method in loginUser() - Login class");
        
        Connection con = DriverManager.getConnection(url, sqluser, sqlpwd);

        Statement st = con.createStatement();

        System.out.println("Hey I am after create method in loginUser() - Login class");

        try {
            String s = String.format("select pwd from passenger where email = '%s'", email);
            ResultSet rs = st.executeQuery(s);
            if(rs.next()) {
                String pwd = rs.getString(1);

                if(pwd.equals(password)) {
                    System.out.println("Password matches...");
                    if((email.equalsIgnoreCase("admin@gmail.com") && pwd.equalsIgnoreCase("admin"))) {
                        return 2;
                    }
                    return 1;
                }
                else {
                    return 0; // invalid password
                }     
            }
            else {
                return -1; // invalid username
            }
        }
        catch(Exception e) {
            System.out.println(e);
            return -1;
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String email = req.getParameter("email");
        String pwd = req.getParameter("password");
        System.out.println(email + ", " + pwd);

        try {
            int result = loginUser(email, pwd);
            if(result == -1) {
                System.out.println("Invalid Username");
                Cookie c = new Cookie("InvalidOrNot", "invalid");
                c.setMaxAge(2);
                res.addCookie(c);
                res.sendRedirect("index.html");
            }
            else if(result == 0) {
                System.out.println("Invalid Password");
                Cookie c = new Cookie("InvalidOrNot", "invalid");
                c.setMaxAge(2);
                res.addCookie(c);
                res.sendRedirect("index.html");
            }
            else if(result == 1) {
                System.out.println("Logging in...");
                Cookie c = new Cookie("InvalidOrNot", "valid");
                c.setMaxAge(2);
                res.addCookie(c);
                Cookie c1 = new Cookie("email", email);
                res.addCookie(c1);
                res.sendRedirect("home.html");
            }
            else if(result == 2) {
                System.out.println("Admin Logging in...");
                Cookie c = new Cookie("InvalidOrNot", "valid");
                c.setMaxAge(2);
                res.addCookie(c);
                Cookie c1 = new Cookie("email", email);
                res.addCookie(c1);
                res.sendRedirect("ahome.html");
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}