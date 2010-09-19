package nl.thanod.jtableannotation.test;

import nl.thanod.jtableannotation.JTableColumn;

public interface SimplePersonView {
	@JTableColumn("Firstname")
	String getFirstname();
	@JTableColumn("Surname")
	String getSurname();
}
