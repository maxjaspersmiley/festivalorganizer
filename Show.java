//Show.java
//Max Smiley
//Program # 5
//CS202

import java.io.Serializable;

//Since event had to be abstract, we needed to implement this class. It does nothing more than Event does.
public class Show extends Event implements Serializable
{
	protected Show(){
		super();
	}
	protected void read_console(){
		super.read_console();
	}
	protected void write_console(){
		super.write_console();
	}
}
