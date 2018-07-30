package senthil.jayaraman.robinhood.api.endpoint.account.methods;

import senthil.jayaraman.robinhood.api.ConfigurationManager;
import senthil.jayaraman.robinhood.api.endpoint.account.Account;
import senthil.jayaraman.robinhood.api.request.RequestMethod;
import senthil.jayaraman.robinhood.api.endpoint.account.data.AccountHolderInvestmentElement;
import senthil.jayaraman.robinhood.api.parameters.HttpHeaderParameter;

public class GetAccountHolderInvestmentInfo extends Account {
	
	public GetAccountHolderInvestmentInfo() {
		
		this.setUrlBase("https://api.robinhood.com/accounts" + ConfigurationManager.getInstance().getAccountNumber() + "/positions/");
		
		//Add the headers into the request
		this.addHttpHeaderParameter(new HttpHeaderParameter("Accept", "application/json"));
		
		//This method is to be ran as GET
		this.setMethod(RequestMethod.GET);
		
		//Declare what the response should look like
		this.setReturnType(AccountHolderInvestmentElement.class);
	}

}
