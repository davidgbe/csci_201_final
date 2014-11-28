public class Attack{
	private String name;
	private int damage;
	private double accuracy;
	private double crit;
	public Attack(String name, int damage, double acc, double crit){
		this.name = name;
		this.damage = damage;
		this.accuracy = acc;
		this.crit = crit;
	}
	public String getName(){
		return this.name;
	}
	public int getDamage(){
		return this.damage;
	}
	public double getAccuracy(){
		return this.accuracy;
	}
	public double getCrit(){
		return this.crit;
	}
}