package senthil.jayaraman.robinhood.api.endpoint.authorize.methods;

import senthil.jayaraman.robinhood.api.request.RequestMethod;
import senthil.jayaraman.robinhood.api.endpoint.authorize.Authentication;
import senthil.jayaraman.robinhood.api.parameters.HttpHeaderParameter;

public class LogoutFromRobinhood extends Authentication{
	
	public LogoutFromRobinhood() {
		
		this.setUrlBase("https://api.robinhood.com/api-token-logout/");
		
		//Add the header parameters
		this.addHttpHeaderParameter(new HttpHeaderParameter("Accept", "application/json"));
		
		//This needs to be ran as POST
		this.setMethod(RequestMethod.POST);
		
		//We are not expecting a response back.
		this.setReturnType(Void.TYPE);
	}

}
