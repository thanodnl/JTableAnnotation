package nl.thanod.jtableannotation.test;

import nl.thanod.jtableannotation.JTableColumn;

public class Person {
	
	@JTableColumn("Surname")
	private String name;
	
	@JTableColumn("Firstname")
	private String firstname;
	
	@JTableColumn
	private int age;
	
	public Person(String name, String firstname, int age) {
		super();
		this.name = name;
		this.firstname = firstname;
		this.age = age;
	}
	
	@JTableColumn
	public boolean isAdmin(){
		return firstname.startsWith( "Ko" ) || name.endsWith( "ijk" ) || age == 1337;
	}
	
	@Override
	public String toString(){
		return firstname + " " + name;
	}
}
