package senthil.jayaraman.robinhood.api.endpoint.orders;

import java.net.URL;

import com.mashape.unirest.http.exceptions.UnirestException;
import senthil.jayaraman.robinhood.api.ApiMethod;
import senthil.jayaraman.robinhood.api.endpoint.fundamentals.data.TickerFundamentalElement;
import senthil.jayaraman.robinhood.api.endpoint.orders.enums.TimeInForce;
import senthil.jayaraman.robinhood.api.endpoint.orders.throwables.InvalidTickerException;
import senthil.jayaraman.robinhood.api.request.RequestMethod;
import senthil.jayaraman.robinhood.api.throwables.RobinhoodApiException;
import senthil.jayaraman.robinhood.api.endpoint.fundamentals.methods.GetTickerFundamental;
import senthil.jayaraman.robinhood.api.endpoint.orders.data.OrderHistoryListResults;
import senthil.jayaraman.robinhood.api.endpoint.orders.data.OrderHistoryResults;
import senthil.jayaraman.robinhood.api.endpoint.orders.data.SecurityOrderElement;
import senthil.jayaraman.robinhood.api.endpoint.orders.enums.OrderTransactionType;
import senthil.jayaraman.robinhood.api.parameters.HttpHeaderParameter;
import senthil.jayaraman.robinhood.api.request.RequestManager;

public class Orders extends ApiMethod {

	protected Orders() {

		super("orders");
		orderSafe = true;
	}

	/**
	 * Flag variable which declares if an order is 'safe' to send. This may be false for a various amount of reasons.
	 * Primarily this is set to false if a invalid ticker is entered.
	 * This variable is checked during the order execution. If true, the order sends. If false, the order cancels.
	 */
	private boolean orderSafe;

	/**
	 * Method which sets up the basic parameters for the endpoint.
	 * This does not include the order data.
	 */
	protected void setEndpointParameters() {

		this.setUrlBase("https://api.robinhood.com/orders/");

		//Add the send-receive headers into the request
		this.addHttpHeaderParameter(new HttpHeaderParameter("Accept", "application/json"));
		this.addHttpHeaderParameter(new HttpHeaderParameter("Content-Type", "application/x-www-form-urlencoded"));

		//This method should be ran as POST
		this.setMethod(RequestMethod.POST);

		this.setReturnType(SecurityOrderElement.class);
	}
	
	protected void setTickerCancelParameters(URL url) {

		this.setUrlBase(url.toString());

		//Add the send-receive headers into the request
		this.addHttpHeaderParameter(new HttpHeaderParameter("Accept", "application/json"));
		this.addHttpHeaderParameter(new HttpHeaderParameter("Content-Type", "application/x-www-form-urlencoded"));

		//This method should be ran as POST
		this.setMethod(RequestMethod.POST);

		this.setReturnType(SecurityOrderElement.class);
	}
	
	protected void setOrderGetparameters() {

		this.setUrlBase("https://api.robinhood.com/orders/");

		//Add the send-receive headers into the request
		this.addHttpHeaderParameter(new HttpHeaderParameter("Accept", "application/json"));
		this.addHttpHeaderParameter(new HttpHeaderParameter("Content-Type", "application/x-www-form-urlencoded"));

		//This method should be ran as POST
		this.setMethod(RequestMethod.GET);

		this.setReturnType(OrderHistoryListResults.class);
	}


	/**
	 * Method which parses the Time of Day enum variable and returns the simplified version which the API
	 * requires in order to make a request
	 */
	protected String getTimeInForceString(TimeInForce time) {

		try {

			switch (time) {

				case FILL_OR_KILL:
					return "fok";

				case GOOD_FOR_DAY:
					return "gfd";

				case GOOD_UNTIL_CANCELED:
					return "gtc";

				case IMMEDIATE_OR_CANCEL:
					return "ioc";

				case ON_MARKET_OPEN:
					return "opg";

				//You should never see this..
				default:
					throw new Exception();

			}
		} catch (Exception ex) {

			System.err.println("[RobinhoodApi] ERROR - Time in Force parsing failed. You shouldn't see this, please file a bug report on github!");
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Method which parses the Order Side enum value.
	 * This should only return BUY or SELL
	 *
	 * @param type
	 */
	protected String getOrderSideString(OrderTransactionType type) {

		try {

			switch (type) {
				case BUY:
					return "buy";

				case SELL:
					return "sell";

				default:
					throw new Exception();

			}

		} catch (Exception ex) {

			System.err.println("[Robinhood API] ERROR - Order Side parsing failed. You shouldn't see this. Please file a bug report on github!");
			ex.printStackTrace();

		}
		return null;

	}

	/**
	 * Method which verifies that the ticker is a valid one. If not, throw an error.
	 * This method also supplies additional information of the Ticker symbol that the order class
	 * is required to use.
	 *
	 * @throws InvalidTickerException
	 */
	protected String verifyTickerData(String ticker) throws RobinhoodApiException, InvalidTickerException {

		//Make a Ticker Fundamental API request for the supplied ticker
		RequestManager requestManager = RequestManager.getInstance();

		ApiMethod method = new GetTickerFundamental(ticker);

		TickerFundamentalElement response = requestManager.makeApiRequest(method);

		//Does the ticker have a valid Instrument URL? If not, this ticker is invalid. Throw an error.
		if (response.getInstrument() == null)
			throw new InvalidTickerException();

		//Otherwise, supply the InstrumentURL to the class to be used in the request
		return response.getInstrument().toString();

	}



	public boolean isOrderSafe() {
		return orderSafe;
	}

	public void setOrderSafe(boolean orderSafe) {
		this.orderSafe = orderSafe;
	}
}




