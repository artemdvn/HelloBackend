package helloBackend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ManagementSystemTest {

    private static Connection con;
    private static ManagementSystemTest instance;
    
    private ManagementSystemTest() {
    }

    public static synchronized ManagementSystemTest getInstance() throws ClassNotFoundException {
        if (instance == null) {
        	try {
                instance = new ManagementSystemTest();
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/helloBackend";
                con = DriverManager.getConnection(url, "root", "admin");
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