import javax.swing.ImageIcon;


public class Mewtwo extends Pokemon{
	public Mewtwo(){
		super("mewtwo", 100, new ImageIcon("images/mewtwo.png"), 150.0);
		allAttacks[0] = "thunderbolt";
		allAttacks[1] = "bolt strike";
		allAttacks[2] = "flamethrower";
		allAttacks[3] = "headbutt";
	}
}
