package com.recruitment.simplerecruitment.controller;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.recruitment.simplerecruitment.controller.ApplicationController;
import com.recruitment.simplerecruitment.controller.OfferController;
import com.recruitment.simplerecruitment.domain.Application;
import com.recruitment.simplerecruitment.domain.Offer;
import com.recruitment.simplerecruitment.exception.ConstraintsViolationException;
import com.recruitment.simplerecruitment.service.ApplicationService;
import com.recruitment.simplerecruitment.service.OfferService;


@RunWith(SpringRunner.class)
@ActiveProfiles(value="test")
@WebMvcTest(controllers = {ApplicationController.class, OfferController.class})
public class ApplicationControllerTest {

	@MockBean
	private OfferService offerService;
	
	@MockBean
	private ApplicationService applicationService;
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	private String uri;
	
	private Long id;
	
	private Jackson2ObjectMapperBuilder builder;
	
	private String email;
	
	private String resume;
	
	private String jobTitle;
	
	private Offer offer;
	
	public ApplicationControllerTest(){
		builder = new Jackson2ObjectMapperBuilder();
		builder.serializationInclusion(JsonInclude.Include.NON_NULL);
		this.email = "firstName.lastName@emailprovide";  
		this.resume  = "resume";
		this.jobTitle = "Java developer back-end";
	}
	
	
	@Before
	public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc  = MockMvcBuilders
                     .webAppContextSetup(context)
                     .build();
        id = 1L;
        this.offer = addAnOffer();
	}
	
	private Offer addAnOffer(){
		Offer addedOffer = null;
        try {
        	String uriOfferAdd = "/rest/offers/new";
            final Offer offer = new Offer();
            offer.setJobTitle(jobTitle);
            final String requestBody = builder.build().writerWithDefaultPrettyPrinter().writeValueAsString(offer);
            when(offerService.create(any(Offer.class))).thenReturn(new Offer());
            mockMvc.perform(post(uriOfferAdd)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                            .andExpect(MockMvcResultMatchers.status().isCreated());
            String uriSearch = "/rest/offers/" + 1;
            Offer off = new Offer();
            off.setId(id);
            off.setJobTitle(jobTitle);
            when(offerService.find(id)).thenReturn(off);
            String body =  mockMvc.perform(get(uriSearch)
		                            .contentType(MediaType.APPLICATION_JSON))
		                            .andReturn()
		                            .getResponse()
		                            .getContentAsString();
            addedOffer = builder.build().readerFor(Offer.class).readValue(body);                           
        } catch(Exception exception){
            fail(exception.getMessage());
        }
        return addedOffer;
	}
	
	@Test
	public void shouldCreateAnApplication(){
        try {
            uri = "/rest/applications/new";
            final Application app = new Application(offer, email, resume);
            final String requestBody = builder.build().writerWithDefaultPrettyPrinter().writeValueAsString(app);
            when(applicationService.create(any(Application.class))).thenReturn(new Application());
            mockMvc.perform(post(uri)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                            .andExpect(MockMvcResultMatchers.status().isCreated());
        } catch(Exception exception){
            fail(exception.getMessage());
        }	
	}
	
	@Test
	public void shouldNotCreateAnApplicationDueNullData(){
        try {
            uri = "/rest/applications/new";
            when(applicationService.create(null)).thenThrow(new ConstraintsViolationException("Missing Application instance"));
            mockMvc.perform(post(uri)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(""))
                            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        } catch(Exception exception){
            fail(exception.getMessage());
        }	
	}
	
	@Test
	public void shouldNotCreateAnApplicationDueNullEmail(){
        try {
            uri = "/rest/applications/new";
            email = null;
            final Application app = new Application(offer, email, resume);
            final String requestBody = builder.build().writerWithDefaultPrettyPrinter().writeValueAsString(app);
            when(applicationService.create(any(Application.class))).thenReturn(new Application());
            mockMvc.perform(post(uri)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        } catch(Exception exception){
            fail(exception.getMessage());
        }	
	}
	
	@Test
	public void shouldNotCreateAnApplicationDueNullResume(){
        try {
            uri = "/rest/applications/new";
            resume  = null;
            final Application app = new Application(offer, email, resume);
            final String requestBody = builder.build().writerWithDefaultPrettyPrinter().writeValueAsString(app);
            when(applicationService.create(any(Application.class))).thenReturn(new Application());
            mockMvc.perform(post(uri)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        } catch(Exception exception){
            fail(exception.getMessage());
        }	
	}
	
	@Test
	public void shouldNotCreateAnApplicationDueNullOffer(){
        try {
            uri = "/rest/applications/new";
            final Application app = new Application(null, email, resume);
            final String requestBody = builder.build().writerWithDefaultPrettyPrinter().writeValueAsString(app);
            when(applicationService.create(any(Application.class))).thenReturn(new Application());
            mockMvc.perform(post(uri)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        } catch(Exception exception){
            fail(exception.getMessage());
        }	
	}

}
