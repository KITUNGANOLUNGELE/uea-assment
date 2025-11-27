package cd.ac.uea.assessment;

import cd.ac.uea.assessment.catalog.Category;
import cd.ac.uea.assessment.catalog.CategoryRepository;
import cd.ac.uea.assessment.catalog.CategoryService;
import cd.ac.uea.assessment.catalog.Product;
import cd.ac.uea.assessment.order.InventoryClient;
import cd.ac.uea.assessment.order.OrderService;
import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@Testcontainers
@Transactional
class AssessmentTests {

    @BeforeAll
    static void loadEnv() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
    }

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName(System.getenv().getOrDefault("POSTGRES_DB", "mydb"))
            .withUsername(System.getenv().getOrDefault("POSTGRES_USER", "postgres"))
            .withPassword(System.getenv().getOrDefault("POSTGRES_PASSWORD", "secret"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.properties.hibernate.session_factory.statement_inspector",
                () -> QueryCountInspector.class.getName());
    }

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderService orderService;

    @MockitoBean
    private InventoryClient inventoryClient;

    @Test
    @DisplayName("✅ Test 1: Database Connection Works")
    void testDockerConnection() {
        assertTrue(postgres.isRunning());
    }

    @Test
    @DisplayName("❌ Test 2: Docker Configuration - App Can Connect to Database in Production")
    void testDockerConfigurationIsCorrect() {
        String expectedPassword = System.getenv().getOrDefault("POSTGRES_PASSWORD", "secret");

        String appConfiguredUrl = System.getenv().getOrDefault("SPRING_DATASOURCE_URL",
                "jdbc:postgresql://localhost:5432/mydb");
        String appConfiguredPassword = System.getenv().getOrDefault("SPRING_DATASOURCE_PASSWORD",
                "password123");

        assertFalse(appConfiguredUrl.contains("localhost"),
                "Docker services cannot use 'localhost' - check docker-compose.yml");
        assertEquals(expectedPassword, appConfiguredPassword,
                "Database password mismatch - check docker-compose.yml");
    }

    @Test
    @DisplayName("❌ Test 3: Category Page Loads Fast (Database Queries Are Optimized)")
    void testCategoryPerformance() {
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Category c = new Category();
            c.setName("Cat " + i);
            List<Product> products = new ArrayList<>();
            Product p = new Product();
            p.setName("Prod " + i);
            p.setCategory(c);
            products.add(p);
            c.setProducts(products);
            categories.add(c);
        }
        categoryRepository.saveAll(categories);

        QueryCountInspector.reset();
        categoryService.getAllCategories();

        int queryCount = QueryCountInspector.getCount();
        assertTrue(queryCount <= 2, "Performance issue detected: " + queryCount + " queries executed. Expected: <= 2");
    }

    @Test
    @DisplayName("❌ Test 4: Out-of-Stock Products Cannot Be Ordered")
    void testOrderStockCheck() {
        when(inventoryClient.checkStock(anyLong())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            orderService.placeOrder(1L, 1L);
        }, "Expected exception when placing order for out-of-stock product");
    }

    public static class QueryCountInspector implements StatementInspector {
        private static final AtomicInteger count = new AtomicInteger(0);

        @Override
        public String inspect(String sql) {
            count.incrementAndGet();
            return sql;
        }

        public static int getCount() {
            return count.get();
        }

        public static void reset() {
            count.set(0);
        }
    }
}
