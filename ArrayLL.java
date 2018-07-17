//ArrayLL.java
//Max Smiley
//Program # 5
//CS202

//A linear linked list of arrays, each with elements of type Event. The general
//design is that EventTree contains the aggregate of information about the festival,
//while ArrayLL is used by an individual user to build a list of events they may be
//interested in attending.

public class ArrayLL
{
	//helper node class for ArrayLL. Contains a reference to the next node, its own
	//size, the keyword with which it corresponds, and an array of Events (each Event
	//with the keyword corresponding to this node.
	private class Node
	{
		private int size;
		private String keyword;
		private Node next;
		private Event data[];

		//nondefault constructor is the only one used.
		private Node(LinearLL in_list, String kw)
		{
			this.keyword = kw;
			this.size = in_list.getLength();
			this.data = new Event[size];
			//as we cannot traverse a LLL ourselves, we ask LLL to populate the array for us.
			in_list.populate_array(data, 0);
		}

		//traverses the linked list until it either reaches the end, or finds
		//an extant node that has the same keyword as the list we're trying to add.
		private void add(LinearLL in_list, String kw)
		{
			if(this.next != null)
			{
				this.next.add(in_list, kw);
			}
			else
			{
				if(this.keyword == kw)
				{
					//creates a new array just large enough for existing data and new data.
					this.size += in_list.getLength();
					Event newdata[] = new Event[this.size];
					System.arraycopy(this.data, 0, newdata, 0, this.data.length);
					//as we cannot traverse a LLL ourselves, we ask LLL to populate the array for us.
					in_list.populate_array(newdata, this.data.length+1);
					this.data = newdata;
					return;
				}
				else
				{
					//make a new node if we've reached the end of the list.
					this.next = new Node(in_list, kw);
				}
			}
		}

		//prints to console. Helper function for ArrayLL. ArrayLL does not need to write to file, fortunately.
		private void print(int n)
		{
			System.out.println("Node #" + (n+1) + ": " + keyword);
			for(int i = 0; i < size; ++i)
			{
				System.out.print("#" + (i+1) + " ");
				data[i].write_console();
			}
			if(this.next != null)
			{
				next.print(++n);
			}
		}

		//there are 2 delete functions - one to remove an individual event and one to remove an entire keyword.
		//these both correspond to a wrapper function in ArrayLL.

		//this is the function to delete an individual event.
		private void delete(int x, int y)
		{
			if(x > 0)
			{
				this.next.delete(--x);
			}
			else if(x ==0)
			{
				if(y < 0 || y >= data.length)
				{
					return;
				}
				//make a new array that's one element smaller than the current data.
				--this.size;
				Event newArray[] = new Event [size];
				//copies over the portion of the array that's before the element to be removed.
				for(int i = 0; i < y; ++i)
				{
					newArray[i] = this.data[i];
				}
				//copies over the rest of the array, excluding the element to be removed.
				for(int i = y; i < this.size; ++i)
				{
					newArray[i] = this.data[i+1];
				}
				this.data = newArray;
			}
		}

		//deletes a keyword from the linked list - simply removes the node corresponding to that keyword.
		//the case where head is the node to be removed is handled in the wrapper function.
		private void delete(int x)
		{
			if(x > 0)
			{
				this.next.delete(--x);
			}
			else if(x == 0)
			{

				this.next = this.next.next;
			}

		}
	}
	private Node head;
	private int length;

	//wrapper function to add a keyword node to the linked list.
	protected void add(LinearLL in_list, String keyword)
	{
		if(head == null)
		{
			head = new Node(in_list, keyword);
		}
		else
		{
			head.add(in_list, keyword);
		}
		++length;
	}

	//wrapper function to print to console.
	protected void print()
	{
		if(head != null)
		{
			head.print(0);
		}
	}

	//deletes an individual event. Note that we must decrement, as
	//client-facing indicies start at one rather than zero.
	protected void delete(int x, int y)
	{
		if(x < 1 || x > this.length)
		{
			return;
		}
		else
		{
			x-=x;
			y-=y;
			head.delete(x, y);
		}
	}

	//deletes an entire node from the list. Note that we must subtract two
	//from x, one to shift the client-facing indicies to the correct ones,
	// and another as we will be modifying the node that POINTS TO the
	//node to be deleted - not the node itself.
	protected void delete(int x)
	{
		if(x < 1 || x > this.length)
		{
			return;
		}
		//if the user wanted to delete the head node, we simply shift head forward
		//by one node.
		if(x == 1)
		{
			head = head.next;
		}
		else
		{
			x -= 2;
			head.delete(x);
		}
	}

	protected int lengthOf()
	{
		return length;
	}

}
