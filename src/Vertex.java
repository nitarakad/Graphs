import java.util.ArrayList;


public class Vertex {

	private String nameIn;
	private ArrayList <String> neighbors;

	public Vertex(String name)
	{
		nameIn = name;
		neighbors = new ArrayList <String>();
	}

	public String getName()
	{
		return nameIn;
	}

	public void addToNeighbor(String vertexName)
	{
		neighbors.add(vertexName);
	}

	public ArrayList <String> getNeighbors()
	{
		return neighbors;
	}
	
	public String neighborsInStringForm()
	{
		String allNeighbors = "";
		for (String v : neighbors)
		{
			allNeighbors += v + " ";
		}
		
		return allNeighbors;
	}

	public int getNumOfNeighbors()
	{
		return neighbors.size();
	}
	
	public boolean isNeighbor(Vertex v)
	{
		if (neighbors.contains(v.getName()))
		{
		return true;
		}
		
		else
		{
			return false;
		}
	}
	
	public boolean isNeighbor(String s)
	{
		return neighbors.contains(s);
	}
	
	public void removeFromNeighbors(String s)
	{
		neighbors.remove(s);
	}

	public double getWeightOfNeighbor(Vertex v, ArrayList <EdgePrototype> eList,
			boolean isDirected)
	{
		if (neighbors.contains(v))
		{
			if (isDirected)
			{
				for (EdgePrototype edge : eList)
				{
					if (edge.getFirst().equals(nameIn) && edge.getSecond().equals(v.getName()))
					{
						return edge.getWeight();
					}
				}
			}

			else
			{
				for (EdgePrototype edge : eList)
				{
					if ((edge.getFirst().equals(nameIn) && edge.getSecond().equals(v.getName())) ||
							(edge.getFirst().equals(v.getName()) && edge.getSecond().equals(nameIn)))
					{
						return edge.getWeight();
					}
				}
			}
		}
		
		return 0.0; //as in they are not connected at all
	}

	public boolean equals(Object a)
	{
		Vertex v = (Vertex) a;
		if (nameIn.equals(v.getName()))
		{
			return true;
		}

		return false;
	}
	
	public String toString()
	{
		return nameIn;
	}


}
