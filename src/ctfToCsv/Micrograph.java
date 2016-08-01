package ctfToCsv;

public class Micrograph {
	//defocusU, defocusV, angle, ccc
	public String name;
	public double defocusU;
	public double defocusV;
	public double angle;
	public double ccc;
	public Micrograph(String name, double defocusU, double defocusV, double angle, double ccc)
	{
		this.name = name;
		this.defocusU = defocusU;
		this.defocusV = defocusV;
		this.angle = angle;
		this.ccc = ccc;
	}
	public String getName() { return name; }
	public double getDefocusU() { return defocusU; }
	public double getDefocusV() { return defocusV; }
	public double getAngle() { return angle; }
	public double getCCC() { return ccc; }
}
