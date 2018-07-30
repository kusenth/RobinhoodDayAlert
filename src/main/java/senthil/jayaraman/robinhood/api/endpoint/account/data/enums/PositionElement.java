package senthil.jayaraman.robinhood.api.endpoint.account.data.enums;


import senthil.jayaraman.robinhood.api.ApiMethod;
import senthil.jayaraman.robinhood.api.endpoint.fundamentals.data.InstrumentFundamentalElement;
import senthil.jayaraman.robinhood.api.endpoint.fundamentals.methods.GetInstrumentFundamental;
import senthil.jayaraman.robinhood.api.request.RequestManager;
import senthil.jayaraman.robinhood.api.throwables.RobinhoodApiException;

/**
 * Element containing information of a given position which exists on a users watchlist.
 */
public class PositionElement {


    private float shares_held_for_stock_grants;
    private float intraday_quantity;
    private float intraday_average_buy_price;

    //TODO: created_at and updated_at

    private float shares_held_for_buys;
    private float average_buy_price;
    private float shares_held_for_sells;
    private float quantity;
    private String created_at;
    private String updated_at;
    

    public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getCreated_at() {
		return created_at;
	}

	private String instrument;


    public float getShares_held_for_stock_grants() {
        return shares_held_for_stock_grants;
    }

    public float getIntraday_quantity() {
        return intraday_quantity;
    }

    public float getIntraday_average_buy_price() {
        return intraday_average_buy_price;
    }

    public float getShares_held_for_buys() {
        return shares_held_for_buys;
    }

    public float getAverage_buy_price() {
        return average_buy_price;
    }

    public float getShares_held_for_sells() {
        return shares_held_for_sells;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getStockName() {

        ApiMethod method = new GetInstrumentFundamental(this.instrument);
        InstrumentFundamentalElement element;

        try {

            element = RequestManager.getInstance().makeApiRequest(method);
            return element.getStockName();

        } catch (RobinhoodApiException e) {

            return "";

        }

    }

    public String getStockTicker() {

        ApiMethod method = new GetInstrumentFundamental(this.instrument);
        InstrumentFundamentalElement element;

        try {

            element = RequestManager.getInstance().makeApiRequest(method);
            return element.getSymbol();

        } catch (RobinhoodApiException e) {

            return "";

        }

    }


}
