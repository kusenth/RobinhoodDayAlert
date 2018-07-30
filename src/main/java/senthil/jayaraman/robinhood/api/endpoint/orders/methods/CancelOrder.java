package senthil.jayaraman.robinhood.api.endpoint.orders.methods;

import java.net.URL;

import senthil.jayaraman.robinhood.api.ConfigurationManager;
import senthil.jayaraman.robinhood.api.endpoint.orders.Orders;
import senthil.jayaraman.robinhood.api.endpoint.orders.enums.TimeInForce;
import senthil.jayaraman.robinhood.api.endpoint.orders.throwables.InvalidTickerException;
import senthil.jayaraman.robinhood.api.parameters.UrlParameter;
import senthil.jayaraman.robinhood.api.throwables.RobinhoodApiException;
import senthil.jayaraman.robinhood.api.endpoint.orders.enums.OrderTransactionType;

public class CancelOrder extends Orders {

	private URL tickerCancelURL = null;

	public CancelOrder(URL cancelurl) throws RobinhoodApiException, InvalidTickerException {

		this.tickerCancelURL = cancelurl;
		setTickerCancelParameters(cancelurl);

	}

}
