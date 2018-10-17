package com.recruitment.simplerecruitment.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

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

import com.recruitment.simplerecruitment.controller.OfferController;
import com.recruitment.simplerecruitment.domain.Offer;
import com.recruitment.simplerecruitment.exception.ConfictException;
import com.recruitment.simplerecruitment.service.OfferService;

@RunWith(SpringRunner.class)
@ActiveProfiles(value="test")
@WebMvcTest(OfferController.class)
public class OfferControllerTest {

	@MockBean
	private OfferService offerService;
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	private String uri;
	
	private Long id;
	
	private String jobTitle;
	
	private void createOffer(){
        try {
            uri = "/rest/offers/new";
            final Offer offer = new Offer();
            offer.setJobTitle(jobTitle);
            final String requestBody = Jackson2ObjectMapperBuilder.json().build().writerWithDefaultPrettyPrinter().writeValueAsString(offer);
            when(offerService.create(any(Offer.class))).thenReturn(new Offer());
            mockMvc.perform(post(uri)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                            .andExpect(MockMvcResultMatchers.status().isCreated());
        } catch(Exception exception){
            fail(exception.getMessage());
        }
	}
	
	@Before
	public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc  = MockMvcBuilders
                     .webAppContextSetup(context)
                     .build();
        id = 1L;
        jobTitle = "Java developer back-end";
	}
	
    @Test
    public void shouldCreateAJobOffer(){
    	createOffer();
    }
    
    @Test
    public void shouldNotCreateAJobOfferDueMissJobTitle(){
        try {
            uri = "/rest/offers/new";
            final Offer offer = new Offer();
            offer.setJobTitle(null);
            final String requestBody = Jackson2ObjectMapperBuilder.json().build().writerWithDefaultPrettyPrinter().writeValueAsString(offer);
            when(offerService.create(any(Offer.class))).thenReturn(new Offer());
            mockMvc.perform(post(uri)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        } catch(Exception exception){
            fail(exception.getMessage());
        }
    }
    
    @Test
    public void shouldNotCreateAJobOfferDueEmptyJobTitle(){
        try {
            uri = "/rest/offers/new";
            final Offer offer = new Offer();
            offer.setJobTitle("");
            final String requestBody = Jackson2ObjectMapperBuilder.json().build().writerWithDefaultPrettyPrinter().writeValueAsString(offer);
            when(offerService.create(any(Offer.class))).thenReturn(new Offer());
            mockMvc.perform(post(uri)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        } catch(Exception exception){
            fail(exception.getMessage());
        }
    }

    @Test
    public void shouldNotCreateAJobOfferWithAlreadyExistTitle(){
    	createOffer();
    	try {
            uri = "/rest/offers/new";
            final Offer offer = new Offer();
            offer.setJobTitle(jobTitle);
            final String requestBody = Jackson2ObjectMapperBuilder.json().build().writerWithDefaultPrettyPrinter().writeValueAsString(offer);
            when(offerService.create(any(Offer.class))).thenThrow(new ConfictException("Job title alredy exists !"));
            mockMvc.perform(post(uri)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                            .andExpect(MockMvcResultMatchers.status().isConflict());
            
        } catch(Exception exception){
            fail(exception.getMessage());
        }
    }
    
    @Test
    public void shouldFindJobOfferById(){
    	createOffer();
    	try {
        	uri = "/rest/offers/" + id;
            Offer off = new Offer();
            off.setId(id);
            off.setJobTitle(jobTitle);
            when(offerService.find(id)).thenReturn(off);
            mockMvc.perform(get(uri)
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(MockMvcResultMatchers.status().isOk())
                            .andExpect(jsonPath("$.jobTitle").value(equalTo(jobTitle)));
            
        } catch(Exception exception){
            fail(exception.getMessage());
        }
    }

    @Test
    public void shouldNotFindJobOfferDueNegativeId(){
        try {
            uri = "/rest/offers/-1" ;
            when(offerService.find(-1L)).thenThrow(new EntityNotFoundException("Could not find entity with id ."));
            mockMvc.perform(get(uri)
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        } catch(Exception exception){
            fail(exception.getMessage());
        }
    }
    
    @Test
    public void shouldNotFindJobOfferDueMissingId(){
        try {
            uri = "/rest/offers/ " ;
            when(offerService.find(null)).thenThrow(new EntityNotFoundException("Could not find entity with id ."));
            mockMvc.perform(get(uri)
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        } catch(Exception exception){
            fail(exception.getMessage());
        }
    }
    
    @Test
    public void shouldBringAllOffer(){
    	createOffer();
        try {
            uri = "/rest/offers/all";
            when(offerService.findAll()).thenReturn(Arrays.asList(new Offer()).stream().collect(Collectors.toList()));
            mockMvc.perform(get(uri)
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(MockMvcResultMatchers.status().isOk())
                            .andExpect(jsonPath("$.length()").value(equalTo(1)));
        } catch(Exception exception){
            fail(exception.getMessage());
        }
    }

}
