package senthil.jayaraman;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity(name = "INTRADAY_PRICE_HISTORY")
@Table(name = "INTRADAY_PRICE_HISTORY")
public class IntradayPriceHistoryModel implements Serializable {
 
    private static final long serialVersionUID = -3009157732242241606L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
  
    @Column(name = "SYMBOL")
    private String symbol;
    
    @Column(name = "PREV_PROFIT")
    private Float prevlossgainpercent;
    
    @Column(name = "HIGH_PERCENT_DAY")
    private Float highpercentday;
	
	@Column(name = "TOTAL_PROFIT_LOSS")
    private Float totalprofitloss;
	
	@Column(name = "LOW_PERCENT_DAY")
    private Float lowpercentday;
	
	@Column(name = "AVG_BUY_PRICE")
    private Float lastprice;
    
    @Column(name = "CURRENT_MARKET_PRICE")
    private Float currentprice;
    
    @Column(name = "TIMESTAMP")
    private Timestamp  timestamp;
    
    @Column(name = "TOTAL_PROFIT_PERCENT")
    private Float profit;
    
    @Column(name = "CANCEL_URL")
    private String cancelurl;
    
    @Column(name = "STOCK_BUY_DATE")
    private Timestamp stockbuydate;
    
    public String getCancelURL() {
 		return cancelurl;
 	}
    
    public String getTimetosell() {
		return timetosell;
	}

	public void setTimetosell(String timetosell) {
		this.timetosell = timetosell;
	}

	@Column(name = "TIME_TO_SELL")
    private String timetosell;

	public Timestamp getStockbuydate() {
		return stockbuydate;
	}

	public void setStockbuydate(Timestamp stockbuydate) {
		this.stockbuydate = stockbuydate;
	}

	public void setCancelURL(String cancelurl) {
		this.cancelurl = cancelurl;
	}    
    
    public Float getPrevlossgainpercent() {
		return prevlossgainpercent;
	}

	public void setPrevlossgainpercent(Float prevlossgainpercent) {
		this.prevlossgainpercent = prevlossgainpercent;
	}
	
    
    public Float getTotalprofitloss() {
		return totalprofitloss;
	}

	public void setTotalprofitloss(Float totalprofitloss) {
		this.totalprofitloss = totalprofitloss;
	}

	public Float getHighpercentday() {
		return highpercentday;
	}

	public void setHighpercentday(Float highpercentday) {
		this.highpercentday = highpercentday;
	}

	public Float getLowpercentday() {
		return lowpercentday;
	}

	public void setLowpercentday(Float lowpercentday) {
		this.lowpercentday = lowpercentday;
	}    
 
    public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Float getLastprice() {
		return lastprice;
	}

	public void setLastprice(Float lastprice) {
		this.lastprice = lastprice;
	}

	public Float getCurrentprice() {
		return currentprice;
	}

	public void setCurrentprice(Float currentprice) {
		this.currentprice = currentprice;
	}

	public Timestamp  getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Float getProfit() {
		return profit;
	}

	public void setProfit(Float profit) {
		this.profit = profit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    protected IntradayPriceHistoryModel() {
    }
 
    public IntradayPriceHistoryModel(String symbol, Float lastprice ,Float currentprice,Timestamp  time,Float profit,Float lowpercentday,Float highpercentday ) {
        this.symbol = symbol;
        this.lastprice = lastprice;
        this.currentprice = currentprice;
        this.timestamp = time;
        this.profit = profit;
        this.lowpercentday = lowpercentday;
        this.highpercentday = highpercentday;
    }
 
    @Override
    public String toString() {
        return String.format("INTRADAY_PRICE_HISTORY[id=%d,symbol='%s', lastprice='%f',currentprice='%f',timestamp='%d',profit='%f']", symbol, lastprice, currentprice,timestamp,profit);
    }
}