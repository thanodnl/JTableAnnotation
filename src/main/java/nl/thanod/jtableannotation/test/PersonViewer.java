package nl.thanod.jtableannotation.test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import nl.thanod.jtableannotation.AnnotatedTableModel;

public class PersonViewer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2551030801127818713L;

	public PersonViewer(List<Person> persons){
		super("Person Viewer");
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final AnnotatedTableModel<Person> model = new AnnotatedTableModel<Person>(Person.class,persons);
		final JTable table = new JTable(model);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				System.out.println("Selected: " + Arrays.toString(model.getData(table.getSelectedRows())));
			}
		});
		this.add(new JScrollPane(table));
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		List<Person> persons = new LinkedList<Person>();
		persons.add(new Person("Zwaan", "Sam", 24));
		persons.add(new Person("Bollen", "Koen", 3));
		persons.add(new Person("Dijk", "Nils", 1337));
		for (int i = 0; i < 0; i++) {
			persons.add( new Person( "Je moeder #"+i, "Nils", i ));
		}
		new PersonViewer(persons);
	}
}
