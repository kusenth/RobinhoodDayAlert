package senthil.jayaraman.robinhood.api.endpoint.account.data.enums;

import senthil.jayaraman.robinhood.api.ApiMethod;
import senthil.jayaraman.robinhood.api.endpoint.fundamentals.data.InstrumentFundamentalElement;
import senthil.jayaraman.robinhood.api.endpoint.fundamentals.methods.GetInstrumentFundamental;
import senthil.jayaraman.robinhood.api.request.RequestManager;
import senthil.jayaraman.robinhood.api.throwables.RobinhoodApiException;

/**
 * Element containing information of a given position which exists on a users
 * watchlist.
 */
public class PositionWatch {

	public String getWatchlist() {
		return watchlist;
	}

	public void setWatchlist(String watchlist) {
		this.watchlist = watchlist;
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	private String watchlist;
	private String instrument;
	private String created_at;

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
