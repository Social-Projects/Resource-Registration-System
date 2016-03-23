package org.registrator.community.controller;


import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.registrator.community.config.LoggingConfig;
import org.registrator.community.config.root.SpringRootConfig;
import org.registrator.community.config.root.TestingConfiguration;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("testing")
@ContextConfiguration(classes={TestingConfiguration.class, LoggingConfig.class,SpringRootConfig.class})
public class RegisterControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(new RegisterController())
                .setViewResolvers(viewResolver)
                .build();
    }

    @IfProfileValue(name="test-groups", values={"unit-tests"})
    @Test
    public void shouldShowRegistrationPage_Test() throws Exception {
        mockMvc.perform(get("/register_new"))
                .andExpect(status().isOk())
                .andExpect(view().name("register_new"));
    }

//    @Test
//    public void RegisterNewUserPostData_Test(){
//        mockMvc.perform(post("/register"))
//                .andExpect(model().attribute(
//    }

    @IfProfileValue(name="test-groups", values={"unit-tests"})
    @Test
    public void shouldShowLoginPage_Test() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @IfProfileValue(name="test-groups", values={"unit-tests"})
    @Test
    public void shouldShowFAQPage_Test() throws Exception {
        mockMvc.perform(get("/faq"))
                .andExpect(status().isOk())
                .andExpect(view().name("faq"));
    }

    @IfProfileValue(name="test-groups", values={"unit-tests"})
    @Test
    public void shouldShowHelpPage_Test() throws Exception {
        mockMvc.perform(get("/help"))
                .andExpect(status().isOk())
                .andExpect(view().name("help"));
    }


}
