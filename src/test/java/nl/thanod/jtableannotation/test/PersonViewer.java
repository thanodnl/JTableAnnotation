package nl.thanod.jtableannotation.test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

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
		table.setRowSorter(new TableRowSorter<AnnotatedTableModel<Person>>(model));
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int [] selected = table.getSelectedRows();
				for (int i=0; i<selected.length; i++)
					selected[i] = table.convertRowIndexToModel(selected[i]);
				System.out.println("Selected: " + Arrays.toString(model.getData(selected)));
			}
		});
		this.add(new JScrollPane(table));
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		List<Person> persons = new LinkedList<Person>();
		Random randy = new Random(System.currentTimeMillis());
		for (int i = 0; i < 100000; i++) {
			persons.add(new Person(NameGenerator.getName(),NameGenerator.getFirstName(), randy.nextInt(100)));
		}
		new PersonViewer(persons);
	}
}
