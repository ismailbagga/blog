package com.ismail.personalblogpost;


import com.ismail.personalblogpost.auth.JwToken;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RestController;

//@WebMvcTest(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION , classes = RestController.class))
//@Import({JwToken.class,})
//@ExtendWith(MockitoExtension.class)
//public class AuthorizationFilter {
//    MockMvc mockMvc ;
//}
