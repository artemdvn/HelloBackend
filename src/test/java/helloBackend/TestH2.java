package helloBackend;

import static org.junit.Assert.assertEquals;

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

import org.h2.tools.DeleteDbFiles;
import org.junit.Test;

public class TestH2 {
	
	@Test
	public void testRegex() {
		try {
			DeleteDbFiles.execute("~", "test", true);

	        Class.forName("org.h2.Driver");
	        Connection conn = DriverManager.getConnection("jdbc:h2:~/test");
	        Statement stat = conn.createStatement();
	        
	        stat.execute("create table contacts(id int primary key, name varchar(255))");
	        stat.execute("insert into contacts values(1, 'Zaporizke shosse str. 22')");
	        stat.execute("insert into contacts values(2, 'Nissan Center')");
	        stat.execute("insert into contacts values(3, 'Laguna')");
	        stat.execute("insert into contacts values(4, 'Dnipropetrivsk, Slobozhanski avn. 127')");
	        
	        ResultSet rs = stat.executeQuery("select * from contacts");	        
			List<Contact> contacts = getFilteredContacts(rs, "^A.*$");
			assertEquals(4, contacts.size());
			
			rs = stat.executeQuery("select * from contacts");
			contacts = getFilteredContacts(rs, "^L.*$");
			assertEquals(3, contacts.size());
			
			rs = stat.executeQuery("select * from contacts");
			contacts = getFilteredContacts(rs, "^.*[aei].*$");
			assertEquals(0, contacts.size());
			
			rs = stat.executeQuery("select * from contacts");
			contacts = getFilteredContacts(rs, "^.*[L].*$");
			assertEquals(3, contacts.size());
			
			rs.close();
			stat.close();
		    conn.close();
		        
		} catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public List<Contact> getFilteredContacts(ResultSet rs, String regex) throws SQLException {
        List<Contact> contacts = new ArrayList<Contact>();

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
		return contacts;
    }  

}
