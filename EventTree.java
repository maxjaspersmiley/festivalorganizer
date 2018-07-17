//EventTree.java
//Max Smiley
//Program # 5
//CS202

import java.io.*;

//The binary search tree that is used to organize events in our festival.
//Note that currently, the program does not support removal of events from
//the tree. The functions exist but are not called from main. This is because
//from the client side, we provide an ArrayList to allow the client to build
//a list of events he/she may be interested in attending. The ArrayList does
//allow the user to add/remove events at will. This also means that care
//should be taken to not input incorrect information in the list, as there
//is not a way to remove it.

class EventTree
{
	//the node class that will model our individual linear linked list data.
	//note that this class keeps track of the keyword, rather than LinearLL. This means
	//that we could conceivable use LinearLL to organize events by something other
	//than keyword.
	private class Node
	{
		private String kw;
		private Node left;
		private Node right;
		private LinearLL data;

		//makes a new node(keyword LLL) from an event;
		private Node(Event event, String key)
		{
			data = new LinearLL();
			data.add(event);
			kw = key;
		}

		//writes a linked list to console.
		private void print()
		{
			System.out.println("List of events with keyword '" + kw + "'");
			System.out.println("****************************************");
			data.print(0);
			System.out.println("****************************************");
		}

		//helper function for tree class, prints entire subttree starting at this.
		//is currently only used on head, but could be used to print any subtree.
		private void print_tree()
		{
			if(left != null)
			{
				left.print_tree();
			}
			this.print();
			if(right != null)
			{
				right.print_tree();
			}
		}

		//writes the subtree to output file by calling LinearLL's write function,
		//but will only write each event once. Currently only called on head to
		//write the entire tree to file.
		private void write_tree(ObjectOutputStream oos)
		{
			if(left != null)
			{
				left.write_tree(oos);
			}
			this.data.write_list(oos);
			if(right!= null)
			{
				right.write_tree(oos);
			}
		}



		//fetches the node(keyword LLL) for the given keyword. This is used to insert.
		private Node find(String key)
		{
			if(this.kw.compareTo(key) == 0)
			{
				return this;
			}
			if(key.compareTo(this.kw) < 0)
			{
				if(left == null)
				{
					return null;
				}
				return left.find(key);
			}
			else if(key.compareTo(this.kw) > 0)
			{
				if(right == null)
				{
					return null;
				}
				return right.find(key);
			}
			return null;
		}

		//adds an event to an existing keyword LLL
		private void add_event(Event event)
		{
			data.add(event);
		}

		//helper function for tree class, inserts a node into the tree.
		private void add_node(Node node)
		{
			if(node.kw.compareTo(this.kw) < 0)
			{
				if(left == null)
				{
					left = node;
					return;
				}
				left.add_node(node);
			}
			else
			{
				if(right == null)
				{
					right = node;
					return;
				}
				right.add_node(node);
			}
		}
	}
	private Node root;

	protected EventTree(){}

	//prints all events of given keyword in the list. returns false if keyword not found, true otherwise.
	protected boolean print(String word)
	{
		if (root == null)
		{
			return false;
		}
		String wordUpper = word.toUpperCase();
		if(wordUpper.compareTo("ALL") == 0)
		{
			root.print_tree();
		}
		else
		{
			Node node = root.find(word);
			if(node == null)
			{
				return false;
			}
			node.print();
		}
		return true;
	}

	//writes the entire tree to output file, but each event only once.
	protected void write(ObjectOutputStream oos)
	{
		if(root != null)
		{
			root.write_tree(oos);
		}
	}


	//adds the given event to the LLL corresponding to each of its keywords.
	//If the keyword has not yet occurred, creates a new node containing that keyword.
	protected void add(Event event)
	{
		Node node = null;

		for(String kw : event.keywords)
		{
			if (root == null)
			{
				root = new Node(event, kw);
				continue;
			}
			else
			{
				node = root.find(kw);
			}
			if(node == null)
			{
				node = new Node(event, kw);
				root.add_node(node);
			}
			else
			{
				node.add_event(event);
			}
		}
	}

	//reads the tree from file. Called at the beginning of the program to populate the tree.
	protected boolean read_from_file()
	{
		try
		{
			FileInputStream file = new FileInputStream("festival_data.txt");
			try
			{
				if(file.available() == 0)
				{
					return false;
				}
				ObjectInputStream ois = new ObjectInputStream(file);
				while (file.available() > 0)
				{
					Object o = ois.readObject();
					if (o instanceof Competition)
					{
						Competition event = (Competition) o;
						event.reset_print_flag();
						add(event);
					} else if (o instanceof Fair)
					{
						Fair event = (Fair) o;
						event.reset_print_flag();
						add(event);
					} else if (o instanceof Show)
					{
						Show event = (Show) o;
						event.reset_print_flag();
						add(event);
					} else if (o instanceof Parade)
					{
						Parade event = (Parade) o;
						event.reset_print_flag();
						add(event);
					}
				}
				ois.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return true;
	}

	//returns the list of all nodes with a given keyword. Used by ArrayLL to
	//populate itself from linked lists.
	protected LinearLL retrieve(String key)
	{
		LinearLL lll = root.find(key).data;
		return lll;
	}

	//searches the tree for every keyword in event, and removes event
	//from each of those linked lists. Not currently called.
	protected boolean remove(Event event)
	{
		Node node = null;
		boolean b = false;
		for(String kw : event.keywords)
		{
			node = root.find(kw);
			b = node.data.remove(event.toString());
		}
		return b;
	}

}

