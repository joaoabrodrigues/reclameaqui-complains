package br.com.reclameaqui.complaints.repository;

import br.com.reclameaqui.complaints.ComplaintsApplication;
import br.com.reclameaqui.complaints.environment.EnvComplaint;
import br.com.reclameaqui.complaints.model.Complaint;
import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ComplaintsApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class ComplaintRepositoryTest {

    private static final String ROOT_PATH_COMPLAINT_TITLE = "$.title";
    private static final String EMBEDDED_PATH = "$._embedded";
    private static final String PATH_COMPLAINTS = "$._embedded.complaints";
    private static final String PATH_COMPLAINTS_TITLE = "$._embedded.complaints[0].title";
    private static final String PATH_COMPLAINTS_CITY = "$._embedded.complaints[0].locale.city";
    private static final String PATH_COMPLAINTS_COMPANY_NAME = "$._embedded.complaints[0].company.name";
    private static final String TICKET_COMPLAINT_TITLE = "Preciso mudar a senha!";
    private static final String TICKET_COMPLAINT_LOCALE_CITY = "Nossa Senhora do Socorro";
    private static final String TICKET_COMPLAINT_COMPANY_NAME = "Ticket Serviços";
    private static final String ITAU_COMPLAINT_TITLE = "Cobrança de tarifas";
    private static final String ITAU_COMPLAINT_LOCALE_CITY = "São Paulo";
    private static final String ITAU_COMPLAINT_COMPANY_NAME = "Banco Itaú";
    private static final String COMPANY_NAME_PROPERTY = "companyName";
    private static final String COMPLAINT_ROUTE = "/complaint";
    private static final String ROOT_PATH_COMPLAINT_LOCALE_CITY = "$.locale.city";
    private static final String ROOT_PATH_COMPLAINT_COMPANY_NAME = "$.company.name";

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private JOpenCageGeocoder jOpenCageGeocoder;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Complaint complaint = EnvComplaint.createComplaintTicketServicos();
        Complaint complaint2 = EnvComplaint.createComplaintBancoItau();
        complaintRepository.saveAll(Arrays.asList(complaint, complaint2));

        Map<String, Object> mapToConvert = new HashMap<>();
        mapToConvert.put("results", Collections.emptyList());
        JOpenCageResponse openCageResponse = new ObjectMapper().convertValue(mapToConvert, JOpenCageResponse.class);
        when(jOpenCageGeocoder.forward(ArgumentMatchers.any(JOpenCageForwardRequest.class))).thenReturn(openCageResponse);
    }

    @Test
    public void shouldFindAllDefaultCreatedObjects() throws Exception {
        mockMvc.perform(get(COMPLAINT_ROUTE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(EMBEDDED_PATH, notNullValue()))
                .andExpect(jsonPath("$.page.totalElements", is(2)))
                .andExpect(jsonPath(PATH_COMPLAINTS, hasSize(2)))
                .andExpect(jsonPath(PATH_COMPLAINTS_TITLE, is(TICKET_COMPLAINT_TITLE)))
                .andExpect(jsonPath(PATH_COMPLAINTS_CITY, is(TICKET_COMPLAINT_LOCALE_CITY)))
                .andExpect(jsonPath(PATH_COMPLAINTS_COMPANY_NAME, is(TICKET_COMPLAINT_COMPANY_NAME)))
                .andExpect(jsonPath("$._embedded.complaints[1].title", is(ITAU_COMPLAINT_TITLE)))
                .andExpect(jsonPath("$._embedded.complaints[1].locale.city", is(ITAU_COMPLAINT_LOCALE_CITY)))
                .andExpect(jsonPath("$._embedded.complaints[1].company.name", is(ITAU_COMPLAINT_COMPANY_NAME)));
    }

    @Test
    public void shouldntFindAllDefaultCreatedObjects() throws Exception {
        mockMvc.perform(get("/complaint/search/company")
                .param(COMPANY_NAME_PROPERTY, "Casas Bahia"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(EMBEDDED_PATH, notNullValue()))
                .andExpect(jsonPath(PATH_COMPLAINTS, empty()));
    }

    @Test
    public void shouldFindDefaultCreatedObjectInSearchByCompanyTicket() throws Exception {
        mockMvc.perform(get("/complaint/search/company")
                .param(COMPANY_NAME_PROPERTY, TICKET_COMPLAINT_COMPANY_NAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(EMBEDDED_PATH, notNullValue()))
                .andExpect(jsonPath(PATH_COMPLAINTS, hasSize(1)))
                .andExpect(jsonPath(PATH_COMPLAINTS_TITLE, is(TICKET_COMPLAINT_TITLE)))
                .andExpect(jsonPath(PATH_COMPLAINTS_CITY, is(TICKET_COMPLAINT_LOCALE_CITY)))
                .andExpect(jsonPath(PATH_COMPLAINTS_COMPANY_NAME, is(TICKET_COMPLAINT_COMPANY_NAME)));
    }

    @Test
    public void shouldFindDefaultCreatedObjectInSearchByCompanyItauAndCitySaoPaulo() throws Exception {
        mockMvc.perform(get("/complaint/search/company-city")
                .param(COMPANY_NAME_PROPERTY, ITAU_COMPLAINT_COMPANY_NAME)
                .param("city", ITAU_COMPLAINT_LOCALE_CITY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(EMBEDDED_PATH, notNullValue()))
                .andExpect(jsonPath(PATH_COMPLAINTS, hasSize(1)))
                .andExpect(jsonPath(PATH_COMPLAINTS_TITLE, is(ITAU_COMPLAINT_TITLE)))
                .andExpect(jsonPath(PATH_COMPLAINTS_CITY, is(ITAU_COMPLAINT_LOCALE_CITY)))
                .andExpect(jsonPath(PATH_COMPLAINTS_COMPANY_NAME, is(ITAU_COMPLAINT_COMPANY_NAME)));
    }

    @Test
    public void shouldFindDefaultCreatedObjectInSearchByCitySaoPaulo() throws Exception {
        mockMvc.perform(get("/complaint/search/city")
                .param("city", ITAU_COMPLAINT_LOCALE_CITY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(EMBEDDED_PATH, notNullValue()))
                .andExpect(jsonPath(PATH_COMPLAINTS, hasSize(1)))
                .andExpect(jsonPath(PATH_COMPLAINTS_TITLE, is(ITAU_COMPLAINT_TITLE)))
                .andExpect(jsonPath(PATH_COMPLAINTS_CITY, is(ITAU_COMPLAINT_LOCALE_CITY)))
                .andExpect(jsonPath(PATH_COMPLAINTS_COMPANY_NAME, is(ITAU_COMPLAINT_COMPANY_NAME)));
    }

    @Test
    public void shouldFindDefaultCreatedObjectInSearchByCompanyAndStateSergipe() throws Exception {
        mockMvc.perform(get("/complaint/search/company-state")
                .param("state", "Sergipe")
                .param(COMPANY_NAME_PROPERTY, TICKET_COMPLAINT_COMPANY_NAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(EMBEDDED_PATH, notNullValue()))
                .andExpect(jsonPath(PATH_COMPLAINTS, hasSize(1)))
                .andExpect(jsonPath(PATH_COMPLAINTS_TITLE, is(TICKET_COMPLAINT_TITLE)))
                .andExpect(jsonPath(PATH_COMPLAINTS_CITY, is(TICKET_COMPLAINT_LOCALE_CITY)))
                .andExpect(jsonPath(PATH_COMPLAINTS_COMPANY_NAME, is(TICKET_COMPLAINT_COMPANY_NAME)));
    }

    @Test
    public void shouldFindDefaultCreatedObjectById() throws Exception {
        Complaint complaint = complaintRepository.findByLocaleCity(TICKET_COMPLAINT_LOCALE_CITY).stream().findFirst().orElseThrow(Exception::new);

        mockMvc.perform(get(COMPLAINT_ROUTE + "/" + complaint.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(complaint.getId())))
                .andExpect(jsonPath(ROOT_PATH_COMPLAINT_TITLE, is(TICKET_COMPLAINT_TITLE)))
                .andExpect(jsonPath(ROOT_PATH_COMPLAINT_LOCALE_CITY, is(TICKET_COMPLAINT_LOCALE_CITY)))
                .andExpect(jsonPath(ROOT_PATH_COMPLAINT_COMPANY_NAME, is(TICKET_COMPLAINT_COMPANY_NAME)));
    }

    @Test
    public void shouldUpdateDefaultCreatedObject() throws Exception {
        Complaint complaint = complaintRepository.findByLocaleCity(TICKET_COMPLAINT_LOCALE_CITY).stream().findFirst().orElseThrow(Exception::new);

        complaint.setTitle("Título modificado");

        MvcResult putResult = mockMvc.perform(put(COMPLAINT_ROUTE + "/" + complaint.getId())
                .content(new ObjectMapper().writeValueAsString(complaint)))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        String id = Optional.ofNullable(putResult.getResponse().getRedirectedUrl()).map(s -> s.split("complaint/")[1]).orElseThrow(Exception::new);
        mockMvc.perform(get(COMPLAINT_ROUTE + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(complaint.getId())))
                .andExpect(jsonPath(ROOT_PATH_COMPLAINT_TITLE, is("Título modificado")))
                .andExpect(jsonPath(ROOT_PATH_COMPLAINT_LOCALE_CITY, is(TICKET_COMPLAINT_LOCALE_CITY)))
                .andExpect(jsonPath(ROOT_PATH_COMPLAINT_COMPANY_NAME, is(TICKET_COMPLAINT_COMPANY_NAME)));
    }

    @Test
    public void shouldCreateNewComplaint() throws Exception {
        Complaint complaint = EnvComplaint.createComplaintVivo();

        MvcResult postResult = mockMvc.perform(post(COMPLAINT_ROUTE)
                .content(new ObjectMapper().writeValueAsString(complaint)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String id = Optional.ofNullable(postResult.getResponse().getRedirectedUrl()).map(s -> s.split("complaint/")[1]).orElseThrow(Exception::new);
        mockMvc.perform(get(COMPLAINT_ROUTE + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath(ROOT_PATH_COMPLAINT_TITLE, is("Aumento do valor do plano")))
                .andExpect(jsonPath(ROOT_PATH_COMPLAINT_LOCALE_CITY, is("Porto Alegre")))
                .andExpect(jsonPath(ROOT_PATH_COMPLAINT_COMPANY_NAME, is("Vivo (Celular, Fixo, Internet, TV)")));
    }

    @Test
    public void shouldReturnInternalServerError() throws Exception {
        Complaint complaint = EnvComplaint.createComplaintVivo();
        complaint.setTitle(null);
        mockMvc.perform(post(COMPLAINT_ROUTE)
                .content(new ObjectMapper().writeValueAsString(complaint)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Title must be informed")))
                .andReturn();
    }
}