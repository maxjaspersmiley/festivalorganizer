//Parade.java
//Max Smiley
//Program # 5
//CS202

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

//an event, with a list of streets that the parade will travel through.
public class Parade extends Event implements Serializable
{
	private ArrayList<String> route;

	protected Parade(){
		super();
		route = new ArrayList();
	}

	protected void read_console(){
		super.read_console();
		Scanner kb = new Scanner(System.in);
		route = new ArrayList<>();

		String s = new String("s");
		System.out.println("\nRoute, empty input will stop loop.");
		while(s != null)
		{
			System.out.print("\n        Street: ");
			s = kb.nextLine();
			if(s.compareTo("") == 0)
			{
				s = null;
			}
			else
			{
				route.add(s);
			}
		}
	}
	protected void write_console(){
		super.write_console();
		System.out.println("\tRoute:");
		for(int i = 0; i < route.size(); ++i)
		{
			System.out.println("\t\t" + route.get(i));
		}
	}
}
