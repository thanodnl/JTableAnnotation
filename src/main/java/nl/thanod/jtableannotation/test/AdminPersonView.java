package nl.thanod.jtableannotation.test;

import nl.thanod.jtableannotation.JTableColumn;

public interface AdminPersonView extends SimplePersonView {
	@JTableColumn(index=1)
	boolean isAdmin();
}
