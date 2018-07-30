package senthil.jayaraman.robinhood.api.endpoint.orders.data;

import senthil.jayaraman.robinhood.api.ApiMethod;
import senthil.jayaraman.robinhood.api.endpoint.fundamentals.data.InstrumentFundamentalElement;
import senthil.jayaraman.robinhood.api.endpoint.fundamentals.methods.GetInstrumentFundamental;
import senthil.jayaraman.robinhood.api.request.RequestManager;
import senthil.jayaraman.robinhood.api.throwables.RobinhoodApiException;

public class OrderHistoryResults {

	private String updated_at;

	public String getUpdatedAt() {
		return this.updated_at;
	}

	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}

	private String ref_id;

	public String getRefId() {
		return this.ref_id;
	}

	public void setRefId(String ref_id) {
		this.ref_id = ref_id;
	}

	private String time_in_force;

	public String getTimeInForce() {
		return this.time_in_force;
	}

	public void setTimeInForce(String time_in_force) {
		this.time_in_force = time_in_force;
	}

	private String fees;

	public String getFees() {
		return this.fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	private String cancel;

	public String getCancel() {
		return this.cancel;
	}

	public void setCancel(String cancel) {
		this.cancel = cancel;
	}

	private String response_category;

	public String getResponseCategory() {
		return this.response_category;
	}

	public void setResponseCategory(String response_category) {
		this.response_category = response_category;
	}

	private String id;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String cumulative_quantity;

	public String getCumulativeQuantity() {
		return this.cumulative_quantity;
	}

	public void setCumulativeQuantity(String cumulative_quantity) {
		this.cumulative_quantity = cumulative_quantity;
	}

	private String stop_price;

	public String getStopPrice() {
		return this.stop_price;
	}

	public void setStopPrice(String stop_price) {
		this.stop_price = stop_price;
	}

	private String reject_reason;

	public String getRejectReason() {
		return this.reject_reason;
	}

	public void setRejectReason(String reject_reason) {
		this.reject_reason = reject_reason;
	}

	private String instrument;

	public String getInstrument() {
		return this.instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	private String state;

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	private String trigger;

	public String getTrigger() {
		return this.trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	private boolean override_dtbp_checks;

	public boolean getOverrideDtbpChecks() {
		return this.override_dtbp_checks;
	}

	public void setOverrideDtbpChecks(boolean override_dtbp_checks) {
		this.override_dtbp_checks = override_dtbp_checks;
	}

	private String type;

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String last_transaction_at;

	public String getLastTransactionAt() {
		return this.last_transaction_at;
	}

	public void setLastTransactionAt(String last_transaction_at) {
		this.last_transaction_at = last_transaction_at;
	}

	private String price;

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	private boolean extended_hours;

	public boolean getExtendedHours() {
		return this.extended_hours;
	}

	public void setExtendedHours(boolean extended_hours) {
		this.extended_hours = extended_hours;
	}

	private String account;

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	private String url;

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private String created_at;

	public String getCreatedAt() {
		return this.created_at;
	}

	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}

	private String side;

	public String getSide() {
		return this.side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	private boolean override_day_trade_checks;

	public boolean getOverrideDayTradeChecks() {
		return this.override_day_trade_checks;
	}

	public void setOverrideDayTradeChecks(boolean override_day_trade_checks) {
		this.override_day_trade_checks = override_day_trade_checks;
	}

	private String position;

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	private String average_price;

	public String getAveragePrice() {
		return this.average_price;
	}

	public void setAveragePrice(String average_price) {
		this.average_price = average_price;
	}

	private String quantity;

	public String getQuantity() {
		return this.quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
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
