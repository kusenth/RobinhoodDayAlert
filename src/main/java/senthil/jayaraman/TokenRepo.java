package senthil.jayaraman;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TokenRepo extends CrudRepository<TokenModel, Timestamp>{
	//List<TokenModel> findByTokenOrderByTimestampDesc();
	
	 @Transactional
	 @Modifying
	 @Query("SELECT token FROM TOKEN_HISTORY ORDER BY timestamp DESC")
	 public List<String> findByToken();
	 
	 
	 @Transactional
	 @Modifying
	 @Query("UPDATE TOKEN_HISTORY K SET K.token = :token,K.timestamp = :timestamp")
	 public void updateToken(@Param("token") String token, @Param("timestamp") Timestamp timestamp);
	   
} 