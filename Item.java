public class Item extends Message {
	private String type;
	
	public Item(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
}