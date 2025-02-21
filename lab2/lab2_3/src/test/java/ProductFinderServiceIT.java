import org.example.HttpClient;
import org.example.ISimpleHttpClient;
import org.example.ProductFinderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductFinderServiceIT {
    private HttpClient httpClient;
    private ProductFinderService service;

    @BeforeEach
    void setUp() {
        httpClient = new HttpClient();
        service = new ProductFinderService(httpClient);
    }

    @Test
    void findProductDetailsValidId() throws Exception {

        Optional<String> response = service.findProductDetails(3);
        String expectedResponse = "{\"id\":3,\"title\":\"Mens Cotton Jacket\",\"price\":55.99,\"description\":\"great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.\",\"category\":\"men's clothing\",\"image\":\"https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg\",\"rating\":{\"rate\":4.7,\"count\":500}}";


        assertTrue(response.isPresent());
        assertEquals(expectedResponse, response.get());
    }

    @Test
    void findProductDetailsInvalidId() {
        Optional<String> response = service.findProductDetails(300);

        assertTrue(response.isPresent());
        assertEquals("", response.get());
    }
}
