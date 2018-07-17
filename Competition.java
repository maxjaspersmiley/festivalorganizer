//Competition.java
//Max Smiley
//Program # 5
//CS202

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

//an event, plus a list of competitors and a boolean describing
//whether the event is open to the public or not. for example,
//a marathon would be open and a car race would be closed.
class Competition extends Event implements Serializable
{
	private boolean closed;
	private ArrayList<String> competitors;

	protected Competition(){
		super();
		competitors = new ArrayList();
	}

	protected void read_console(){
		super.read_console();
		Scanner kb = new Scanner(System.in);
		String s = "s";
		System.out.print("\nCompetitors, empty input will stop loop.");
		while(s != null)
		{
			System.out.print("\n   Competitor: ");
			s = kb.nextLine();
			if(s.compareTo("") == 0)
			{
				s = null;
			}
			else
			{
				competitors.add(s);
			}
		}
		s = null;
		while(s == null)
		{
			System.out.print("\nOpen to public?\n(Y or N): ");
			s = kb.next();
			kb.nextLine();
			s = s.toUpperCase();
			if(s.compareTo("Y") == 0)
			{
				closed = false;
			}
			else if(s.compareTo("N") == 0)
			{
				closed = true;
			}
			else s = null;
		}
	}
	protected void write_console(){
		super.write_console();
		Scanner kb = new Scanner(System.in);
		System.out.println("\tCompetitors: ");
		for(int i = 0; i < competitors.size(); ++i)
		{
			System.out.println("\t\t" + competitors.get(i));
		}
		if(closed)
		{
			System.out.println("\tClosed entry.");
		}
		else
		{
			System.out.println("\tOpen entry.");
		}
	}
}
