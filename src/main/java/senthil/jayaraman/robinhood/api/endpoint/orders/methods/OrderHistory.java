package senthil.jayaraman.robinhood.api.endpoint.orders.methods;

import java.net.URL;

import senthil.jayaraman.robinhood.api.ConfigurationManager;
import senthil.jayaraman.robinhood.api.endpoint.orders.Orders;
import senthil.jayaraman.robinhood.api.endpoint.orders.enums.TimeInForce;
import senthil.jayaraman.robinhood.api.endpoint.orders.throwables.InvalidTickerException;
import senthil.jayaraman.robinhood.api.parameters.UrlParameter;
import senthil.jayaraman.robinhood.api.throwables.RobinhoodApiException;
import senthil.jayaraman.robinhood.api.endpoint.orders.enums.OrderTransactionType;

public class OrderHistory extends Orders {

	public OrderHistory() throws RobinhoodApiException, InvalidTickerException {
		setOrderGetparameters();
	}

}
