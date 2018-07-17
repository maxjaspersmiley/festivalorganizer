//Fair.java
//Max Smiley
//Program # 5
//CS202

import java.io.Serializable;
import java.util.Scanner;

//an event, with an ending day/time - as many fairs last multiple days.
public class Fair extends Event implements Serializable
{
	private Time end;

	protected Fair(){
		super();
	}

	protected void read_console(){
		super.read_console();
		Scanner kb = new Scanner(System.in);
		int m, d, t;
		System.out.print("\nEnd day");
		System.out.print("\n         Month: ");
		m = kb.nextInt();
		kb.nextLine();
		System.out.print("\n           Day: ");
		d = kb.nextInt();
		kb.nextLine();
		System.out.print("\n          Time: ");
		t = kb.nextInt();
		kb.nextLine();
		this.end = new Time(m, d, t);
	}
	protected void write_console(){
		super.write_console();
		System.out.println("\tEnds: " + end);
	}
}
