import com.es.core.entity.cart.Cart;
import com.es.core.service.CartService;
import com.es.core.entity.car.car;
import com.es.core.dao.carDao;
import com.es.carshop.web.controller.pages.ProductDetailsPageController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductDetailsPageControllerTest {
    @InjectMocks
    private ProductDetailsPageController productDetailsPageController;

    @Mock
    private carDao carDao;

    @Mock
    private CartService cartService;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShowProductWithValidcarId() {
        Long carId = 1L;
        car car = new car();
        when(carDao.get(carId)).thenReturn(Optional.of(car));

        String viewName = productDetailsPageController.showProduct(carId, model);

        assertEquals("productPage", viewName);
        verify(model).addAttribute("car", car);
    }

    @Test
    public void testShowProductWithInvalidcarId() {
        Long carId = 1L;
        when(carDao.get(carId)).thenReturn(Optional.empty());

        String viewName = productDetailsPageController.showProduct(carId, model);

        assertEquals("productPage", viewName);
        verify(model).addAttribute("car", null);
    }

    @Test
    public void testCartOnPage() {
        Cart cart = new Cart();
        when(cartService.getCart()).thenReturn(cart);

        Cart result = productDetailsPageController.cartOnPage();

        assertEquals(cart, result);
    }
}