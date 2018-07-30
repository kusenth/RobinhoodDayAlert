package senthil.jayaraman.robinhood.api.endpoint.authorize.methods;

import senthil.jayaraman.robinhood.api.parameters.UrlParameter;
import senthil.jayaraman.robinhood.api.endpoint.authorize.Authentication;
import senthil.jayaraman.robinhood.api.endpoint.authorize.data.Token;
import senthil.jayaraman.robinhood.api.parameters.HttpHeaderParameter;
import senthil.jayaraman.robinhood.api.request.RequestMethod;

public class AuthorizeWithoutMultifactor extends Authentication {
	
	public AuthorizeWithoutMultifactor(String username, String password) {
		
		setUrlBase("https://api.robinhood.com/api-token-auth/");
		
		//Add the parameters into the request
		this.addUrlParameter(new UrlParameter("username", username));
		this.addUrlParameter(new UrlParameter("password", password));
		
		//We're going to want a Json response
		this.addHttpHeaderParameter(new HttpHeaderParameter("Accept", "application/json"));
		this.addHttpHeaderParameter(new HttpHeaderParameter("Content-Type", "application/x-www-form-urlencoded"));
		
		//This needs to be ran as POST
		this.setMethod(RequestMethod.POST);
		
		//Declare what the response should look like
		this.setReturnType(Token.class);
		
	}

}
