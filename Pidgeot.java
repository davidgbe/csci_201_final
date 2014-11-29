import javax.swing.ImageIcon;


public class Pidgeot extends Pokemon{
	public Pidgeot(){
		super("pidgeot", 400, new ImageIcon("images/pidgeot.png"), 75.0);
		allAttacks[0] = "surf";
		allAttacks[1] = "thunder punch";
		allAttacks[2] = "earthquake";
		allAttacks[3] = "waterfall";
	}
}
