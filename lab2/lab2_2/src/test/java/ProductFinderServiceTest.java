import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.ISimpleHttpClient;
import org.example.ProductFinderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

public class ProductFinderServiceTest {
    @Mock
    private ISimpleHttpClient httpClient;
    private ProductFinderService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new ProductFinderService(httpClient);
    }

    @Test
    void findProductDetailsValidId() throws Exception {

        String jsonResponse = "{\"id\":3,\"title\":\"Mens Cotton Jacket\",\"price\":55.99,\"description\":\"Great jacket\",\"image\":\"url\",\"category\":\"men's clothing\"}";
        when(httpClient.doHttpGet("https://fakestoreapi.com/products/3")).thenReturn(jsonResponse);

        Optional<String> response = service.findProductDetails(3);

        assertTrue(response.isPresent());
        assertEquals(jsonResponse, response.get());
    }

    @Test
    void findProductDetailsInvalidId() {
        when(httpClient.doHttpGet("https://fakestoreapi.com/products/300")).thenReturn(null);

        Optional<String> response = service.findProductDetails(300);

        assertFalse(response.isPresent());
    }
}
