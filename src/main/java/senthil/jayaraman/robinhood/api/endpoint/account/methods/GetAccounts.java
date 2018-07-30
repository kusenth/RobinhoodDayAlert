package senthil.jayaraman.robinhood.api.endpoint.account.methods;

import senthil.jayaraman.robinhood.api.endpoint.account.Account;
import senthil.jayaraman.robinhood.api.endpoint.account.data.AccountArrayWrapper;
import senthil.jayaraman.robinhood.api.request.RequestMethod;
import senthil.jayaraman.robinhood.api.parameters.HttpHeaderParameter;

public class GetAccounts extends Account {
	
	public GetAccounts()  {
		
		this.setUrlBase("https://api.robinhood.com/accounts/");
		
		//Add the headers into the request
		this.addHttpHeaderParameter(new HttpHeaderParameter("Accept", "application/json"));
		
		//This method is ran as GET
		this.setMethod(RequestMethod.GET);
		
		//Declare what the response should look like
		this.setReturnType(AccountArrayWrapper.class);
	}

}
