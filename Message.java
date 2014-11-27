
import java.io.Serializable;

public class Message implements Serializable{
 
    protected String messageFrom;
 
    public Message() {
    	
    }
 
    public String getUsernameMessageIsFrom(){
        return messageFrom;
    }
    public void setUsernameMessageIsFrom(String uname){
    	this.messageFrom = uname;
    }
}
