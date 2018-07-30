package senthil.jayaraman;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IntradayPriceHistoryRepo extends CrudRepository<IntradayPriceHistoryModel, Long>{
    List<IntradayPriceHistoryModel> findBySymbol(String symbol);
    List<IntradayPriceHistoryModel> findBySymbolOrderByTimestamp(String symbol);
    List<IntradayPriceHistoryModel> findBySymbolOrderByCurrentpriceDesc(String symbol);
    List<IntradayPriceHistoryModel> findBySymbolOrderByProfitDesc(String symbol);
    List<IntradayPriceHistoryModel> findBySymbolOrderByProfitAsc(String symbol);
    
    @Transactional
    @Modifying
    @Query("UPDATE INTRADAY_PRICE_HISTORY P SET P.lowpercentday = :lowpercent WHERE P.symbol = :symbol")
    public void updatelowpercentday(@Param("lowpercent") Float lowpercent, @Param("symbol") String symbol);
    
    @Transactional
    @Modifying
    @Query("UPDATE INTRADAY_PRICE_HISTORY K SET K.highpercentday = :highpercent WHERE K.symbol = :symbol")
    public void updatehighpercentday(@Param("highpercent") Float highpercent, @Param("symbol") String symbol);
    
    @Transactional
    @Modifying
    @Query("UPDATE INTRADAY_PRICE_HISTORY K SET K.cancelurl =:flag WHERE K.symbol = :symbol")
    public void updatenearingday(@Param("flag") String flag, @Param("symbol") String symbol);
    
    
    @Transactional
    @Modifying
    @Query("UPDATE INTRADAY_PRICE_HISTORY K SET K.timetosell =:flag WHERE K.symbol = :symbol")
    public void updatetimetosell(@Param("flag") String flag, @Param("symbol") String symbol);
    
    
    @Transactional
    @Modifying
    @Query("UPDATE INTRADAY_PRICE_HISTORY K SET K.symbol = :symbol,K.lastprice = :lastprice,K.currentprice = :currentprice,K.timestamp = :timestamp,K.prevlossgainpercent = :prevlossgainpercent,K.profit = :profit,K.totalprofitloss = :totalprofitloss WHERE K.symbol = :symbol")
    public void updatemodel(@Param("symbol") String symbol, @Param("lastprice") Float lastprice,@Param("currentprice") Float currentprice,@Param("prevlossgainpercent") Float prevlossgainpercent,@Param("totalprofitloss") Float totalprofitloss, @Param("timestamp") Timestamp timestamp, @Param("profit") Float profit);
   
    
    @Transactional
    @Modifying
    @Query("DELETE INTRADAY_PRICE_HISTORY K WHERE K.symbol = :symbol")
    public void deletemodel(@Param("symbol") String symbol);
   
} 