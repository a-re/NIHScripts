package fscToCsv;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parse {

	public static void main(String[] args) 
	{
		if (args.length != 4)
		{
			System.out.println("Invalid arguments");
			printHelp();
			return;
		}
		boolean inFound = false;
		boolean outFound = false;
		String inFile = "";
		String outFile = "";
		for (int i = 0; i < args.length; i += 2)
		{
			switch (args[i])
			{
				case "--in":
					inFound = true;
					inFile = args[i + 1];
					break;
				case "--out":
					outFound = true;
					outFile = args[i + 1];
					break;
			}
		}
		if (!inFound || !outFound)
		{
			System.out.println("Invalid arguments");
			printHelp();
			return;
		}
		try
		{
			File xmlFile = new File(inFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			
			NodeList nodes = doc.getElementsByTagName("coordinate");
			System.out.println("Parsing XML file...");
			ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
			for (int i = 0; i < nodes.getLength(); i++)
			{
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element ele = (Element) node;
					double x = Double.parseDouble(ele.getElementsByTagName("x").item(0).getTextContent());
					double y = Double.parseDouble(ele.getElementsByTagName("y").item(0).getTextContent());
					coords.add(new Coordinate(x, y));
				}
			}
			
			PrintWriter writer = new PrintWriter(outFile, "UTF-8");
			writer.write("X,Y\n");
			for (Coordinate c : coords)
			{
				writer.write(String.format("%f,%f\n", c.getX(), c.getY()));
			}
			writer.close();
			System.out.println("Done! Output written to " + outFile);
		}
		catch (Exception e)
		{
			System.out.println("Unspecified error. Send the error to restifoan@mail.nih.gov");
			e.printStackTrace();
		}
	}
	
	public static void printHelp()
	{
		String jarName = new File(Parse.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
		System.out.println("\nUsage:");
		System.out.println(jarName + " --in <fsc_xml_coordinate_file> --out <output_csv_file>");
	}
}
