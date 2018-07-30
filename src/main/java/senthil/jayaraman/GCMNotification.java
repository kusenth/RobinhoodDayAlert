package senthil.jayaraman;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import senthil.jayaraman.robinhood.api.endpoint.orders.throwables.InvalidTickerException;
import senthil.jayaraman.robinhood.api.throwables.RobinhoodApiException;
import senthil.jayaraman.robinhood.api.throwables.RobinhoodNotLoggedInException;

@RestController
public class GCMNotification {
	private static final long serialVersionUID = 1L;
	
	public GCMNotification() {
		super();
	}
	
	
	@Autowired
	RobinhoodMApplication RobinhoodMApplication;
	
	@Autowired
	TokenRepo repository;
	
	@Autowired
	PriceHistoryRepo pricerepository;

	@RequestMapping(value = "/GCMNotification", method = RequestMethod.POST)
	protected void persist(@RequestBody String tokenGCM) throws ServletException, IOException {
		TokenModel model = new TokenModel();
		model.setTimestamp(new Timestamp(System.currentTimeMillis()));
		model.setToken(tokenGCM);
		List<String> tokenlist = repository.findByToken();
		if(tokenlist !=null && tokenlist.size()>0) {
			repository.updateToken(tokenGCM,new Timestamp(System.currentTimeMillis()));
		} else {
			repository.save(model);
		}
	}	
	
	@RequestMapping(value = "/Reset", method = RequestMethod.GET)
	protected void delete() throws ServletException, IOException {
		pricerepository.deleteAll();
	}		
	
	
	@RequestMapping(value = "/Limit", method = RequestMethod.GET)
	protected void limit() throws ServletException, IOException, InvalidTickerException, RobinhoodApiException, RobinhoodNotLoggedInException {
		//RobinhoodMApplication.limitorder();
	
	}
}