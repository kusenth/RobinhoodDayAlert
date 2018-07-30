package senthil.jayaraman;

public class LossGainModel {
	

	Float avgbuyprice; 
	Float quantity; 
	String stocksymbol;
	Float extendehourprice ;
	Float lastprice;
	Float lossgainpercent;

	public LossGainModel(Float avgbuyprice, Float quantity, String stocksymbol, Float extendehourprice, Float lastprice,
			Float lossgainpercent) {
		super();
		this.avgbuyprice = avgbuyprice;
		this.quantity = quantity;
		this.stocksymbol = stocksymbol;
		this.extendehourprice = extendehourprice;
		this.lastprice = lastprice;
		this.lossgainpercent = lossgainpercent;
	}
	
	public LossGainModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Float getAvgbuyprice() {
		return avgbuyprice;
	}
	public void setAvgbuyprice(Float avgbuyprice) {
		this.avgbuyprice = avgbuyprice;
	}
	public Float getQuantity() {
		return quantity;
	}
	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}
	public String getStocksymbol() {
		return stocksymbol;
	}
	public void setStocksymbol(String stocksymbol) {
		this.stocksymbol = stocksymbol;
	}
	public Float getExtendehourprice() {
		return extendehourprice;
	}
	public void setExtendehourprice(Float extendehourprice) {
		this.extendehourprice = extendehourprice;
	}
	public Float getLastprice() {
		return lastprice;
	}
	public void setLastprice(Float lastprice) {
		this.lastprice = lastprice;
	}
	public Float getLossgainpercent() {
		return lossgainpercent;
	}
	public void setLossgainpercent(Float lossgainpercent) {
		this.lossgainpercent = lossgainpercent;
	}
}
