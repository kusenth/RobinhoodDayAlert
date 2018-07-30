package senthil.jayaraman.robinhood.api.endpoint.account.methods;

import senthil.jayaraman.robinhood.api.ConfigurationManager;
import senthil.jayaraman.robinhood.api.endpoint.account.Account;
import senthil.jayaraman.robinhood.api.endpoint.account.data.enums.PositionListElement;
import senthil.jayaraman.robinhood.api.endpoint.account.data.enums.PositionListWatch;
import senthil.jayaraman.robinhood.api.parameters.HttpHeaderParameter;
import senthil.jayaraman.robinhood.api.request.RequestMethod;

/**
 * Created by SirensBell on 6/19/2017.
 */
public class GetAccountWatch extends Account {

    public GetAccountWatch() {

        //Get the current account ID to be used with the position search
        String accountId = ConfigurationManager.getInstance().getAccountNumber();

        this.setUrlBase("https://api.robinhood.com/watchlists/Default/");

        //Add the headers into the request'
        this.addHttpHeaderParameter(new HttpHeaderParameter("Accept", "application/json"));

        //This method is to be ran as GETe
        this.setMethod(RequestMethod.GET);

        //Declare what the response should look like
        this.setReturnType(PositionListWatch.class);

    }
}
