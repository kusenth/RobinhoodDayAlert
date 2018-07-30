package senthil.jayaraman.robinhood.api.endpoint.fundamentals.methods;

import senthil.jayaraman.robinhood.api.endpoint.fundamentals.Fundamentals;
import senthil.jayaraman.robinhood.api.endpoint.fundamentals.data.TickerFundamentalElement;
import senthil.jayaraman.robinhood.api.parameters.HttpHeaderParameter;
import senthil.jayaraman.robinhood.api.request.RequestMethod;

public class GetTickerFundamental extends Fundamentals {
	
	public GetTickerFundamental(String ticker) {
		
		this.setUrlBase("https://api.robinhood.com/fundamentals/" + ticker +"/");
		
		//Add the headers into the request
		this.addHttpHeaderParameter(new HttpHeaderParameter("Accept", "appliation/json"));
		
		//This method is ran as GET
		this.setMethod(RequestMethod.GET);
		
		//Declare what the response should look like
		this.setReturnType(TickerFundamentalElement.class);
	}

}
