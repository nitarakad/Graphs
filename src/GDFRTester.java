
import java.util.ArrayList;

public class GDFRTester {
	public static void main (String [] args) 
	{
		String filename;

		if ( args != null && args.length > 0 ) 
		{
			filename = args[0];
		}
		else
		{
			filename = "src/calCities.txt";
		}

		ArrayList<EdgePrototype> edgeList = new ArrayList <EdgePrototype>();
		edgeList = GraphDataFileReader.readDataFile(filename);

		//		if (edgeList != null && edgeList.size() > 0 ) 
		//		{
		//			for ( EdgePrototype edge : edgeList ) {
		//				System.out.println(edge);
		//			}
		//		}
		//		else
		//		{
		//			System.out.println("No edge list, possibly bad file name or empty file.");
		//		}

		Graph g = new Graph(filename, false); //calCities is undirected
		System.out.println(g.getNumEdges());
		//System.out.println(g.directlyConnected("Eureka", "Redding"));
		System.out.println(g.areConnected("Redding", "Eureka"));
		g.remove("Bishop");

	}
}