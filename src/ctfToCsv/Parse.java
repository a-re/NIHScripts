package ctfToCsv;
import static java.lang.System.err;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
public class Parse {

	public static void main(String[] args) 
	{
		if (args.length != 4)
		{
			System.out.println("Error parsing arguments.");
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
		if (!outFound || !inFound)
		{
			System.out.println("Invalid arguments.");
			printHelp();
			return;
		}
		Scanner fileScan = null;
		try
		{
			fileScan = new Scanner(new File(inFile));
		}
		catch (Exception ex)
		{
			err.println("Error opening file.");
		}
		
		ArrayList<Micrograph> mrcList = new ArrayList<Micrograph>();
		while (fileScan.hasNextLine())
		{
			String line = fileScan.nextLine();
			String[] preprocess = line.split(" ");
			if (preprocess.length > 4) // arbitrary value to know if we are on a micrograph line and not a data line
			{
				ArrayList<String> process = new ArrayList<String>();
				for (String s : preprocess)
					if (!s.replace(" ", "").equals(""))
						process.add(s);
				String name = process.get(1);
				try
				{
					mrcList.add(new Micrograph(name.substring(0, name.lastIndexOf(":")), Double.parseDouble(process.get(2)), Double.parseDouble(process.get(3)), Double.parseDouble(process.get(4)), Double.parseDouble(process.get(5))));
				}
				catch (Exception ex)
				{
					System.out.println("Error parsing micrograph!");
					System.out.println("Line: " + line);
				}
			}
		}
		try 
		{
			PrintWriter writer = new PrintWriter(outFile, "UTF-8");
			writer.write("Name,DefocusU,DefocusV,Angle,CCC\n");
			for (Micrograph m : mrcList)
			{
				writer.write(String.format("%s,%f,%f,%f,%f\n", m.getName(), m.getDefocusU(), m.getDefocusV(), m.getAngle(), m.getCCC()));
			}
			writer.close();
		} 
		catch (UnsupportedEncodingException | FileNotFoundException e) 
		{
			System.out.println("Error writing output file!");
			e.printStackTrace();
		}
		System.out.println("Done! Output written to " + outFile);
	}
	
	public static void printHelp()
	{
		String jarName = new File(Parse.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
		System.out.println("Usage:");
		System.out.println(jarName + " --in <micrograph_to_parse> --out <output_csv_file>");
	}
}