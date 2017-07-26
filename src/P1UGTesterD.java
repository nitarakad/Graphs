
import java.util.ArrayList;

public class P1UGTesterD 
{
	public static void main (String [] args)
	{
		ArrayList<String> path;
		double distance;
		boolean connected;
							  
		System.out.println("\n=== PROJECT 1 : UNDIRECTED GRAPH TESTER for BLOCK A ===");
		System.out.println("Loading calCities...");
		Graph graph = new Graph("src/calCities.txt", false);

		// testing forward path
		System.out.println("Testing shortest path SF->LV...");
		path = graph.getShortestPath("SanFrancisco","LasVegas");
		if ( path != null )
		{
			if (path.size() != 9)
			{
				System.out.println("   FAILED! path steps = " + path.size() + ", not 9.");
			}
			if (!path.get(6).equals("Barstow") || !path.get(5).equals("Bakersfield") || !path.get(2).equals("Merced"))
			{
				System.out.println("   FAILED! wrong path.");
				System.out.println("      Expected: SF -> Stockton -> Merced -> Chowchilla -> Fresno -> Bakersfield -> Barstow -> Baker -> LV");
				System.out.print("      Got: ");

				for (String s : path)
				{
					System.out.print(s + " ");
				}
				System.out.println();
			}
			distance = graph.getShortestPathLength("SanFrancisco","LasVegas");
			if (distance != 582.0)
			{
				System.out.println("   FAILED! path length = " + distance + ", not 582.");
			}	
		} else {
			System.out.println("   FAILED! No path found.");
		}
		// testing reverse path	
		System.out.println("Testing shortest path LV->SF...");
		path = graph.getShortestPath("LasVegas","SanFrancisco");
		if ( path != null )
		{
			if (path.size() != 9)
			{
				System.out.println("   FAILED! path steps = " + path.size() + ", not 9.");
			}
			if (!path.get(2).equals("Barstow") || !path.get(3).equals("Bakersfield") || !path.get(6).equals("Merced"))
			{
				System.out.println("   FAILED! wrong path.");
				System.out.println("      Expected: LV -> Baker -> Barstow -> Bakersfield -> Fresno -> Chowchilla -> Merced -> Stockton -> SF");
				System.out.print("      Got: ");
				for (String s : path)
				{
					System.out.print(s + " -> ");
				}
				System.out.println();
			}
			distance = graph.getShortestPathLength("LasVegas","SanFrancisco");
			if (distance != 582.0)
			{
				System.out.println("   FAILED! path length = " + distance + ", not 582.");
			}		
		} else {
			System.out.println("   FAILED! No path found.");
		}

		// testing alternate path that uses none of the previous vertices
		System.out.println("Testing shortest paths from Alturas->ElCentro...");
		path = graph.getShortestPath("Alturas","ElCentro");
		if ( path != null ) 
		{
			if ( path.size() != 9 || Math.abs(graph.getShortestPathLength("Alturas","ElCentro") - 868.0) > 1e-6)
			{
				System.out.println("   FAILED: Wrong path.");
				System.out.println("      Expected 9 steps, got: " + path.size());
				System.out.println("      Expected total distance = 868.0, got: "+graph.getShortestPathLength("Alturas","ElCentro"));
			}
		} else {
			System.out.println("   FAILED: Found no path.");
		}	
		path = graph.getShortestPath("ElCentro","Alturas");
		if ( path != null ) 
		{
			if ( path.size() != 9 || Math.abs(graph.getShortestPathLength("ElCentro","Alturas") - 868.0) > 1e-6)
			{
				System.out.println("   FAILED: Found wrong reverse path (ElCentro to Alturas).");
				System.out.println("      Expected 9 steps, got: " + path.size());
				System.out.println("      Expected total distance = 868.0, got: "+graph.getShortestPathLength("ElCentro","Alturas"));
			}
		} else {
			System.out.println("   FAILED: Found no reverse path (ElCentro to Alturas).");
		}		

		// test getNumVertices and getNumEdges
		System.out.println("Testing getNumVertices and getNumEdges...");
		int numVertices = graph.getNumVertices();
		int numEdges = graph.getNumEdges();
		System.out.println("Vertices = "+numVertices+" Edges = "+numEdges);
		final int NVERT = 45;
		final int NEDGE = 63;
		if ( (numVertices != NVERT && numVertices != (NVERT+2)) || numEdges != NEDGE)
		{
			System.out.println("   FAILED: Wrong number of edges and/or vertices.");
			System.out.println("      Expected " + NVERT + " vertices, got: " + numVertices);
			System.out.println("      Expected " + NEDGE + " edges, got: "+numEdges);
		}

		// test removing a vertex
		System.out.println("Testing removal of the vertex named Bishop...");
		graph.remove("Bishop");		
		int numVertices1 = graph.getNumVertices();
		numEdges = graph.getNumEdges();
//		System.out.println("Vertices = "+nVr+" Edges = "+nEr);
		if (numVertices1 != (numVertices-1) || numEdges != (NEDGE-2))
		{
			System.out.println("   FAILED: Removal of a vertex modified wrong number of items.");
			System.out.println("      Expected "+(numVertices-1)+" vertices, got: " + numVertices1);
			System.out.println("      Expected "+(NEDGE-2)+" edges, got: "+numEdges);
		}
		path = graph.getShortestPath("Alturas","ElCentro");
		if ( path != null ) 
		{
			if ( path.size() != 13 || Math.abs(graph.getShortestPathLength("Alturas","ElCentro") - 931.0) > 1e-6)
			{
				System.out.println("   FAILED: Wrong new path.");
				System.out.println("      Expected 13 steps, got: " + path.size());
				System.out.println("      Expected total distance = 931.0, got: "+graph.getShortestPathLength("Alturas","ElCentro"));
			}
		} else {
			System.out.println("   FAILED: Found no path.");
		}			
		// test removing an edge
		System.out.println("Testing removal of the edge between SanDiego and ElCentro...");
		graph.remove("SanDiego","ElCentro");
		int numVertices2 = graph.getNumVertices();
		int numEdges2 = graph.getNumEdges();
//		System.out.println("Vertices = "+nVrr+" Edges = "+nErr);
		if (numVertices1 != numVertices2 || (numEdges-1) != numEdges2 )
		{
			System.out.println("   FAILED: Removal of an edge modified wrong number of items.");
			System.out.println("      Expected "+numVertices1+" vertices, got: " + numVertices2);
			System.out.println("      Expected "+(numEdges-1)+" edges, got: "+numEdges2);
		}
		connected = graph.areConnected("Alturas","ElCentro");
		if (connected)
		{
			System.out.println("   FAILED: Alturas and ElCentro are still connected, despite removing only edge to ElCentro.");
		}


		// adding an edge to the graph
		System.out.println("Testing addition of an edge from ElCentro to LosAngeles...");
		graph.insert("ElCentro","LosAngeles",163.0);
		int numVertices3 = graph.getNumVertices();
		int numEdges3 = graph.getNumEdges();
//		System.out.println("Vertices = "+nVrr+" Edges = "+nErr);
		if (numVertices3 != numVertices2 || numEdges3 != (numEdges2+1))
		{
			System.out.println("   FAILED: Addition of an edge modified wrong number of items.");
			System.out.println("      Expected "+(numVertices2)+" vertices, got: " + numVertices3);
			System.out.println("      Expected "+(numEdges2+1)+" edges, got: "+numEdges3);
		}
		connected = graph.areConnected("Alturas","ElCentro");
		if (!connected)
		{
			System.out.println("   FAILED: Alturas and ElCentro are NOT connected, despite adding an edge to ElCentro.");
		}

		// adding a vertex
		System.out.println("Testing addition of a vertex named Gilroy...");
		graph.insert("Gilroy");
		int numVertices4 = graph.getNumVertices();
		int numEdges4 = graph.getNumEdges();
//		System.out.println("Vertices = "+nVrr+" Edges = "+nErr);
		if (numVertices4 != (numVertices3+1) || numEdges4 != numEdges3)
		{
			System.out.println("   FAILED: Addition of a vertex modified wrong number of items.");
			System.out.println("      Expected "+(numVertices3+1)+" vertices, got: " + numVertices4);
			System.out.println("      Expected "+(numEdges3)+" edges, got: "+numEdges4);
		}
		System.out.println("=======================================================");
	}
	
}