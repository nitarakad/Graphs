
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class GraphDataFileReader {

	public static ArrayList<EdgePrototype> readDataFile(String fileName) {

		File dataFile;
		Scanner input = null;
		ArrayList<EdgePrototype> edgeList = null;

		try 
		{
			dataFile = new File(fileName);
			if ( dataFile.canRead() ) 
			{
				input = new Scanner(dataFile);
			}
			else
			{
				input = null;
			}
		} 
		catch ( NullPointerException npe ) 
		{
			System.err.println("The input fileName must not be null.");
		} 
		catch ( SecurityException se ) 
		{
			System.err.println("The security manager denies access to this file.");
		} 
		catch ( Exception e ) 
		{
			System.err.println("Unexpected Error: ");
			e.printStackTrace();
		}

		if (input != null)
		{
			edgeList = new ArrayList<EdgePrototype>();
			while ( input.hasNextLine() )
			{
				Scanner inputLine = new Scanner(input.nextLine());
				String one = inputLine.next();
				String two = "";
				Double num = 0.0;
				if ( inputLine.hasNext() ) 
				{
					two = inputLine.next();
				}
				
				if ( inputLine.hasNext() ) 
				{
					try 
					{
						num = Double.parseDouble(inputLine.next());
					}
					catch ( NumberFormatException nfe)
					{
						num = 0.0;
					}

				}
				EdgePrototype edge = new EdgePrototype(one,two,num);    
				edgeList.add(edge);
			}
		} 
		return edgeList;

	}
}