//LinearLL.java
//Max Smiley
//Program # 5
//CS202

import java.io.ObjectOutputStream;

//An instance of this class is contained within every node in the event tree.
//It has its own Node class, plus a reference to the head node and a length.
//does not implement tail pointers.
class LinearLL
{
	//node class for managing individual elements within the linked list. Each
	//node has an instance of Event and a next node reference.
	private class Node
	{
		private Node next;
		private Event data;

		//nondefault constructor is the only one called. Makes insertion easy.
		private Node(Event event, Node newnext)
		{
			data = event;
			this.next = newnext;
		}

		//called by the LinearLL class to copy a linked list into an array of events.
		//helper funtion for LinearLL's helper function for ArrayLL.
		private void populate_array(Event[] array, int i)
		{
			array[i] = this.data;
			if(this.next != null)
			{
				this.next.populate_array(array, ++i);
			}
		}

		private void print()
		{
			data.write_console();
		}

		//adds an event to the list. helper function for list class.
		private Node add(Event event)
		{
			if(this.data.compareTo(event) <= 0 || this.next == null)
			{
				Node node = new Node(event, this.next);
				this.next = node;
				return this;
			}
			else
			{
				this.next.add(event);
				return this;
			}
		}

		//removes an event by name from the list. helper function for list class.
		private boolean remove_item(String term)
		{
			if(this.next == null)
			{
				return false;
			}
			else if(this.next.data.toString().compareTo(term) == 0)
			{
				this.next = this.next.next;
				return true;
			}
			else
			{
				return this.next.remove_item(term);
			}
		}
	}
	private Node head;
	private int length;

	//default constructor does not initialize head - we rather check on every insertion
	//to see that head exists. This protects us in the case that the client deletes
	//every node from the list and then tries to insert.
	protected LinearLL()
	{
		this.length = 0;
	}


	//write to console. Will print the index requested by the client (starting at 1)
	//if the client enters 0 the entire list will print. The latter is the only
	//mode in which this function is called currently.
	protected boolean print(int index)
	{
		if(index > length || index < 0)
		{
			return false;
		}

		if(index == 0)
		{
			Node node = head;
			int i = 1;
			while(node != null)
			{
				System.out.print(i + ": ");
				node.print();
				System.out.println("\n");
				++i;
				node = node.next;
			}
			return true;
		}

		else
		{
			Node node = head;
			while(index > 1)
			{
				node = node.next;
				--index;
			}
			node.print();
			return true;
		}
	}

	//writes each element to the file, provided its has_been_printed flag is not set.
	protected void write_list(ObjectOutputStream oos)
	{
		Node node = head;
		while(node != null)
		{
			if(!node.data.has_printed())
			{
				node.data.write_to_file(oos);
			}
			node = node.next;
		}
	}



	//not currently used. removes a given index from the list, returns true
	//if index was found, false if not.
	protected boolean remove(int index)
	{
		if(this.head == null || index < 1 || index > length)
		{
			return false;
		}

		if(index == 1)
		{
			head = head.next;
			--length;
			return true;
		}

		Node node = head;
		while(index > 2)
		{
			node = node.next;
			--index;
		}

		node.next = node.next.next;
		--length;
		return true;
	}

	//removes an event by name from the list.
	protected boolean remove(String term)
	{
		if(this.head == null)
		{
			return false;
		}
		else
		{
			return head.remove_item(term);
		}
	}

	//fetches an event at a given index, not currently used.
	protected Event get(int index)
	{
		if(this.head == null || index < 1 || index > length)
		{
			return null;
		}
		Node node = head;

		while(index > 1)
		{
			node = node.next;
			--index;
		}
		return node.data;
	}

	//helper function for ArrayLL. Calles the same-named function in Node to recurse through
	//the list and add every element to the passed-in array.
	protected void populate_array(Event[] array, int i)
	{
		head.populate_array(array, 0);
	}


	protected int getLength()
	{
		return length;
	}

	//adds an event to the list.
	protected void add(Event event)
	{
		//we don't initialize head in the default constructor for reasons described
		//above, so we must check for it here.
		if(this.head == null)
		{
			this.head = new Node(event, null);
		}
		else
		{
			Node node = head.add(event);
			if(node != head)
			{
				head = node;
			}
		}
		++length;
	}


}

