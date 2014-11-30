import java.util.HashMap;


public class DamageCalculator {
	private HashMap<String, AttackInformation> attackMap;	
	public DamageCalculator(){
		this.attackMap = new HashMap<String, AttackInformation>();
//		initialize attacks
		AttackInformation[] allAttacks = new AttackInformation[15];
		allAttacks[0] = new AttackInformation("waterfall", 7, 1.0, .125);
		allAttacks[1] = new AttackInformation("surf", 8, 0.9, .125);
		allAttacks[2] = new AttackInformation("hydro pump", 9, .75, .125 );
		allAttacks[3] = new AttackInformation("hydro cannon", 10, .65, .125);
		allAttacks[4] = new AttackInformation("blaze kick", 6, 1.0, .25);
		allAttacks[5] = new AttackInformation("flamethrower", 7, .9, .25);
		allAttacks[6] = new AttackInformation("fire blast", 8, .75, .25);
		allAttacks[7] = new AttackInformation("blast burn", 9, .65, .25);
		allAttacks[8] = new AttackInformation("thunder punch", 5, 1.0, .5);
		allAttacks[9] = new AttackInformation("thunderbolt", 6, 0.9, .5);
		allAttacks[10] = new AttackInformation("volt tackle", 7, 0.75, .5);
		allAttacks[11] = new AttackInformation("bolt strike", 8, 0.65, .5);
		allAttacks[12] = new AttackInformation("earthquake", 8, 1.0, 0.0);
		allAttacks[13] = new AttackInformation("headbutt", 6, 1.0, 0.7);
		allAttacks[14] = new AttackInformation("sheer cold", 200000, .3, 1.0);
		
		
//		initialize hash map
		this.attackMap.put("waterfall", allAttacks[0]);
		this.attackMap.put("surf", allAttacks[1]);
		this.attackMap.put("hydro pump", allAttacks[2]);
		this.attackMap.put("hydro cannon", allAttacks[3]);
		this.attackMap.put("blaze kick", allAttacks[4]);
		this.attackMap.put("flamethrower", allAttacks[5]);
		this.attackMap.put("fire blast", allAttacks[6]);
		this.attackMap.put("blast burn", allAttacks[7]);
		this.attackMap.put("thunder punch", allAttacks[8]);
		this.attackMap.put("thunderbolt", allAttacks[9]);
		this.attackMap.put("volt tackle", allAttacks[10]);
		this.attackMap.put("bolt strike", allAttacks[11]);
		this.attackMap.put("earthquake", allAttacks[12]);
		this.attackMap.put("headbutt", allAttacks[13]);
		this.attackMap.put("sheer cold", allAttacks[14]);
		
	}
	public double getTotalDamage(String attackName, Pokemon pok){
		return this.attackMap.get(attackName).totalDamage(pok);
	}
}

class AttackInformation{
	private String name;
	private int damage;
	private double accuracy;
	private double crit;
	public AttackInformation(String name, int damage, double acc, double crit){
		this.name = name;
		this.damage = damage;
		this.accuracy = acc;
		this.crit = crit;
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
				return 1.0*this.damage*pok.getStrength();
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

