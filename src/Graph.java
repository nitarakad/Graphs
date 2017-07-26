import java.util.ArrayList;
import java.util.Stack;

public class Graph {

	private ArrayList <EdgePrototype> edgeList;
	private ArrayList <Vertex> vertexList;
	//private ArrayList <Double> weightList; -->  i don't use this
	private boolean isDirected;

	public Graph(String fileName, boolean directed)
	{
		edgeList = GraphDataFileReader.readDataFile(fileName);
		vertexList = new ArrayList<Vertex>();
		//weightList = new ArrayList<Double>();
		isDirected = directed;

		if (edgeList != null)
		{
			for (EdgePrototype edge : edgeList)
			{
				if (!vertexList.contains(new Vertex(edge.getFirst())))
				{
					vertexList.add(new Vertex(edge.getFirst()));
				}

				if (!vertexList.contains(new Vertex(edge.getSecond())))
				{
					vertexList.add(new Vertex(edge.getSecond()));
				}

				//weightList.add(edge.getWeight());
			}
		}

		else
		{
			System.out.println("no edgeList to be used");
		}

		if (vertexList != null)
		{
			if (isDirected)
			{
				for (Vertex v : vertexList)
				{
					for (EdgePrototype edge : edgeList)
					{
						if (v.getName().equals(edge.getFirst()))
						{
							v.addToNeighbor(edge.getSecond());
						}
					}
				}
			}

			else
			{
				for (Vertex v : vertexList)
				{
					for (EdgePrototype edge : edgeList)
					{
						if (v.getName().equals(edge.getFirst()))
						{
							v.addToNeighbor(edge.getSecond());
						}

						if (v.getName().equals(edge.getSecond()))
						{
							v.addToNeighbor(edge.getFirst());
						}
					}
				}
			}
		}

		else
		{
			System.out.println("no vertexlist to be used");
		}
	}

	public int getNumEdges()
	{
		return edgeList.size();
	}

	public int getNumVertices()
	{
		return vertexList.size();
	}

	public ArrayList <Vertex> getVertices()
	{
		return vertexList;
	}

	public boolean directlyConnected(String firstVertexName, String secondVertexName)
	{
		Vertex firstVertex = null;
		Vertex secondVertex = null;

		for (Vertex v : vertexList)
		{
			if (v.getName().equals(firstVertexName))
			{
				firstVertex = v;
			}

			if (v.getName().equals(secondVertexName))
			{
				secondVertex = v;
			}
		}

		if (isDirected)
		{
			return firstVertex.isNeighbor(secondVertex);
		}

		else
		{
			return firstVertex.isNeighbor(secondVertex) || secondVertex.isNeighbor(firstVertex);
		}
	}

	//BFS
	public boolean areConnected(String firstVertexName, String secondVertexName)
	{
		Vertex firstVertex = null;
		Vertex secondVertex = null;

		for (Vertex v : vertexList)
		{
			if (v.getName().equals(firstVertexName))
			{
				firstVertex = v;
			}

			if (v.getName().equals(secondVertexName))
			{
				secondVertex = v;
			}
		}

		Stack <Vertex> queue = new Stack <Vertex>();
		ArrayList <Vertex> visited = new ArrayList <Vertex>();
		queue.push(firstVertex);
		Vertex temp = new Vertex("");
		while (!temp.equals(secondVertex) && queue.size() != 0)
		{
			temp = queue.pop();
			if (!temp.equals(secondVertex) && !visited.contains(temp))
			{
				visited.add(temp);
				for (String s : temp.getNeighbors())
				{
					for (Vertex v : vertexList)
					{
						if (v.getName().equals(s))
						{
							queue.push(v);
						}
					}
				}
			}
		}

		if (temp.equals(secondVertex))
		{
			return true;
		}

		else
		{
			return false;
		}

	}

	public void remove(String vertexName)
	{	
		Vertex removed = new Vertex(vertexName);
		if(vertexList.contains(removed))
		{
			vertexList.remove(removed);
			int pos = 0;
			while(pos < vertexList.size())
			{
				Vertex v = vertexList.get(pos);
				if(v.isNeighbor(vertexName))
				{
					v.removeFromNeighbors(vertexName);
				}
				//vertexList.set(pos, v);
				pos++;
			}
		}
		
		for (int i = 0; i < edgeList.size(); i++)
		{
			EdgePrototype e = edgeList.get(i);
			if (isDirected)
			{
				if (e.getFirst().equals(vertexName))
				{
					edgeList.remove(e);
					i--;
				}
			}
			
			else
			{
				if (e.getFirst().equals(vertexName) || e.getSecond().equals(vertexName))
				{
					edgeList.remove(e);
					i--;
				}
			}
		}
	}

	public void remove(String firstVertexName, String secondVertexName)
	{
		Vertex firstVertex = null;
		Vertex secondVertex = null;
		for (Vertex v : vertexList)
		{
			if (v.getName().equals(firstVertexName))
			{
				firstVertex = v;
			}
			
			if (v.getName().equals(secondVertexName))
			{
				secondVertex = v;
			}
		}
		
		for (int i = 0; i < edgeList.size(); i++)
		{
			EdgePrototype e = edgeList.get(i);
			if (isDirected)
			{
				if (e.getFirst().equals(firstVertexName) && e.getSecond().equals(secondVertexName))
				{
					firstVertex.removeFromNeighbors(secondVertexName);
					edgeList.remove(e);
					i--;
				}
			}
			
			else
			{
				if ((e.getFirst().equals(firstVertexName) && e.getSecond().equals(secondVertexName)) || 
						(e.getFirst().equals(secondVertexName) && e.getSecond().equals(firstVertexName)))
				{
					firstVertex.removeFromNeighbors(secondVertexName);
					secondVertex.removeFromNeighbors(firstVertexName);
					edgeList.remove(e);
					i--;
				}
			}
		}
	}

	public void insert(String vertexName)
	{
		vertexList.add(new Vertex(vertexName));
	}

	public void insert(String firstVertexName, String secondVertexName, Double theWeight)
	{
		edgeList.add(new EdgePrototype(firstVertexName, secondVertexName, theWeight));
		if (!vertexList.contains(new Vertex(firstVertexName)))
		{
			vertexList.add(new Vertex(firstVertexName));
		}

		if (!vertexList.contains(new Vertex(secondVertexName)))
		{
			vertexList.add(new Vertex(secondVertexName));
		}

		//add the neighbors in
		if (isDirected)
		{
			for (Vertex v : vertexList)
			{
				if (v.getName().equals(firstVertexName))
				{
					v.addToNeighbor(secondVertexName);
				}
			}
		}

		else
		{
			for (Vertex v : vertexList)
			{
				if (v.getName().equals(firstVertexName))
				{
					v.addToNeighbor(secondVertexName);
				}

				if (v.getName().equals(secondVertexName))
				{
					v.addToNeighbor(firstVertexName);
				}
			}
		}
	}

	public ArrayList <String> getShortestPath(String firstVertexName, String secondVertexName)
	{
		System.out.println("redesign report didn't include this");
		return null;
	}

	public Double getShortestPathLength(String firstVertexName, String secondVertexName)
	{
		System.out.println("redesign report didn't include this");
		return null;
	}


}
