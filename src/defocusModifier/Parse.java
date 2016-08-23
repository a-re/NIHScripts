package defocusModifier;

import static java.lang.System.err;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Parse {

	public static void main(String[] args) throws FileNotFoundException 
	{
		if (args.length != 6)
		{
			System.out.println("Error parsing arguments.");
			printHelp();
			return;
		}
		boolean inFound = false;
		boolean outFound = false;
		boolean offsetFound = false;
		double offset = 0.0;
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
				case "--offset":
					offsetFound = true;
					offset = Double.parseDouble(args[i + 1]);
					break;
				default:
					System.out.println("Invalid arguments");
					printHelp();
			}
		}
		if (!outFound || !inFound || !offsetFound)
		{
			System.out.println("Invalid arguments.");
			printHelp();
			return;
		}
		System.out.println("Replacing values...");
		Scanner fileScan = null;
		try
		{
			fileScan = new Scanner(new File(inFile));
		}
		catch (Exception ex)
		{
			err.println("Error opening file.");
		}
		
		ArrayList<String> writeLines = new ArrayList<String>();
		while (fileScan.hasNextLine())
		{
			String line = fileScan.nextLine();
			String[] pre = line.split(" ");

			if (pre.length > 5)
			{
				ArrayList<String> process = new ArrayList<String>();
				for (String s : pre)
					if (!s.replace(" ", "").equals(""))
						process.add(s); //index 5 is start of defocus
				String df1Pre = process.get(5);
				String df2Pre = process.get(6);
				String df1 = String.format("%.6f", Double.parseDouble(process.get(5)) + offset);
				String df2 = String.format("%.6f", Double.parseDouble(process.get(6)) + offset);
				String finString = line.substring(0, line.indexOf(df1Pre)) + df1 + " " + df2 + line.substring(line.indexOf(df2Pre) + df2Pre.length());
				writeLines.add(finString);
			}
			else
			{
				writeLines.add(line);
			}
		}
		
		System.out.println("Writing output file...");
		try
		{
			PrintWriter writer = new PrintWriter(outFile, "UTF-8");
			for (String s : writeLines)
			{
				writer.write(s + "\n");
			}
			writer.close();
		}
		catch (Exception ex)
		{
			System.out.println("Error. Send an email to calix1999@gmail.com with the following info:");
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		fileScan.close();
		
		System.out.println("Done!");
	}
	
	static void printHelp()
	{
		String jarName = new File(Parse.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
		System.out.println("\nUsage:");
		System.out.println(jarName + " --in <input_particles_star_file> --out <output_star_file> --offset <offset_defocus_by_this_amount>");
	}
}
