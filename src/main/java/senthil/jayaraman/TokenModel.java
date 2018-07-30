package senthil.jayaraman;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity(name = "TOKEN_HISTORY")
@Table(name = "TOKEN_HISTORY")
public class TokenModel implements Serializable {
 
    private static final long serialVersionUID = -3009157732242241606L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
  
    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column(name = "TOKEN")
    private String token;
    
   
	public Timestamp  getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    @Column(name = "TIMESTAMP")
    private Timestamp  timestamp;
    
    protected TokenModel() {
    }
 
    public TokenModel(String token,Timestamp time ) {
        this.token = token;
        this.timestamp = time;
        
    }
 
    @Override
    public String toString() {
        return String.format("TOKEN_HISTORY[id=%d,token='%s',timestamp='%d']",id, token,timestamp);
    }
}