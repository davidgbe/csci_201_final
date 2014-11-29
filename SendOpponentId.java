
public class SendOpponentId extends Message{
	
	private int IDOfOpponent;
	
	public SendOpponentId(int a){
		this.IDOfOpponent = a;
	}

	public int getIDOfOpponent() {
		return IDOfOpponent;
	}

	public void setIDOfOpponent(int iDOfOpponent) {
		IDOfOpponent = iDOfOpponent;
	}
	

}
