package nl.thanod.jtableannotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

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
		int diff = index-o.index;
		if (diff == 0)
			diff = this.name.compareTo(o.name);
		return diff;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColumnHeader other = (ColumnHeader) obj;
		if (index != other.index)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + ((f == null) ? 0 : f.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			FieldColumnHeader other = (FieldColumnHeader) obj;
			if (f == null) {
				if (other.f != null)
					return false;
			} else if (!f.equals(other.f))
				return false;
			return true;
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + ((m == null) ? 0 : m.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			MethodColumnHeader other = (MethodColumnHeader) obj;
			if (m == null) {
				if (other.m != null)
					return false;
			} else if (!m.equals(other.m))
				return false;
			return true;
		}
		
		
	}
	
	public static Set<ColumnHeader> getHeadersFromClass(Class<?> clazz){
		if (clazz == null)
			return Collections.emptySet();
		Set<ColumnHeader> columns = new TreeSet<ColumnHeader>();
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
