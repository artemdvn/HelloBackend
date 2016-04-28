package helloBackend;

import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestHelloBackend {
	
	@Test
	public void testRegex() {
		try {
			ManagementSystemTest ms = ManagementSystemTest.getInstance();
			List<Contact> contacts = ms.getFilteredContacts("^A.*$");
			assertEquals(4, contacts.size());
			contacts = ms.getFilteredContacts("^L.*$");
			assertEquals(3, contacts.size());
			contacts = ms.getFilteredContacts("^.*[aei].*$");
			assertEquals(0, contacts.size());
			contacts = ms.getFilteredContacts("^.*[L].*$");
			assertEquals(3, contacts.size());
		} catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
