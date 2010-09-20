package nl.thanod.jtableannotation.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NameGenerator {
	private static final List<String>	names;
	private static final List<String>	firstnames;
	private static final Random randy = new Random(System.currentTimeMillis());
	
	static {
		names = new ArrayList<String>();
		firstnames = new ArrayList<String>();

		InputStream inFirstnames = NameGenerator.class.getClassLoader().getResourceAsStream("firstnames");
		InputStream inLastnames = NameGenerator.class.getClassLoader().getResourceAsStream("lastnames");

		if (inFirstnames != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inFirstnames));
			String name;
			try {
				while ((name = reader.readLine()) != null)
					firstnames.add(name);
			} catch (IOException ball) {}
		}
		if (firstnames.size() == 0)
			firstnames.add("John");

		if (inLastnames != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inLastnames));
			String name;
			try {
				while ((name = reader.readLine()) != null)
					names.add(name);
			} catch (IOException ball) {
			}
		}
		if (names.size() == 0)
			names.add("Doe");
	}
	
	public static String getName(){
		return names.get(randy.nextInt(names.size()));
	}
	
	public static String getFirstName(){
		return firstnames.get(randy.nextInt(firstnames.size()));
	}
}
