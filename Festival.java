//Festival.java
//Max Smiley
//Program # 5
//CS202


//****************************************IMPORTANT************************************************
//there is an issue with file i/o. If the program terminates early, nothing gets written to file.
//I could not figure out how to fix this, so I've included two text files - festival_data.txt is
//the file the program reads the tree out of, and backup.txt is a direct copy of it. If
//festival_data.txt doesn't get written properly: delete it, make a copy of backup.txt, and rename
//that copy to festival_data.txt. Copying the contents of the file will NOT work, you must copy
//the file itself. I know this is a serious flaw, especially since some of my input is not guarded
//and will lead to a crash. Sorry! :(
//*************************************************************************************************


//This is the main driver class for the project. The entire thing is wrapped in a do/while loop
//that will allow the user to continue adding data to the binary search tree and creating/modifying
//the flexible array.

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Festival
{
	public static void main(String args[]) throws Exception
	{
		Scanner in = new Scanner(System.in);

		//user defined types
		EventTree tree = new EventTree();
		ArrayLL flexarray = new ArrayLL();

		//used to add events to the tree.
		Event e;

		//used to read a list of keywords the user is interested in,
		//in order to populate the flexible array.
		ArrayList<String> keywords = new ArrayList();

		//multi-purpose string variable.
		String s = "s";

		//condition for the loop that adds elements to the tree.
		boolean b = true;
		//condition for terminating the program at the end of the do/while loop.
		boolean end = false;


		tree.read_from_file();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("festival_data.txt"));

		do
		{
			b = true;
			System.out.println("Would you like to add an element to the tree?");
			s = y_or_n(in);
			while (b)
			{


				if (s.compareTo("N") == 0)
				{
					b = false;
				} else if (s.compareTo("Y") == 0)
				{
					//read event from keyboard
					e = event_fkb(in);

					//event_fkb will return null if bad input
					if (e != null)
					{
						tree.add(e);
						e.write_to_file(oos);
					}
					System.out.println("Would you like to add another element to the tree?");
					s = y_or_n(in);
				}
			}
			System.out.println("Printing binary search tree:\n");
			//any word other than "all" will print the linked list corresponding to that keyword.
			tree.print("all");

			System.out.println("We will now prepare a list of events with keywords that you \n may be interested in. Please enter as many" +
					"\n keywords as you like. Empty input will stop the loop.");

			//add keywords to an arraylist. This is used to populate flexarray.
			while (s != null)
			{
				s = in.nextLine();
				if (s.compareTo("") == 0)
				{
					s = null;
				} else
				{
					s = s.toLowerCase();
					keywords.add(s);
				}
			}

			//fetches the LinearLL corresponding to each keyword, and adds it to the flexible array.
			for (String kw : keywords)
			{
				LinearLL list = tree.retrieve(kw);
				flexarray.add(list, kw);
			}

			System.out.println("Writing flexible array to console: ");
			flexarray.print();

			System.out.println("Would you like to remove any elements from the flexible array?");
			s = y_or_n(in);
			while(s.compareTo("Y") == 0)
			{
				int x, y;
				System.out.print("Which keyword index would you like to modify?");

				//this input is not guarded and corresponds to the indices printed in ArrayLL's print command.
				x = in.nextInt();
				if(x < 1 || x > flexarray.lengthOf())
				{
					System.out.println("INVALID INPUT");
					continue;
				}

				System.out.println("\nOK, index " + x + ". Which element of index?");
				System.out.print("0 will delete entire keyword entry: ");
				y = in.nextInt();
				if(y < 0)
				{
					System.out.println("INVALID INPUT");
					continue;
				}
				else if(y == 0)
				{
					flexarray.delete(x);
				}
				else if(y > 0)
				{
					flexarray.delete(x, y);
				}
				System.out.println("Would you like to continue editing the flexible array? ");
				s = y_or_n(in);

			}

			System.out.println("Would you like to go back to the beginning of the program? N will terminate.");
			s = y_or_n(in);
			if(s.compareTo("N") == 0)
			{
				end = true;
			}
		}while(end == false);

		System.out.println("Printing binary search tree: ");
		tree.print("all");
		System.out.println("Printing flexible array: ");
		flexarray.print();

		tree.write(oos);
		oos.close();
	}

	//fkb = from key-board. returns an event to main, to be added to the tree.
	private static Event event_fkb(Scanner in)
	{
		//just in case
		if(in == null)
		{
			in = new Scanner(System.in);
		}
		Event event;
		int x;

		//input here is not guarded either.
		System.out.println("\t1: Parade");
		System.out.println("\t2: Show");
		System.out.println("\t3: Fair");
		System.out.println("\t4: Competition");
		System.out.print("New event. Please select type: ");
		x = in.nextInt();

		while(x < 1 || x > 4)
		{
			System.out.print("\nINVALID INPUT. Try again: ");
			x = in.nextInt();
			in.nextLine();
		}

		switch(x)
		{
			case 1:
				event = new Parade();
				event.read_console();
				break;
			case 2:
				event = new Show();
				event.read_console();
				break;
			case 3:
				event = new Fair();
				event.read_console();
				break;
			case 4:
				event = new Competition();
				event.read_console();
				break;
			default:
				event = null;
		}
		return event;
	}

	//grabs yes or no input from user.
	private static String y_or_n(Scanner in)
	{
		String s;
		System.out.print("Y or N: ");
		s = in.next();
		in.nextLine();
		s = s.toUpperCase();
		while(s.compareTo("Y") != 0 && s.compareTo("N") != 0)
		{
			System.out.println("\nSorry, didn't get that. Try again.");
			s = in.next();
			in.nextLine();
			s = s.toUpperCase();
		}
		return s;
	}
}
