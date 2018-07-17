//simple class for managing dates/times of events.

import java.io.Serializable;

class Time implements Comparable<Time>, Serializable
{
	int month;
	int day;
	int time; //in hours


	//default constructor
	Time(int m, int d, int t)
	{
		month = m;
		day = d;
		time = t;
	}

	//copy constructor
	Time(Time t)
	{
		month = t.month;
		day = t.day;
		time = t.time;
	}

	//writes the time to console. This method is not used currently in the program in favor of toString.
	public void print()
	{
		if((this.month < 0) || (this.month > 12) || (this.day < 0) || (this.day > 31) || (this.time < 0) || (this.time > 23))
		{
			System.out.print("invalid date");
			return;
		}

		System.out.print(this.month + '/' + this.day + ":  ");
		if(this.time < 12)
		{
			System.out.print(this.time + " am");
		}
		else
		{
			if(this.time == 12)
			{
				System.out.println(this.time + " pm");
			}
			System.out.print(this.time-12 + " pm");
		}

	}

	//override allows us to print times in System.out.print(time)
	@Override
	public String toString()
	{
		String s = new String();

		if((this.month < 0) || (this.month > 12) || (this.day < 0) || (this.day > 31) || (this.time < 0) || (this.time > 23))
		{
			s = "invalid date";
			return s;
		}

		s = this.month + "/" + this.day + ": ";

		if(this.time < 12)
		{
			s = s + this.time + " AM";
		}
		else
		{
			s = s + (this.time-12) + " PM";
		}

		return s;
	}

	//lets us compare times.
	@Override
	public int compareTo(Time c)
	{
		int d;
		d = this.month - c.month;
		if(d == 0)
		{
			d = this.day - c.day;
			if(d == 0)
			{
				d = this.time - c.time;
			}
		}
		return d;
	}

	//to support time incrementation.
	public Time addHours(int hours)
	{
		int m = this.month, d = this.day, t = this.time;
		t+=hours;
		while(t > 23)
		{
			t-=23;
			++d;
		}
		if(m == 2)
		{
			if(d > 28)
			{
				d -= 28;
				++m;
			}
		}
		else if(m == 4 || m == 6 || m == 9 || m == 11)
		{
			if(d > 30)
			{
				d-=30;
				++m;
			}
		}
		else
		{
			if(d > 31)
			{
				d-=31;
				++m;
			}
		}
		Time r = new Time(d, m, t);
		return r;
	}
}

