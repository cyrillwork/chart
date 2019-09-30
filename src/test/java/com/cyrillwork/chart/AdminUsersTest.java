package com.cyrillwork.chart;

import com.cyrillwork.chart.controllers.AdminUsersController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@WithUserDetails("admin")
@Sql(value = {"/create-users-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-users-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AdminUsersTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminUsersController controller;

    @Test
    public void mainPageTest() throws Exception {
        this.mockMvc.perform(get("/admin_users"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id=\"login_user\"]").string("admin"));
    }

    @Test
    public void userListTest() throws Exception {
        this.mockMvc.perform(get("/admin_users"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id=\"users-table\"]").nodeCount(3));
    }

}
