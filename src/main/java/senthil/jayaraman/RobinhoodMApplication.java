package senthil.jayaraman;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import senthil.jayaraman.robinhood.api.RobinhoodApi;
import senthil.jayaraman.robinhood.api.endpoint.account.data.enums.PositionElement;
import senthil.jayaraman.robinhood.api.endpoint.account.data.enums.PositionWatch;
import senthil.jayaraman.robinhood.api.endpoint.orders.data.OrderHistoryResults;
import senthil.jayaraman.robinhood.api.endpoint.orders.data.SecurityOrderElement;
import senthil.jayaraman.robinhood.api.endpoint.orders.enums.OrderTransactionType;
import senthil.jayaraman.robinhood.api.endpoint.orders.enums.TimeInForce;
import senthil.jayaraman.robinhood.api.endpoint.orders.throwables.InvalidTickerException;
import senthil.jayaraman.robinhood.api.endpoint.quote.data.TickerQuoteElement;
import senthil.jayaraman.robinhood.api.request.RequestStatus;
import senthil.jayaraman.robinhood.api.throwables.RobinhoodApiException;
import senthil.jayaraman.robinhood.api.throwables.RobinhoodNotLoggedInException;

@EnableScheduling
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class RobinhoodMApplication{
	
	RobinhoodApi api = new RobinhoodApi();
	RobinhoodApi apiobj = new RobinhoodApi();
	List<PositionElement> pos;
	List<PositionWatch> poswtch;
	List<String> columns;
	RequestStatus status;
	public static float lastprice = 0;

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Value("${RobinHoodUserName}")
	private String userName;

	@Value("${RobinHoodPassword}")
	private String password;

	@Value("${ProfitOrLossPercentChangeBy}")
	private Float percentchangeby;

	@Value("${TimetoSellPreVPercent}")
	private Float prevpercentage;

	List<Float> pricelist = new ArrayList<Float>();

	@Autowired
	PriceHistoryRepo repository;

	@Autowired
	IntradayPriceHistoryRepo intradayrepository;

	@Autowired
	TokenRepo tokenrepository;

	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

	HashMap<String, Float> valuemap = new HashMap<String, Float>();
	HashMap<String, URL> buycancelmap = new HashMap<String, URL>();
	HashMap<String, URL> sellcancelmap = new HashMap<String, URL>();

	PushNotify notify = (PushNotify) context.getBean("PushNotify");

	public static void main(String[] args) {
		SpringApplication.run(RobinhoodMApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void Startup() throws RobinhoodApiException, RobinhoodNotLoggedInException, InterruptedException {
		status = api.logUserIn(userName, password);
		Thread.sleep(10000);
		if (RequestStatus.SUCCESS.equals(status)) {
			pos = api.getAccountPositions();
		}
	}

	

	@Scheduled(fixedDelay = 6000000)
	public void logionAPI() throws RobinhoodApiException, RobinhoodNotLoggedInException, IOException {
		api.logUserIn(userName, password);
	}

	@Scheduled(fixedDelay = 8000)
	public void getpostionvalues()
			throws RobinhoodApiException, RobinhoodNotLoggedInException, IOException, InvalidTickerException {
		Document doc;
		doc = Jsoup.connect(
				"https://finance.yahoo.com/losers/")
				.get();
		Elements el = doc.getElementsByClass("SimpleDataTableRow");
		StringBuffer buff = new StringBuffer();
		for (Element link : el) {
			buff.append(link.text());
		}
		String [] ar = buff.toString().split("/");
		for(int i=0;i+4<ar.length;i=i+10) {
			System.out.println(ar[i]+ar[i+4]);
		}
		
		if (RequestStatus.SUCCESS.equals(status)) {
			pos = api.getAccountPositions();
			poswtch = api.getAccountWatch();
		}
	}

	@Scheduled(fixedDelay = 8000)
	public void SaveDatatoDB()
			throws RobinhoodApiException, RobinhoodNotLoggedInException, IOException, ParseException {
		SimpleDateFormat monthDayformatter = new SimpleDateFormat("yyyy-MM-dd");
		boolean symboldiff = false;

		if (RequestStatus.SUCCESS.equals(status) && pos != null && pos.size() > 0) {
			List<PriceHistoryModel> allList = (List<PriceHistoryModel>) repository.findAll();
			List<String> listone = allList.stream().map(a -> a.getSymbol()).sorted().collect(Collectors.toList());
			List<String> listtwo = pos.stream().map(k -> k.getStockTicker()).sorted().collect(Collectors.toList());
			listone.removeAll(listtwo);
			listone.forEach(a -> {
				repository.deletemodel(a);
			});

			for (PositionElement ele : pos) {
				TickerQuoteElement ticker = api.getQuoteByTicker(ele.getStockTicker());
				Float qty = ele.getQuantity();
				Float prevprice = ticker.getPrevious_close();
				Float lastprice = ticker.getLast_extended_hours_trade_price() == 0.0 ? ticker.getLast_trade_price()
						: ticker.getLast_extended_hours_trade_price();
				Float lossgainpercent = ((lastprice - ele.getAverage_buy_price()) / ele.getAverage_buy_price()) * 100;
				Float prevlossgainpercent = ((prevprice - ele.getAverage_buy_price()) / ele.getAverage_buy_price())
						* 100;
				PriceHistoryModel model = new PriceHistoryModel();
				model.setStockbuydate(parseZuluToPst(ele.getUpdated_at()));
				model.setCurrentprice(lastprice);
				model.setLastprice(ele.getAverage_buy_price());
				model.setProfit(lossgainpercent);
				model.setSymbol(ele.getStockTicker());
				model.setTimestamp(
						new Timestamp(dateFormat.parse(dateFormat.format(Calendar.getInstance().getTime())).getTime()));
				if (!(monthDayformatter.format(model.getTimestamp())
						.equalsIgnoreCase(monthDayformatter.format(model.getStockbuydate())))) {
					model.setPrevlossgainpercent(prevlossgainpercent);
				} else {
					model.setPrevlossgainpercent(0f);
				}

				model.setLowpercentday(0f);
				model.setHighpercentday(0f);
				model.setTotalprofitloss((lossgainpercent * qty * ele.getAverage_buy_price()) / 100);

				if (model.getSymbol() != null && !model.getSymbol().equalsIgnoreCase("")) {
					List<PriceHistoryModel> history = repository.findBySymbol(model.getSymbol());
					if (history != null && history.size() > 0) {
						repository.updatemodel(model.getSymbol(), model.getLastprice(), model.getCurrentprice(),
								model.getPrevlossgainpercent(), model.getTotalprofitloss(), model.getTimestamp(),
								model.getProfit());
					} else {
						repository.save(model);
					}
				}
			}

		}
	}

	@Scheduled(cron = "0/8 * 4-19 ? *  MON-FRI")
	public void checkPriceAlert() throws RobinhoodApiException, RobinhoodNotLoggedInException, IOException {
		PriceHistoryModel model = new PriceHistoryModel();
		if (RequestStatus.SUCCESS.equals(status) && pos != null && pos.size() > 0) {
			for (PositionElement ele : pos) {
				List<PriceHistoryModel> buysellpercenchange = repository
						.findBySymbolOrderByProfitDesc(ele.getStockTicker());
				Float finalgainpercent = 0f;
				Float finallosspercent = 0f;
				if (buysellpercenchange.size() > 0 && buysellpercenchange.size() > 0) {
					if (buysellpercenchange.get(0).getProfit() > buysellpercenchange.get(0).getPrevlossgainpercent()) {
						finalgainpercent = buysellpercenchange.get(0).getProfit()
								- buysellpercenchange.get(0).getPrevlossgainpercent();
					}
					if (buysellpercenchange.get(0).getProfit() < buysellpercenchange.get(0).getPrevlossgainpercent()) {
						finallosspercent = buysellpercenchange.get(0).getProfit()
								- buysellpercenchange.get(0).getPrevlossgainpercent();
					}
					Float dayperc = buysellpercenchange.get(0).getHighpercentday();
					Float dayperclow = buysellpercenchange.get(0).getLowpercentday();
					Float percentvalue = buysellpercenchange.get(0).getProfit();
					Float totalprofitloss = buysellpercenchange.get(0).getTotalprofitloss();
					if ((finalgainpercent >= dayperc + percentchangeby)) {
						String lossorgain = "GAIN";
						repository.updatehighpercentday(dayperc + percentchangeby, ele.getStockTicker());
						String mess = buildMessage(ele.getStockTicker(), lossorgain, dayperc + percentchangeby,
								buysellpercenchange.get(0).getTotalprofitloss());
						notify.sendPushNotification(getToken(tokenrepository), mess);
					} else if (finallosspercent <= dayperclow - percentchangeby) {
						String lossorgain = "LOSS";
						repository.updatelowpercentday(dayperclow - percentchangeby, ele.getStockTicker());
						String mess = buildMessage(ele.getStockTicker(), lossorgain, dayperclow - percentchangeby,
								buysellpercenchange.get(0).getTotalprofitloss());
						notify.sendPushNotification(getToken(tokenrepository), mess);

					} else if (totalprofitloss <= 1 && totalprofitloss >= -1
							&& buysellpercenchange.get(0).getNearingpercentday() == null) {
						String lossorgain = "NEARING MARGIN";
						repository.updatenearingday("Y", ele.getStockTicker());
						String mess = buildMessage(ele.getStockTicker(), lossorgain, percentvalue,
								buysellpercenchange.get(0).getTotalprofitloss());
						notify.sendPushNotification(getToken(tokenrepository), mess);

					} else if (buysellpercenchange.get(0).getPrevlossgainpercent() <= -prevpercentage
							&& percentvalue >= 0 && buysellpercenchange.get(0).getTimetosell() == null) {
						String lossorgain = "TIME TO SELL";
						repository.updatetimetosell("Y", ele.getStockTicker());
						String mess = buildMessage(ele.getStockTicker(), lossorgain, percentvalue,
								buysellpercenchange.get(0).getTotalprofitloss());
						notify.sendPushNotification(getToken(tokenrepository), mess);

					}
				}
			}
		}
	}

	@Scheduled(cron = "0 0/30 6-17 ? * MON-FRI")
	public void resetflags() throws RobinhoodApiException, RobinhoodNotLoggedInException, IOException {
		if (RequestStatus.SUCCESS.equals(status) && pos != null && pos.size() > 0) {
			for (PositionElement ele : pos) {
				repository.updatenearingday(null, ele.getStockTicker());
				repository.updatetimetosell(null, ele.getStockTicker());
			}
		}

	}

	public String buildMessage(String ele, String lossorgain, Float percentnum, Float totalprofitloss) {
		return ele + "  " + lossorgain + "  " + percentnum + " " + "% percent" + " " + "TotalGainloss: "
				+ totalprofitloss;
	}

	public String getToken(TokenRepo tokenrepository) {
		String tok = tokenrepository.findByToken().get(0);
		return tok;
	}

	/*
	 * public void readfile() throws IOException { File file = new
	 * File("C:\\Android/tndm.txt"); FileInputStream fis = null; BufferedReader
	 * reader= null; int content; try { fis = new FileInputStream(file); reader =
	 * new BufferedReader(new InputStreamReader(fis)); String line =
	 * reader.readLine(); while(line != null){ Float percentchangetodaya =
	 * ((Float.parseFloat(line) - 26.9f) / 26.9f) 100; //
	 * System.out.println(percentchangetodaya);
	 * pricelist.add(Float.parseFloat(line)); line = reader.readLine(); }
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } finally { reader.close(); fis.close();
	 * 
	 * 
	 * } }
	 */
	@Scheduled(cron = "0/8 * 4-19 ? *  MON-FRI")
	public void alertWatchlistPrice()
			throws InvalidTickerException, RobinhoodApiException, RobinhoodNotLoggedInException, IOException {
		if (RequestStatus.SUCCESS.equals(status) && poswtch != null && poswtch.size() > 0) {
			for (PositionWatch ele : poswtch) {
				TickerQuoteElement ticker = api.getQuoteByTicker(ele.getStockTicker());
				Float prevprice = ticker.getAdjusted_previous_close();
				Float lastprice = ticker.getLast_extended_hours_trade_price() == 0.0 ? ticker.getLast_trade_price()
						: ticker.getLast_extended_hours_trade_price();
				Float percentchangetoday = ((lastprice - prevprice) / prevprice) * 100;
				if (percentchangetoday <= -2.5) {
					if (valuemap.containsKey(ele.getStockTicker())) {
						if (((valuemap.get(ele.getStockTicker())) - (percentchangetoday) >= 1)) {
							valuemap.put(ele.getStockTicker(), percentchangetoday);
							String lossorgain = "WATCH ALERT";
							String mess = buildMessage(ele.getStockTicker(), lossorgain, percentchangetoday, 0f);
							notify.sendPushNotification(getToken(tokenrepository), mess);
						}
					} else {
						valuemap.put(ele.getStockTicker(), percentchangetoday);
						String lossorgain = "WATCH ALERT";
						String mess = buildMessage(ele.getStockTicker(), lossorgain, percentchangetoday, 0f);
						notify.sendPushNotification(getToken(tokenrepository), mess);
					}
				}
			}
		}

	}

	@Scheduled(cron = "0 00 06 ? * MON,TUE,WED,THU,FRI")
	public void resetwatch() {
		if(valuemap!=null) {
			valuemap.clear();
		}
	}
	
	@Scheduled(cron = "0 45 09 ? * MON,TUE,WED,THU,FRI")
	public void buyorder()
			throws InvalidTickerException, RobinhoodApiException, RobinhoodNotLoggedInException, IOException {
		if (RequestStatus.SUCCESS.equals(status) && poswtch != null && poswtch.size() > 0) {
			Document doc;
			float buyingpower = api.getAccountData().getMarginBalances().getUnallocated_margin_cash();
			int moneytobuy = 500;
			doc = Jsoup.connect(
					"https://finviz.com/screener.ashx?v=111&f=an_recom_strongbuy,cap_smallover,sh_avgvol_o200,sh_relvol_o1,ta_change_d15&ft=4&ty=l&ta=0&o=change&ar=180")
					.get();
			Elements el = doc.getElementsByClass("screener-body-table-nw");
			StringBuffer buff = new StringBuffer();
			for (Element link : el) {
				buff.append(link.text() + ":");
			}
			String[] arr = buff.toString().split(":");
			if (arr.length > 0 && buyingpower >= 500) {
				String todaysymbol = arr[1];
				// String todaysymbol = "MOMO";
				List<OrderHistoryResults> resul = api.getOrderHistory();
				List<OrderHistoryResults> li = resul.stream().filter(order -> {
					try {
						return order.getState().equalsIgnoreCase("filled")
								&& parseZuluToPst(order.getUpdatedAt()).after(new Timestamp(dateFormat
										.parse(dateFormat
												.format(DateUtils.addDays(Calendar.getInstance().getTime(), -7)))
										.getTime()))
								&& order.getStockTicker().equalsIgnoreCase(todaysymbol);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return false;
				}).collect(Collectors.toList());

				List<IntradayPriceHistoryModel> lis = (List<IntradayPriceHistoryModel>) intradayrepository.findAll();

				if (li != null && li.size() == 0 && lis != null && lis.size() == 0) {
					TickerQuoteElement ticker = api.getQuoteByTicker(todaysymbol);
					Float prevprice = ticker.getAdjusted_previous_close();
					Float lastprice = ticker.getLast_extended_hours_trade_price() == 0.0 ? ticker.getLast_trade_price()
							: ticker.getLast_extended_hours_trade_price();
					// Float percentchangetoday = ((lastprice - prevprice) / prevprice) * 100;
					Float finalprice = lastprice * .985f;
					int qty = moneytobuy / Math.round(finalprice);
					limitbuyorder(todaysymbol, round(finalprice, 2), qty);
				}
			}
		}
	}

	public void limitbuyorder(String ticker, Float price, int qty)
			throws InvalidTickerException, RobinhoodApiException, RobinhoodNotLoggedInException {
		try {
			SecurityOrderElement secel = api.makeLimitOrder(ticker, TimeInForce.GOOD_FOR_DAY, price, qty,
					OrderTransactionType.BUY);

			IntradayPriceHistoryModel model = new IntradayPriceHistoryModel();
			model.setSymbol(ticker);
			model.setCancelURL(secel.getCancel().toString());
			model.setTimestamp(
					new Timestamp(dateFormat.parse(dateFormat.format(Calendar.getInstance().getTime())).getTime()));
			if (model.getSymbol() != null && !model.getSymbol().equalsIgnoreCase("")) {
				List<IntradayPriceHistoryModel> history = intradayrepository.findBySymbol(model.getSymbol());
				if (history != null && history.size() > 0) {
					intradayrepository.updatemodel(model.getSymbol(), model.getLastprice(), model.getCurrentprice(),
							model.getPrevlossgainpercent(), model.getTotalprofitloss(), model.getTimestamp(),
							model.getProfit());
				} else {
					intradayrepository.save(model);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// @Scheduled(cron = "0 57 21 ? * MON,TUE,WED,THU,FRI")
	@Scheduled(fixedDelay = 8000)
	public void monitorlimitorder()
			throws InvalidTickerException, RobinhoodApiException, RobinhoodNotLoggedInException, ParseException {
		try {
			if (RequestStatus.SUCCESS.equals(status) && poswtch != null && poswtch.size() > 0) {
				List<IntradayPriceHistoryModel> lis = (List<IntradayPriceHistoryModel>) intradayrepository.findAll();
				if (lis != null && lis.size() > 0) {
					List<Float> avg_price = pos.stream()
							.filter(a -> a.getStockTicker().equalsIgnoreCase(lis.get(0).getSymbol()))
							.map(PositionElement::getAverage_buy_price).collect((Collectors.toList()));
					List<Float> totalqty = pos.stream()
							.filter(a -> a.getStockTicker().equalsIgnoreCase(lis.get(0).getSymbol()))
							.map(PositionElement::getQuantity).collect((Collectors.toList()));
					if (avg_price != null && avg_price.size() > 0 && avg_price.get(0) != null
							&& avg_price.get(0) != 0f) {
						TickerQuoteElement ticker = api.getQuoteByTicker(lis.get(0).getSymbol());
						Float prevprice = ticker.getAdjusted_previous_close();
						Float lastprice = ticker.getLast_extended_hours_trade_price() == 0.0
								? ticker.getLast_trade_price()
								: ticker.getLast_extended_hours_trade_price();
						Float percentchangetoday = ((lastprice - prevprice) / prevprice) * 100;
						Float lossgainpercent = ((lastprice - avg_price.get(0)) / avg_price.get(0)) * 100;
						if (lossgainpercent >= 3.25) {
							List<OrderHistoryResults> resul = api.getOrderHistory();
							List<OrderHistoryResults> li = resul.stream().filter(order -> {
								try {
									return order.getState().equalsIgnoreCase("queued")
											&& order.getSide().equalsIgnoreCase("sell")
											&& order.getStockTicker().equalsIgnoreCase(lis.get(0).getSymbol());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								return false;
							}).collect(Collectors.toList());

							if (li != null && li.size() > 0) {
								List<IntradayPriceHistoryModel> gainperct = intradayrepository
										.findBySymbol(lis.get(0).getSymbol());
								if (lossgainpercent >= gainperct.get(0).getHighpercentday() + 0.5) {
									api.cancelorder(new URL(gainperct.get(0).getCancelURL()));
									limitsellorder(lis.get(0).getSymbol(), lastprice * .99f,
											Math.round(totalqty.get(0)));
									intradayrepository.updatehighpercentday(lossgainpercent, lis.get(0).getSymbol());
								}
							} else {
								limitsellorder(lis.get(0).getSymbol(), lastprice * .99f, Math.round(totalqty.get(0)));
								intradayrepository.updatehighpercentday(lossgainpercent, lis.get(0).getSymbol());
							}
						}
					}
				}
			}
			// SecurityOrderElement secel = api.cancelorder(buycancelmap.get("NFEC"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void limitsellorder(String ticker, Float price, int qty)
			throws InvalidTickerException, RobinhoodApiException, RobinhoodNotLoggedInException {
		try {
			SecurityOrderElement secel = api.makeLimitStopOrder(ticker, TimeInForce.GOOD_FOR_DAY, round(price, 2), qty,
					OrderTransactionType.SELL, round(price, 2));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void refreshIntraPercent()
			throws InvalidTickerException, RobinhoodApiException, RobinhoodNotLoggedInException {
		try {
			List<IntradayPriceHistoryModel> lis = (List<IntradayPriceHistoryModel>) intradayrepository.findAll();
			List<Float> avg_price = pos.stream()
					.filter(a -> a.getStockTicker().equalsIgnoreCase(lis.get(0).getSymbol()))
					.map(PositionElement::getAverage_buy_price).collect((Collectors.toList()));
			TickerQuoteElement ticker = api.getQuoteByTicker(lis.get(0).getSymbol());
			Float prevprice = ticker.getAdjusted_previous_close();
			Float lastprice = ticker.getLast_extended_hours_trade_price() == 0.0 ? ticker.getLast_trade_price()
					: ticker.getLast_extended_hours_trade_price();
			Float percentchangetoday = ((lastprice - prevprice) / prevprice) * 100;
			Float lossgainpercent = ((lastprice - avg_price.get(0)) / avg_price.get(0)) * 100;
			intradayrepository.updatehighpercentday(lossgainpercent, lis.get(0).getSymbol());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// @Scheduled(cron = "0 57 21 ? * MON,TUE,WED,THU,FRI")
	public void deleteintraDB() throws InvalidTickerException, RobinhoodApiException, RobinhoodNotLoggedInException {
		try {
			List<IntradayPriceHistoryModel> intraallList = (List<IntradayPriceHistoryModel>) intradayrepository
					.findAll();
			List<String> intralistone = intraallList.stream().map(a -> a.getSymbol()).sorted()
					.collect(Collectors.toList());
			List<String> intralisttwo = pos.stream().map(k -> k.getStockTicker()).sorted().collect(Collectors.toList());
			intralistone.removeAll(intralisttwo);
			intralistone.forEach(a -> {
				intradayrepository.deletemodel(a);
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static float round(float number, int decimalPlace) {
		BigDecimal bd = new BigDecimal(number);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	public Timestamp parseZuluToPst(String datetoparse) {
		try {
			DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			java.util.Date date = utcFormat.parse(datetoparse);
			DateFormat pstFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			pstFormat.setTimeZone(TimeZone.getTimeZone("PST"));
			String str = pstFormat.format(date);
			Date parseddate = pstFormat.parse(str);
			Timestamp ts = new Timestamp(parseddate.getTime());
			return ts;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

}
