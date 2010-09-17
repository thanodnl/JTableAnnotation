package nl.thanod.jtableannotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class ColumnHeader implements Comparable<ColumnHeader> {
	public final Class<?> type;
	public final String name;
	public final int index;

	public ColumnHeader(Class<?> type, String name, int index){
		this.type = typeResolver(type);
		this.name = name;
		this.index = index;
	}
	
	@Override
	public int compareTo(ColumnHeader o) {
		return index-o.index;
	}
	
	public abstract Object getValue(Object o);
	
	private static String nameResolver(String first, String second){
		if (first == null || first.length() == 0)
			return second;
		return first;
	}
	
	private static Class<?> typeResolver(Class<?> clazz){
		if (clazz.equals(int.class))
			return Integer.class;
		if (clazz.equals(float.class))
			return Float.class;
		if (clazz.equals(double.class))
			return Double.class;
		if (clazz.equals(boolean.class))
			return Boolean.class;
		if (clazz.equals(short.class))
			return Short.class;
		if (clazz.equals(byte.class))
			return Byte.class;
		if (clazz.equals(char.class))
			return Character.class;
		if (clazz.equals(long.class))
			return Long.class;
		return clazz;
	}
	
	static class FieldColumnHeader extends ColumnHeader {

		private final Field f;

		public FieldColumnHeader(JTableColumn a, Field f) {
			super(f.getType(), nameResolver(a.value(),f.getName()), a.index());
			this.f = f;
			if (!this.f.isAccessible())
				this.f.setAccessible(true);
		}

		@Override
		public Object getValue(Object o) {
			try {
				return this.f.get(o);
			} catch (Throwable ball){
				return ball.getClass().getCanonicalName();
			}
		}
		
	}
	
	static class MethodColumnHeader extends ColumnHeader {

		private final Method m;

		public MethodColumnHeader(JTableColumn a, Method m) {
			super(m.getReturnType(),nameResolver(a.value(),m.getName()), a.index());
			this.m = m;
			if (!this.m.isAccessible())
				this.m.setAccessible(true);
		}

		@Override
		public Object getValue(Object o) {
			try {
				return this.m.invoke(o);
			} catch (Throwable ball) {
				return ball.getClass().getCanonicalName();
			}
		}
	}
	
	public static List<ColumnHeader> getHeadersFromClass(Class<?> clazz){
		if (clazz == null)
			return Collections.emptyList();
		List<ColumnHeader> columns = new LinkedList<ColumnHeader>();
		for(Field f:clazz.getDeclaredFields()){
			JTableColumn a = f.getAnnotation(JTableColumn.class);
			if (a == null)
				continue;
			columns.add(new FieldColumnHeader(a, f));
		}
		for(Method m:clazz.getDeclaredMethods()){
			JTableColumn a = m.getAnnotation(JTableColumn.class);
			if (a == null)
				continue;
			columns.add(new MethodColumnHeader(a, m));
		}
		columns.addAll(getHeadersFromClass(clazz.getSuperclass()));
		for(Class<?> c:clazz.getInterfaces())
			columns.addAll(getHeadersFromClass(c));
		return columns;
	}
}
