package computer.shop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

@WithMockUser(username="admin",roles={"USER","ADMIN"})
@ActiveProfiles("test")
@SpringBootTest
class ShopApplicationTests {

	@Test
	void contextLoads() {
	}

}
