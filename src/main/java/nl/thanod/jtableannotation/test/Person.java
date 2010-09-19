package nl.thanod.jtableannotation.test;

import nl.thanod.jtableannotation.JTableColumn;

public class Person implements SimplePersonView,AdminPersonView{
	
	private String name;

	private String firstname;

	@JTableColumn(index=1)
	private int age;
	
	public Person(String name, String firstname, int age) {
		super();
		this.name = name;
		this.firstname = firstname;
		this.age = age;
	}
	
	@Override
	public boolean isAdmin(){
		return firstname.startsWith( "Ko" ) || name.endsWith( "ijk" ) || age == 1337;
	}
	
	@Override
	public String toString(){
		return firstname + " " + name;
	}

	@Override
	public String getFirstname() {
		return this.firstname;
	}

	@Override
	public String getSurname() {
		return this.name;
	}
}
