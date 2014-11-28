public class ChatMessage extends Message{
	
	private boolean isGlobalMessage;
	private String messageContents;
	private String recipient;
	
	public ChatMessage(String contents, String fromUser){
		
	this.messageContents = contents;
	this.messageFrom  = fromUser;
	}
	
	public boolean isGlobalMessage() {
		return isGlobalMessage;
	}
	public void setGlobalMessage(boolean isGlobalMessage) {
		this.isGlobalMessage = isGlobalMessage;
	}
	public String getMessageContents() {
		return messageContents;
	}
	public void setMessageContents(String messageContents) {
		this.messageContents = messageContents;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}






}