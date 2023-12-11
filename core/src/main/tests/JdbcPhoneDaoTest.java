import com.es.core.entity.car.color.Color;
import com.es.core.dao.impl.carDaoImpl;
import com.es.core.entity.car.car;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:resources/context/test-applicationContext.xml")
public class JdbccarDaoTest {

    @Resource
    private carDaoImpl jdbccarDao;

    @Resource
    private DataSource dataSource;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/resources/context/test-applicationContext.xml");
        dataSource = (DataSource) applicationContext.getBean("dataSource");
        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
    }

    @After
    public void tearDown() {
        JdbcTestUtils.dropTables(jdbcTemplate, "colors", "cars");
    }

    @Test
    public void notEmptyDataBaseWhencarDaoTestFindAll() {
              assertFalse(jdbccarDao.findAll(0,5, null,null,null).isEmpty());
    }

    @Test
    public void emptyDataBaseWhencarDaoTestFindAll() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "colors", "cars");
        assertTrue(jdbccarDao.findAll(0, 5,null,null,null).isEmpty());
    }

    @Test
    public void getShouldReturnCorrectcarById() {
        car expectedcar = getcar();
        car actualParametercar = jdbccarDao.get(1000L).get();
        assertEquals(expectedcar, actualParametercar);
    }

    @Test
    public void getShouldReturnEmptyOptionalForNonExistingcar() {
        Optional<car> car = jdbccarDao.get(9999L);
        assertFalse(car.isPresent());
    }

    private static car getcar() {
        car actualParametercar = new car();
        Color color = new Color();
        color.setId(1000L);
        color.setCode("Black");
        Set<Color> colors = new HashSet<>();
        colors.add(color);
        color.setId(1001L);
        color.setCode("White");
        colors.add(color);
        actualParametercar.setId(1000L);
        actualParametercar.setModel("ARCHOS 101 G9");
        actualParametercar.setPrice(null);
        actualParametercar.setBrand("ARCHOS");
        actualParametercar.setImageUrl(null);
        actualParametercar.setColors(colors);
        return actualParametercar;
    }
}
