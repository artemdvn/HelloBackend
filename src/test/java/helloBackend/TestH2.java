package helloBackend;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.h2.tools.DeleteDbFiles;
import org.junit.Test;

public class TestH2 {

	@Test
	public void testRegex() {
		Connection conn = null;
		Statement stat = null;
		try {
			DeleteDbFiles.execute("~", "test", true);

			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:~/test");
			stat = conn.createStatement();

			stat.execute("create table contacts(id int primary key, name varchar(255))");
			stat.execute("insert into contacts values(1, 'Zaporizke shosse str. 22')");
			stat.execute("insert into contacts values(2, 'Nissan Center')");
			stat.execute("insert into contacts values(3, 'Laguna')");
			stat.execute("insert into contacts values(4, 'Dnipropetrivsk, Slobozhanski avn. 127')");

			List<Contact> contacts = ManagementSystem.getInstance().getFilteredContacts("^A.*$", conn);
			assertEquals(4, contacts.size());

			contacts = ManagementSystem.getInstance().getFilteredContacts("^L.*$", conn);
			assertEquals(3, contacts.size());

			contacts = ManagementSystem.getInstance().getFilteredContacts("^.*[aei].*$", conn);
			assertEquals(0, contacts.size());

			contacts = ManagementSystem.getInstance().getFilteredContacts("^.*[L].*$", conn);
			assertEquals(3, contacts.size());

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stat != null) {
					stat.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
