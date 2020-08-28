package airlines;

import java.util.*;


public class Airport implements Comparable<Airport>
{
	private String					name;
	private int						x;
	private int						y;
	private Set<Airport>			connections;	// all airports with a direct route from this airport
	
	
	public Airport(String name, int x, int y)
	{
		this.name = name;
		this.x = x;
		this.y = y;
		connections = new TreeSet<>();				//orders the connections and removes duplicates
	}
	
	
	public String getName()
	{
		return name;
	}
	
	
	public int getX()
	{
		return x;
	}
	
	
	public int getY()
	{
		return y;
	}
	
	
	//turns the treeSet of connections into an ArrayList 
	//allows for better indexing
	public List<Airport> getConnections()
	{
		return new ArrayList<>(connections);
	}
	
	
	//adds that airport to the list of connections
	//one-way route
	public void connectTo(Airport that)
	{
		connections.add(that);
	}
	
	
	//disconnect Airport that 
	public void disconnectFrom(Airport that)
	{
		for(Airport port : this.getConnections()) {
			if(port.equals(that)) {
				connections.remove(that);
			}
		}
	}
	
	
	//satisfies equals and compareTo contract
	public boolean equals(Object x)
	{
		Airport that = (Airport) x;
		return this.compareTo(that) == 0;
	}
	
	
	//uses String's built in compareTo method
	public int compareTo(Airport that)
	{
		return this.getName().compareTo(that.getName());
	}
	
	
	//returns true if this Airport is connected to that Airport
	public boolean isConnectedTo(Airport that)
	{
		for(Airport port : this.getConnections()) {
			if(port.equals(that)) {
				return true;
			}
		}
		return false;
	}
	
	
	public String toString()
	{
		return "Airport " + name + " @(" + x + "," + y + ")";
	}
}
