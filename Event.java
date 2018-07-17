//Event.java
//Max Smiley
//Program # 5
//CS202

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

//abstract base class to describe a single event in the festival.
//Implements comparable for tree/list insertion, and serializable
//for file input/output.
abstract public class Event implements Comparable<Event>, Serializable
{
	//allows us to write each event to file only once.
	private boolean hasBeenPrinted;

	//the private data
	private String name;
	private String location;
	private Time start;
	protected ArrayList<String> keywords;

	protected Event(){
		keywords = new ArrayList<>();
		hasBeenPrinted = false;
	}


	//two overrides to allow comparison for tree/list insertion.
	@Override
	public String toString(){return this.name;}

	@Override
	public int compareTo(Event event){return this.name.compareTo(event.name);}

	//reads an event from the console.
	protected void read_console(){
		Scanner kb = new Scanner(System.in);
		int m, d, t;

		System.out.print("\nEvent name:     ");
		this.name = kb.nextLine();
		System.out.print("\nEvent location: ");
		this.location = kb.nextLine();

		String s = new String("s");
		System.out.print("\nKeywords, empty input will stop loop.");
		while(s != null)
		{
			System.out.print("\n       Keyword: ");
			s = kb.nextLine();
			if(s.compareTo("") == 0)
			{
				s = null;
			}
			else
			{
				s = s.toLowerCase();
				keywords.add(s);
			}
		}
		System.out.print("\nStart day");
		System.out.print("\n         Month: ");
		m = kb.nextInt();
		kb.nextLine();
		System.out.print("\n           Day: ");
		d = kb.nextInt();
		kb.nextLine();
		System.out.print("\n          Time: ");
		t = kb.nextInt();
		kb.nextLine();
		this.start = new Time(m, d, t);
	}

	protected void write_console(){
		System.out.println(name + "\n" + "\tLocation: " + location);
		System.out.println("\tKeywords: ");
		for(int i = 0; i < keywords.size(); ++i)
		{
			System.out.println("\t\t" + keywords.get(i));
		}
		System.out.println("\tStarts: " + start);
	}

	//we call this on every event we read from a file, to make sure it's written correctly at end of program.
	protected void reset_print_flag()
	{
		hasBeenPrinted = false;
	}
	//check to see if we've already written event to file, so we don't have duplicates.
	protected boolean has_printed()
	{
		return hasBeenPrinted;
	}
	//writes the event to the output file.
	protected void write_to_file(ObjectOutputStream oos)
	{
		try
		{
			oos.writeObject(this);
			hasBeenPrinted = true;
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}
