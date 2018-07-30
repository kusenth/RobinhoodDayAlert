package senthil.jayaraman.robinhood.api.endpoint.account.methods;

import senthil.jayaraman.robinhood.api.endpoint.account.Account;
import senthil.jayaraman.robinhood.api.request.RequestMethod;
import senthil.jayaraman.robinhood.api.endpoint.account.data.AccountHolderAffiliationElement;
import senthil.jayaraman.robinhood.api.parameters.HttpHeaderParameter;

public class GetAccountHolderAffiliationInfo extends Account {
	
	public GetAccountHolderAffiliationInfo() {
		
		this.setUrlBase("https://api.robinhood.com/user/additional_info/");
		
		//Add the headers into the request
		this.addHttpHeaderParameter(new HttpHeaderParameter("Accept", "application/json"));
		
		//This method is ran as GET
		this.setMethod(RequestMethod.GET);
		
		//Declare what the response should look like
		this.setReturnType(AccountHolderAffiliationElement.class);
	}
	

}
