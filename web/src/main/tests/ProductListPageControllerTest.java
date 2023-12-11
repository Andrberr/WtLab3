import com.es.core.service.CartService;
import com.es.core.entity.car.sort.SortField;
import com.es.core.entity.car.sort.SortOrder;
import com.es.core.entity.car.car;
import com.es.core.dao.carDao;
import com.es.carshop.web.controller.pages.ProductListPageController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ProductListPageControllerTest {

    @Mock
    private carDao carDao;

    @Mock
    private CartService cartService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductListPageController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShowProductList() {
        // Mock data
        List<car> cars = new ArrayList<>();
        cars.add(new car()); // Add sample car objects
        when(carDao.findAll(anyInt(), anyInt(), any(), any(), any())).thenReturn(cars);
        when(carDao.numberByQuery(any())).thenReturn(cars.size() * 2L);

        String viewName = controller.showProductList(1, "model", "asc", "query", model);

        verify(carDao).findAll(0, 10, SortField.MODEL, SortOrder.ASC, "query");
        verify(carDao).numberByQuery("query");
        verify(model).addAttribute("cars", cars);
        verify(model).addAttribute("numberOfcars", cars.size() * 2L);
        verify(model).addAttribute("numberOfPages", 1L);
        assertEquals("productList", viewName);
    }

    @Test
    public void testShowProductListWithNullParams() {
        List<car> cars = new ArrayList<>();
        cars.add(new car());
        when(carDao.findAll(anyInt(), anyInt(), any(), any(), any())).thenReturn(cars);
        when(carDao.numberByQuery(any())).thenReturn(cars.size() * 2L);

        String viewName = controller.showProductList(null, null, null, null, model);

        verify(carDao).findAll(0, 10, null, null, null);
        verify(carDao).numberByQuery(null);
        verify(model).addAttribute("cars", cars);
        verify(model).addAttribute("numberOfcars", cars.size() * 2L);
        verify(model).addAttribute("numberOfPages", 1L);
        assertEquals("productList", viewName);
    }
}
