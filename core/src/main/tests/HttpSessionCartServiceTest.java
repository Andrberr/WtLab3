import com.es.core.entity.cart.Cart;
import com.es.core.entity.cart.CartItem;
import com.es.core.service.impl.HttpSessionCartService;
import com.es.core.entity.car.car;
import com.es.core.dao.carDao;
import com.es.core.dao.StockDao;
import com.es.core.entity.order.OutOfStockException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class HttpSessionCartServiceTest {
    @InjectMocks
    private HttpSessionCartService cartService;

    @Mock
    private carDao carDao;

    @Mock
    private StockDao stockDao;

    private HttpSession httpSession;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        httpSession = new MockHttpSession();
        cartService.setHttpSession(httpSession);
    }

    @Test
    public void testAddcarWithNewCartItem() throws OutOfStockException {
        Long carId = 1L;
        Long quantity = 2L;
        Cart cart = cartService.getCart();
        car car = new car();
        when(carDao.get(carId)).thenReturn(Optional.of(car));
        when(stockDao.availableStock(carId)).thenReturn(Math.toIntExact(quantity));

        cartService.addcar(carId, quantity);

        assertEquals(1, cart.getItems().size());
        assertEquals(car, cart.getItems().get(0).getcar());
        assertEquals(quantity, cart.getItems().get(0).getQuantity());
    }

    @Test
    public void testAddcarWithExistingCartItem() throws OutOfStockException {
        Long carId = 1L;
        Long quantity = 2L;
        Cart cart = cartService.getCart();
        car car = new car(1L, null, null, null, null);
        when(carDao.get(carId)).thenReturn(Optional.of(car));
        when(stockDao.availableStock(carId)).thenReturn(5);

        cartService.addcar(carId, quantity);
        cartService.addcar(carId, quantity);

        assertEquals(1, cart.getItems().size());
        assertEquals(car, cart.getItems().get(0).getcar());
        assertEquals(Optional.of(quantity * 2), Optional.ofNullable(cart.getItems().get(0).getQuantity()));
    }

    @Test
    public void testAddcarOutOfStock() {
        Long carId = 1L;
        Long quantity = 10L;
        Cart cart = cartService.getCart();
        when(stockDao.availableStock(carId)).thenReturn(5);

        assertThrows(OutOfStockException.class, () -> cartService.addcar(carId, quantity));

        assertEquals(0, cart.getItems().size());
    }

    @Test
    public void testUpdateCart() throws OutOfStockException {
        Long carId = 1L;
        Long newQuantity = 3L;
        Cart cart = cartService.getCart();
        CartItem cartItem = new CartItem();
        cartItem.setcar(new car(1L, null, null, null, null));
        cartItem.setQuantity(1L);
        cart.getItems().add(cartItem);
        when(stockDao.availableStock(carId)).thenReturn(Math.toIntExact(newQuantity));

        cartService.update(carId, newQuantity);

        assertEquals(1, cart.getItems().size());
        assertEquals(newQuantity, cart.getItems().get(0).getQuantity());
    }

    @Test
    public void testUpdateCartOutOfStock() {
        Long carId = 1L;
        Long newQuantity = 10L;
        Cart cart = cartService.getCart();
        CartItem cartItem = new CartItem();
        cartItem.setcar(new car(1L, null, null, null, null));
        cartItem.setQuantity(1L);
        cart.getItems().add(cartItem);
        when(stockDao.availableStock(carId)).thenReturn(5);

        assertThrows(OutOfStockException.class, () -> cartService.update(carId, newQuantity));

        assertEquals(1, cart.getItems().size());
        assertEquals(Optional.of(1L), Optional.of(cart.getItems().get(0).getQuantity()));
    }

    @Test
    public void testRemoveCartItem() {
        Long carId = 1L;
        Cart cart = cartService.getCart();
        CartItem cartItem = new CartItem();
        cartItem.setcar(new car(1L, null, null, null, null));
        cartItem.setQuantity(1L);
        cart.getItems().add(cartItem);

        cartService.remove(carId);

        assertEquals(0, cart.getItems().size());
    }

    @Test
    public void testGetTotalQuantity() {
        Cart cart = cartService.getCart();
        CartItem cartItem1 = new CartItem();
        cartItem1.setQuantity(2L);
        car car1 = new car();
        car1.setPrice(BigDecimal.valueOf(100));
        cartItem1.setcar(car1);

        CartItem cartItem2 = new CartItem();
        cartItem2.setQuantity(3L);
        car car2 = new car();
        car2.setPrice(BigDecimal.valueOf(50));
        cartItem2.setcar(car2);

        cart.getItems().addAll(Arrays.asList(cartItem1, cartItem2));
        cartService.recalculateCart(cart);
        Long totalQuantity = cartService.getTotalQuantity();

        assertEquals(Optional.of(5L), Optional.of(totalQuantity));
    }

    @Test
    public void testGetTotalCost() {
        Cart cart = cartService.getCart();
        CartItem cartItem1 = new CartItem();
        cartItem1.setQuantity(2L);
        car car1 = new car();
        car1.setPrice(BigDecimal.valueOf(100));
        cartItem1.setcar(car1);

        CartItem cartItem2 = new CartItem();
        cartItem2.setQuantity(3L);
        car car2 = new car();
        car2.setPrice(BigDecimal.valueOf(50));
        cartItem2.setcar(car2);

        cart.getItems().addAll(Arrays.asList(cartItem1, cartItem2));
        cartService.recalculateCart(cart);
        BigDecimal totalCost = cartService.getTotalCost();

        assertEquals(BigDecimal.valueOf(350), totalCost);
    }

    @Test
    public void testRecalculateCartWithNullcarPrice() {
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(2L);
        car car = new car();
        car.setPrice(null);
        cartItem.setcar(car);
        cart.getItems().add(cartItem);

        cartService.recalculateCart(cart);

        assertEquals(2L, cart.getTotalQuantity());
        assertEquals(BigDecimal.ZERO, cart.getTotalCost());
    }

    @Test
    public void testRecalculateCartWithMixedItems() {
        Cart cart = new Cart();
        CartItem cartItem1 = new CartItem();
        cartItem1.setQuantity(2L);
        car car1 = new car();
        car1.setPrice(BigDecimal.valueOf(100));
        cartItem1.setcar(car1);

        CartItem cartItem2 = new CartItem();
        cartItem2.setQuantity(3L);
        car car2 = new car();
        car2.setPrice(BigDecimal.valueOf(50));
        cartItem2.setcar(car2);

        cart.getItems().addAll(Arrays.asList(cartItem1, cartItem2));

        cartService.recalculateCart(cart);

        assertEquals(5L, cart.getTotalQuantity());
        assertEquals(BigDecimal.valueOf(350), cart.getTotalCost());
    }

}
