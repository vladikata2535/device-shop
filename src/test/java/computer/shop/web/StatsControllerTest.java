package computer.shop.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class StatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @Test
    void testIfStatsWorks() throws Exception {
        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(view().name("stats"))
                .andExpect(model().attributeExists("stats"));
    }

}