public class EdgePrototype {
	private String first;
	private String second;
	private double weight;

	public EdgePrototype (String iFirst, String iSecond, Double iWeight)
	{
		first = iFirst;
		second = iSecond;
		weight = iWeight;
	}

	public String getFirst() //first node
	{
		return first;
	}

	public String getSecond() //second node
	{
		return second;
	}

	public double getWeight() //distance btwn the two nodes
	{
		return weight;
	}

	public String toString() 
	{
		return first + " -> " + second + " (" + weight + ")";
	}
	
	public String onlyVertexString()
	{
		return first + " -> " + second;
	}
}