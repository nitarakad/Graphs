import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class OriginalGraph {
	
	private static int numberOfVertices;
	private static int numberOfEdges;
	private static ArrayList <EdgePrototype> edgeList;
	private static ArrayList <Vertex> vertexListFirst;
	private static ArrayList <Vertex> vertexListSecond;
	private static ArrayList <Vertex> allVertices;
	private static ArrayList <Double> weightList;
	private static Scanner scan = new Scanner (System.in);

	public OriginalGraph(String fileName, boolean directed)
	{
		edgeList = GraphDataFileReader.readDataFile(fileName);
		System.out.println("got here");
		vertexListFirst = new ArrayList <Vertex>();
		vertexListSecond = new ArrayList <Vertex>();
		allVertices = new ArrayList <Vertex>();
		weightList = new ArrayList <Double>();
		
		for (EdgePrototype edge : edgeList)
		{
			Vertex v1 = new Vertex(edge.getFirst());
			vertexListFirst.add(v1);

			Vertex v2 = new Vertex(edge.getSecond());
			vertexListSecond.add(v2);

			allVertices.add(v1);
			allVertices.add(v2);

			weightList.add(edge.getWeight());
		}

	}

	public int getNumEdges()
	{
		return edgeList.size();
	}

	public int getNumVertices()
	{
		return vertexListFirst.size() + vertexListSecond.size();
	}

	/**
	 * WORKS
	 * @param vertexA
	 * @param vertexB
	 * @return
	 */
	public boolean isDirectlyConnected(Vertex vertexA, Vertex vertexB)
	{
		for (int i = 0; i < vertexListFirst.size(); i++)
		{
			if (vertexListFirst.get(i).equals(vertexA))
			{
				if (i == vertexListSecond.indexOf(vertexB))
				{
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * WORKS
	 * @param vertexA
	 * @param VertexB
	 * @return - number of vertices directly connected to vertexA
	 */
	public int numberOfAdjacentVertices(Vertex vertexA)
	{
		int count = 0;
		for (Vertex x : vertexListFirst)
		{
			if (x.equals(vertexA))
			{
				count++;
			}
		}

		return count;
	}

	public void insert(String firstVertex, String secondVertex, double theWeight)
	{
		EdgePrototype edge = new EdgePrototype(firstVertex, secondVertex, theWeight);
		if (!edgeList.contains(edge))
		{
			edgeList.add(edge);
		}

		else
		{
			System.out.println("This edge is already there");
			return;
		}
		if (!vertexListFirst.contains(new Vertex(edge.getFirst())) &&
				!vertexListSecond.contains(new Vertex(edge.getSecond())))
		{
			vertexListFirst.add(new Vertex(edge.getFirst()));
			vertexListSecond.add(new Vertex(edge.getSecond()));
		}
		else
		{
			System.out.println("These vertices already exist in the graph");
		}

	}

	public void insert(String vertexName)
	{
		Vertex v = new Vertex(vertexName);
		vertexListFirst.add(v);
		vertexListSecond.add(null);
		allVertices.add(v);
		allVertices.add(null);
	}

	public void remove(String firstVertex, String secondVertex)
	{
		double theWeight = 0.0;
		for (EdgePrototype edge : edgeList)
		{
			if (edge.getFirst().equals(firstVertex) && edge.getSecond().equals(secondVertex))
			{
				theWeight = edge.getWeight();
			}
		}
		EdgePrototype edge = new EdgePrototype(firstVertex, secondVertex, theWeight);
		if (edgeList.contains(edge))
		{
			int indexOfEdge = edgeList.indexOf(edge);
			edgeList.set(indexOfEdge, null); //set to null
			System.out.println(edge + " has been removed");
		}
		else
		{
			System.out.println("This edge is not in the graph");
		}
	}

	/**
	 * need to remove all vertices of the number in the first, second,
	 * and allVertices arrayLists
	 */
	public void remove(String vertexName)
	{
		Vertex v = new Vertex(vertexName);
		if (allVertices.contains(v))
		{
			int indexOfVertex = allVertices.indexOf(v);
			allVertices.set(indexOfVertex, null);
			System.out.println("Removed from all vertices");
		}
		if (vertexListFirst.contains(v))
		{
			int indexOfVertex = vertexListFirst.indexOf(v);
			vertexListFirst.set(indexOfVertex, null); //set to null
			System.out.println(v + " has been removed from first list");
		}

		else if (vertexListSecond.contains(v))
		{
			int indexOfVertex = vertexListSecond.indexOf(v);
			vertexListSecond.set(indexOfVertex, null); //set to null
			System.out.println(v + " has been removed from second list");
		}

		else
		{
			System.out.println("the vertex is not in the graph");
		}
	}

	/** WORKS
	 * Checks if a vertex is in the graph with just
	 * the name of the vertex
	 * @param vertexName - name of the vertex
	 * @return - true if in graph, false if not
	 */
	public boolean contains(String vertexName)
	{

		if (allVertices.contains(new Vertex(vertexName)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * WORKS
	 * @param v
	 * @return
	 */
	public boolean contains(Vertex v)
	{

		if (allVertices.contains(v))
		{
			return true;
		}

		else
		{
			return false;
		}
	}

	public ArrayList <Vertex> getAdjacentVertices(Vertex a)
	{
		ArrayList <Vertex> adjacentVerticesList = new ArrayList <Vertex>();

		for (EdgePrototype edge : edgeList)
		{
			if (edge.getFirst().equals(a.getName()))
			{
				adjacentVerticesList.add(new Vertex(edge.getSecond()));
			}
		}

		return adjacentVerticesList;
	}

	/**
	 * WORKS
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean areConnected(Vertex a, Vertex b)
	{
		ArrayList <Vertex> adjacentVertices = new ArrayList <Vertex>();
		if (numberOfAdjacentVertices(a) == 0)
		{
			return false;
		}

		else if (isDirectlyConnected(a, b))
		{
			return true;
		}
		else
		{
			adjacentVertices = getAdjacentVertices(a);
			for (Vertex v : adjacentVertices)
			{
				return areConnected(v, b);
			}	
		}

		return false;
	}

	//STRING
	public boolean areConnected (String firstVertexName, 
			String secondVertexName)
	{
		ArrayList <Vertex> adjacentVertices = new ArrayList <Vertex>();
		if (numberOfAdjacentVertices(new Vertex(firstVertexName)) == 0)
		{
			return false;
		}

		else if (isDirectlyConnected(new Vertex(firstVertexName), 
				new Vertex(secondVertexName)))
		{
			return true;
		}
		else
		{
			adjacentVertices = getAdjacentVertices(new Vertex(firstVertexName));
			for (Vertex v : adjacentVertices)
			{
				return areConnected(v, new Vertex(secondVertexName));
			}	
		}

		return false;
	}

	public ArrayList<String> getShortestPath(String firstVertexName, String secondVertexName)
	{	
		ArrayList<Double> distance = new ArrayList <Double>(); //the sum of the distance value of the previous edge plus the weight of this edge
		ArrayList<Integer> previous = new ArrayList <Integer>(); //get the index of the edge YOU CAME FROM
		ArrayList<Boolean> visited = new ArrayList <Boolean>(); //true for the edge when you travel it
		ArrayList <String> path = new ArrayList <String>(); //THE ARRAY LIST BEING RETURNED
		for(int i = 0; i < edgeList.size(); i++)
		{
			distance.add(Double.POSITIVE_INFINITY);
			previous.add(-1);
			visited.add(false);
		}

		String currentVertexName = firstVertexName;
		ArrayList <Vertex> adjacentVertices = getAdjacentVertices(new Vertex(currentVertexName));

		for (EdgePrototype edge : edgeList)
		{
			for (Vertex v : adjacentVertices)
			{
				if (edge.getFirst().equals(currentVertexName) && edge.getSecond().equals(v.getName()))
				{
					distance.set(edgeList.indexOf(edge), edge.getWeight());
					//previous.set(edgeList.indexOf(edge), edgeList.indexOf(edge));
				}
			}
		}

		Double shortestDistance = distance.get(0);
		for (Double distanceBtwn : distance)
		{
			//System.out.println("Distance BTWN: " + distanceBtwn);
			if (distanceBtwn < shortestDistance)
			{
				shortestDistance = distanceBtwn;
			}
		}

		//System.out.println("FIRST SHORT: " + shortestDistance);

		EdgePrototype EIP = edgeList.get(distance.indexOf(shortestDistance));
		//System.out.println("EIP: " + EIP);
		//distance.set(distance.indexOf(shortestDistance), Double.POSITIVE_INFINITY);
		while (EIP != null && !EIP.getSecond().equals(secondVertexName))
		{
			visited.set(edgeList.indexOf(EIP), true); //where do I check if the edge has been visited?
			ArrayList <Vertex> adjacentVertices2 = getAdjacentVertices(new Vertex(EIP.getSecond()));
			ArrayList <EdgePrototype> edgeNeighbors = new ArrayList <EdgePrototype>();
			for(Vertex v : adjacentVertices2)
			{
				if (vertexListFirst.indexOf(new Vertex(EIP.getSecond())) == vertexListSecond.indexOf(v))
				{
					if (!visited.get(vertexListSecond.indexOf(v)))
					{
						edgeNeighbors.add(edgeList.get(vertexListSecond.indexOf(v)));
						//System.out.println("EN: " + edgeList.get(vertexListSecond.indexOf(v)));
					}
				}
			}

			double tempDistance = distance.get(edgeList.indexOf(EIP));
			//System.out.println("TEMP: " + tempDistance);

			distance.set(edgeList.indexOf(EIP), Double.POSITIVE_INFINITY);


			for(Vertex v : adjacentVertices2)
			{
				//System.out.println("BEGIN OF FOR LOOP V: " + v.getName());
				//System.out.println("EIP SECOND: " + EIP.getSecond());
				//System.out.println("FIRST INDEX: " + vertexListFirst.indexOf(new Vertex(EIP.getSecond())));
				//System.out.println("SECOND INDEX: " + vertexListSecond.indexOf(v));
				//				if (vertexListFirst.indexOf(new Vertex(EIP.getSecond())) == vertexListSecond.indexOf(v))
				//				{
				//System.out.println("EIP IN IF: " + EIP.getSecond());
				//System.out.println(v.getName());
				//System.out.println("temp: " + tempDistance);
				//System.out.println("WEIGHT: " + weightList.get(vertexListFirst.indexOf(new Vertex(EIP.getSecond()))));
				//System.out.println("TOTAL: " + (weightList.get(vertexListFirst.indexOf(new Vertex(EIP.getSecond())))
				//		+ tempDistance));
				distance.set(vertexListFirst.indexOf(new Vertex(EIP.getSecond())), tempDistance + 
						weightList.get(vertexListFirst.indexOf(new Vertex(EIP.getSecond()))));
				previous.set(vertexListFirst.indexOf(new Vertex(EIP.getSecond())), edgeList.indexOf(EIP));
				//System.out.println("OUT");
				//				}
			}

			Double shortDistance = distance.get(0);
			for (Double distanceBtwn : distance)
			{
				//System.out.println("DISTANCE BTWN 2: " + distanceBtwn);
				if (distanceBtwn < shortDistance)
				{
					shortDistance = distanceBtwn;
				}
			}

			//System.out.println("Short: " + shortDistance);

			//previous.set(distance.indexOf(shortDistance), edgeList.indexOf(EIP));
			//System.out.println("INDEX: " + edgeList.indexOf(EIP));

			EIP = edgeList.get(distance.indexOf(shortDistance));
			//System.out.println("HERE: " + EIP);
			//distance.set(distance.indexOf(shortDistance), Double.POSITIVE_INFINITY);
		}

		int index = previous.get(edgeList.indexOf(EIP));
		int tempIndex = index;
		//System.out.println("INDEX: " + index);

		if (index == -1) //means that it was directly connected
		{
			path.add(EIP.onlyVertexString());
		}
		while (index > -1)
		{
			//			System.out.println("PATH INDEX: " + previous.indexOf(index));
			//			System.out.println(edgeList.get(previous.indexOf(index)).onlyVertexString());
			//			System.out.println("PREVIOUS: " + previous.indexOf(index));
			path.add(edgeList.get(previous.indexOf(index)).onlyVertexString());
			index = previous.get(index);
			if (index == -1)
			{
				path.add(edgeList.get(tempIndex).onlyVertexString());
			}
		}

		Collections.reverse(path);
		return path;
	}
	
	public double getShortestPathLength(String firstVertexName, String secondVertexName)
	{
		ArrayList<Double> distance = new ArrayList <Double>(); //the sum of the distance value of the previous edge plus the weight of this edge
		ArrayList<Integer> previous = new ArrayList <Integer>(); //get the index of the edge YOU CAME FROM
		ArrayList<Boolean> visited = new ArrayList <Boolean>(); //true for the edge when you travel it
		//ArrayList <String> path = new ArrayList <String>(); //THE ARRAY LIST BEING RETURNED
		ArrayList <EdgePrototype> pathInEdges = new ArrayList <EdgePrototype>(); //edges
		for(int i = 0; i < edgeList.size(); i++)
		{
			distance.add(Double.POSITIVE_INFINITY);
			previous.add(-1);
			visited.add(false);
		}

		String currentVertexName = firstVertexName;
		ArrayList <Vertex> adjacentVertices = getAdjacentVertices(new Vertex(currentVertexName));

		for (EdgePrototype edge : edgeList)
		{
			for (Vertex v : adjacentVertices)
			{
				if (edge.getFirst().equals(currentVertexName) && edge.getSecond().equals(v.getName()))
				{
					distance.set(edgeList.indexOf(edge), edge.getWeight());
					//previous.set(edgeList.indexOf(edge), edgeList.indexOf(edge));
				}
			}
		}

		Double shortestDistance = distance.get(0);
		for (Double distanceBtwn : distance)
		{
			//System.out.println("Distance BTWN: " + distanceBtwn);
			if (distanceBtwn < shortestDistance)
			{
				shortestDistance = distanceBtwn;
			}
		}

		//System.out.println("FIRST SHORT: " + shortestDistance);

		EdgePrototype EIP = edgeList.get(distance.indexOf(shortestDistance));
		//System.out.println("EIP: " + EIP);
		//distance.set(distance.indexOf(shortestDistance), Double.POSITIVE_INFINITY);
		while (EIP != null && !EIP.getSecond().equals(secondVertexName))
		{
			visited.set(edgeList.indexOf(EIP), true); //where do I check if the edge has been visited?
			ArrayList <Vertex> adjacentVertices2 = getAdjacentVertices(new Vertex(EIP.getSecond()));
			ArrayList <EdgePrototype> edgeNeighbors = new ArrayList <EdgePrototype>();
			for(Vertex v : adjacentVertices2)
			{
				if (vertexListFirst.indexOf(new Vertex(EIP.getSecond())) == vertexListSecond.indexOf(v))
				{
					if (!visited.get(vertexListSecond.indexOf(v)))
					{
						edgeNeighbors.add(edgeList.get(vertexListSecond.indexOf(v)));
						//System.out.println("EN: " + edgeList.get(vertexListSecond.indexOf(v)));
					}
				}
			}

			double tempDistance = distance.get(edgeList.indexOf(EIP));
			//System.out.println("TEMP: " + tempDistance);

			distance.set(edgeList.indexOf(EIP), Double.POSITIVE_INFINITY);


			for(Vertex v : adjacentVertices2)
			{
				//System.out.println("BEGIN OF FOR LOOP V: " + v.getName());
				//System.out.println("EIP SECOND: " + EIP.getSecond());
				//System.out.println("FIRST INDEX: " + vertexListFirst.indexOf(new Vertex(EIP.getSecond())));
				//System.out.println("SECOND INDEX: " + vertexListSecond.indexOf(v));
				//				if (vertexListFirst.indexOf(new Vertex(EIP.getSecond())) == vertexListSecond.indexOf(v))
				//				{
				//System.out.println("EIP IN IF: " + EIP.getSecond());
				//System.out.println(v.getName());
				//System.out.println("temp: " + tempDistance);
				//System.out.println("WEIGHT: " + weightList.get(vertexListFirst.indexOf(new Vertex(EIP.getSecond()))));
				//System.out.println("TOTAL: " + (weightList.get(vertexListFirst.indexOf(new Vertex(EIP.getSecond())))
				//		+ tempDistance));
				distance.set(vertexListFirst.indexOf(new Vertex(EIP.getSecond())), tempDistance + 
						weightList.get(vertexListFirst.indexOf(new Vertex(EIP.getSecond()))));
				previous.set(vertexListFirst.indexOf(new Vertex(EIP.getSecond())), edgeList.indexOf(EIP));
				//System.out.println("OUT");
				//				}
			}

			Double shortDistance = distance.get(0);
			for (Double distanceBtwn : distance)
			{
				//System.out.println("DISTANCE BTWN 2: " + distanceBtwn);
				if (distanceBtwn < shortDistance)
				{
					shortDistance = distanceBtwn;
				}
			}

			//System.out.println("Short: " + shortDistance);

			//previous.set(distance.indexOf(shortDistance), edgeList.indexOf(EIP));
			//System.out.println("INDEX: " + edgeList.indexOf(EIP));

			EIP = edgeList.get(distance.indexOf(shortDistance));
			//System.out.println("HERE: " + EIP);
			//distance.set(distance.indexOf(shortDistance), Double.POSITIVE_INFINITY);
		}

		int index = previous.get(edgeList.indexOf(EIP));
		int tempIndex = index;
		//System.out.println("INDEX: " + index);

		if (index == -1) //means that it was directly connected
		{
			//path.add(EIP.onlyVertexString());
			pathInEdges.add(EIP);
		}
		while (index > -1)
		{
			//			System.out.println("PATH INDEX: " + previous.indexOf(index));
			//			System.out.println(edgeList.get(previous.indexOf(index)).onlyVertexString());
			//			System.out.println("PREVIOUS: " + previous.indexOf(index));
			//path.add(edgeList.get(previous.indexOf(index)).onlyVertexString());
			pathInEdges.add(edgeList.get(previous.indexOf(index)));
			index = previous.get(index);
			if (index == -1)
			{
				//path.add(edgeList.get(tempIndex).onlyVertexString());
				pathInEdges.add(edgeList.get(tempIndex));
			}
		}

		Collections.reverse(pathInEdges);
		double distanceTotal = 0.0;
		for (int i = 0; i < pathInEdges.size(); i++)
		{
			distanceTotal += pathInEdges.get(i).getWeight();
		}
		
		return distanceTotal;
	}

	public String toString()
	{
		String edges = "";
		if (edgeList != null && edgeList.size() > 0)
		{
			for (EdgePrototype edge : edgeList)
			{
				edges += edge + "\n";
			}
		}		
		else
		{
			System.out.println("No edge list, possibly "
					+ "bad file name or empty file.");
		}
		String vertices = "";
		if (vertexListFirst.size() == vertexListSecond.size())
		{
			for (int i = 0; i < vertexListFirst.size(); i++)
			{
				vertices += vertexListFirst.get(i).getName() + " : " +
						vertexListSecond.get(i).getName() + "\n";
			}
		}

		return "\nEDGES:\n" + edges + "VERTICES:\n" + vertices;	

	}

}
