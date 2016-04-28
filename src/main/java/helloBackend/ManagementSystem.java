package helloBackend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ManagementSystem {

    private static Connection con;
    private static ManagementSystem instance;
    private static DataSource dataSource;
    
    private ManagementSystem() {
    }

    public static synchronized ManagementSystem getInstance() {
        if (instance == null) {
        	try {
                instance = new ManagementSystem();
                Context ctx = new InitialContext();
                dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/ContactsDS");
                con = dataSource.getConnection();  
        	} catch (NamingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
    
    public List<Contact> getFilteredContacts(String regex) throws SQLException {
        List<Contact> contacts = new ArrayList<Contact>();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, name FROM contacts");

        Pattern p = Pattern.compile("");
        try {
        	p = Pattern.compile(regex); 
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
        }
               
		while (rs.next()) {
			String contactString = rs.getString(2);
			Matcher m = p.matcher(contactString);
			if (!m.matches()) {
				Contact sr = new Contact();
				sr.setId(rs.getInt(1));
				sr.setName(contactString);
				contacts.add(sr);
			}
		}
		rs.close();
		stmt.close();
		return contacts;
    }  
   
}