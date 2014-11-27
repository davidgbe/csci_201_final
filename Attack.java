public class Attack extends Message {
	private String name;
	private int damage;
	private double accuracy;
	private double crit;
	
	public Attack() {
		
	}
	
	public Attack(String n, int d) {
		this.name = name;
		this.damage = d;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public double getCrit() {
		return crit;
	}

	public void setCrit(double crit) {
		this.crit = crit;
	}
	
}