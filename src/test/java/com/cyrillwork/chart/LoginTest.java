package com.cyrillwork.chart;


import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-users-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-users-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LoginTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testShowLogin() throws Exception{
        this.mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(CoreMatchers.containsString("Вход в систему")));
    }

    @Test
    public void testDirectLogin() throws Exception{
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void testInputLogin() throws  Exception{
        this.mockMvc.perform((SecurityMockMvcRequestBuilders.formLogin().user("admin").password("12345")))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

}
