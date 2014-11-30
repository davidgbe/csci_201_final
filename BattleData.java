public class BattleData extends Message{
	
	private String type;
	
	private int id;
	
	private String myPokemon;
	private int myHealth;
	private double myStrength;
	
	private String opponentPokemon;
	private int opponentHealth;
	private double opponentStrength;
	
	private String attackName;
	private boolean critical;
	private boolean missed;
	
	private String itemName;
	
	public BattleData(int id, String attack, String oName, int oHealth){
		this.id = id;
		this.type = "attack";
		this.attackName = attack;
		this.opponentPokemon = oName;
		this.opponentHealth = oHealth;
	}

	public BattleData(int id, String itemName, String myName, int newHealth, double newStrength) {
		this.id = id;
		this.type = "item";
		this.itemName = itemName;
		this.myPokemon = myName;
		this.myHealth = newHealth;
		this.myStrength = newStrength;
	}
	
	public BattleData(int id, String myName, String oName, int myHealth, int oHealth, double myStrength, double oStrength){
		this.id = id;
		this.type = "switch";
		this.myPokemon = myName;
		this.opponentPokemon = oName;
		this.myHealth = myHealth;
		this.opponentHealth = oHealth;
		this.myStrength = myStrength;
		this.opponentStrength = oStrength;
	}
	
	public double getMyStrength() {
		return myStrength;
	}

	public void setMyStrength(double myStrength) {
		this.myStrength = myStrength;
	}

	public double getOpponentStrength() {
		return opponentStrength;
	}

	public void setOpponentStrength(double opponentStrength) {
		this.opponentStrength = opponentStrength;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMyPokemon() {
		return myPokemon;
	}

	public void setMyPokemon(String myPokemon) {
		this.myPokemon = myPokemon;
	}

	public int getMyHealth() {
		return myHealth;
	}

	public void setMyHealth(int myHealth) {
		this.myHealth = myHealth;
	}

	public String getOpponentPokemon() {
		return opponentPokemon;
	}

	public void setOpponentPokemon(String opponentPokemon) {
		this.opponentPokemon = opponentPokemon;
	}

	public int getOpponentHealth() {
		return opponentHealth;
	}

	public void setOpponentHealth(int opponentHealth) {
		this.opponentHealth = opponentHealth;
	}

	public String getAttackName() {
		return attackName;
	}

	public void setAttackName(String attackName) {
		this.attackName = attackName;
	}

	public boolean isCritical() {
		return critical;
	}

	public void setCritical(boolean critical) {
		this.critical = critical;
	}

	public boolean isMissed() {
		return missed;
	}

	public void setMissed(boolean missed) {
		this.missed = missed;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
}