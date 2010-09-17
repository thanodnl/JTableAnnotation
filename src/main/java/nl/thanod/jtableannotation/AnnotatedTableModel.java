package nl.thanod.jtableannotation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class AnnotatedTableModel<T> extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4200156972894866858L;
	
	private final List<T> data;
	private final List<ColumnHeader> header;
	
	public AnnotatedTableModel(Class<T> clazz, Iterable<T> data){
		this(clazz);
		this.setData(data);
	}

	public AnnotatedTableModel(Class<T> clazz){
		this.data = new LinkedList<T>();
		this.header = ColumnHeader.getHeadersFromClass(clazz);
		Collections.sort(this.header);
	}
	
	public void setData(Iterable<T> data){
		this.data.clear();
		for (T t:data)
			this.data.add(t);
		this.fireTableDataChanged();
	}
	
	@Override
	public int getColumnCount() {
		return this.header.size();
	}

	@Override
	public int getRowCount() {
		return this.data.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		T t = this.data.get(row);
		ColumnHeader h = this.header.get(column);
		if (t == null || h == null)
			return null;
		return h.getValue(t);
	}
	
	@Override
	public String getColumnName(int column){
		return this.header.get(column).name;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex){
		return this.header.get(columnIndex).type;
	}
	
	public T[] getData(int ... indices){
		T[] ts = (T[])new Object[indices.length];
		for (int i = 0; i < indices.length; i++)
			ts[i] = this.getData(indices[i]);
		return ts;
	}
	
	public T getData(int index){
		return this.data.get(index);
	}
}
