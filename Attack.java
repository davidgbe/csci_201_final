public class Attack{
	private String name;
	private int damage;
	private double accuracy;
	private double crit;
	public Attack(String name, int damage, double acc, double crit){
		this.name = name;
		this.damage = damage;
		this.accuracy = acc; // number between 0 and 1;
		this.crit = crit; // number between 0 and 1;
	}
	public String getName(){
		return this.name;
	}
//	public int getDamage(){
//		return this.damage;
//	}
//	public double getAccuracy(){
//		return this.accuracy;
//	}
//	public double getCrit(){
//		return this.crit;
//	}
	public double totalDamage(Pokemon pok){
		if (Math.random()<this.accuracy){
			if (Math.random()<this.crit){
				return 1.5*this.damage*pok.getStrength();
			}
			else{
				return this.damage*pok.getStrength();
			}
		}
		else{
			return 0.0;
		}
	}
}