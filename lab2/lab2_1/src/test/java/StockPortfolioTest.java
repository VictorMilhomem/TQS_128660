import org.example.IStockmarketService;
import org.example.Stock;
import org.example.StocksPortfolio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockPortfolioTest {

    @Mock
    IStockmarketService market;

    @InjectMocks
    StocksPortfolio portfolio;

    @Test
    void testTotalValue() {

        when(market.lookUpPrice("XPTO")).thenReturn(20.0);
        when(market.lookUpPrice("WHAT")).thenReturn(15.0);

        portfolio.addStock(new Stock("XPTO", 2));
        portfolio.addStock(new Stock("WHAT", 4));

        double result = portfolio.totalValue();

        assertEquals(100.0, result);
        verify(market, times(2)).lookUpPrice(anyString());
    }

    @Test
    void testMVStocks() {

        when(market.lookUpPrice("XPTO")).thenReturn(20.0);
        when(market.lookUpPrice("WHAT")).thenReturn(15.0);
        when(market.lookUpPrice("GOOGL")).thenReturn(30.0);

        portfolio.addStock(new Stock("XPTO", 2));
        portfolio.addStock(new Stock("WHAT", 4));
        portfolio.addStock(new Stock("GOOGL", 5));
        int n = 2;
        List<Stock> result = portfolio.mostValuableStocks(n);
        List<String> labels = new ArrayList<>();
        for (Stock stk : result) {
            labels.add(stk.getLabel());
        }
        assertThat(
                labels,
                contains("GOOGL", "WHAT")
        );

        verify(market, times(4)).lookUpPrice(anyString());
    }
}
