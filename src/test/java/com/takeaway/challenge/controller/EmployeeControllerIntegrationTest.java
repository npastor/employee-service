package com.takeaway.challenge.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.takeaway.challenge.dto.EmployeeDto;
import com.takeaway.challenge.exception.ErrorMessageDto;
import com.takeaway.challenge.service.EmployeeService;

@WebMvcTest(value = EmployeeController.class)
public class EmployeeControllerIntegrationTest {

    private static final String BASE_PATH = "/v1/employees";

    private final com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @BeforeEach
    void setup() {
        mapper.registerModule(new JSR310Module());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    void test_getEmployees_ok() throws Exception {

        List<EmployeeDto> expectedEmployees = new ArrayList<EmployeeDto>();
        expectedEmployees.add(new EmployeeDto().id(1)
                                               .firstName("abc")
                                               .lastName("xyz")
                                               .birthDate(LocalDate.of(1990, 12, 21))
                                               .email("xyz@gmail.coms"));
        Mockito.when(employeeService.getEmployees())
               .thenReturn(expectedEmployees);
        String contentBody = mockMvc
                                    .perform(MockMvcRequestBuilders.get(BASE_PATH)
                                                                   .accept(MediaType.APPLICATION_JSON)
                                                                   .contentType(MediaType.APPLICATION_JSON))
                                    .andExpect(MockMvcResultMatchers.status().isOk())
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();
        Assertions.assertFalse(StringUtils.isEmpty(contentBody));
        List<EmployeeDto> actualEmployees = mapper.readValue(contentBody, new TypeReference<List<EmployeeDto>>() {});
        Assertions.assertNotNull(actualEmployees);
        Assertions.assertEquals(expectedEmployees.get(0).getId(), actualEmployees.get(0).getId());
        Assertions.assertEquals(expectedEmployees.get(0).getFirstName(), actualEmployees.get(0).getFirstName());
        Assertions.assertEquals(expectedEmployees.get(0).getLastName(), actualEmployees.get(0).getLastName());
        Assertions.assertEquals(expectedEmployees.get(0).getBirthDate(), actualEmployees.get(0).getBirthDate());
        Assertions.assertEquals(expectedEmployees.get(0).getEmail(), actualEmployees.get(0).getEmail());
        Assertions.assertEquals(expectedEmployees.get(0).getDepartmentId(), actualEmployees.get(0).getDepartmentId());
    }

    @Test
    void test_getEmployeeById_ok() throws Exception {
        EmployeeDto expectedEmployee = new EmployeeDto().id(1)
                                                        .firstName("abc")
                                                        .lastName("xyz")
                                                        .birthDate(LocalDate.of(1990, 12, 21))
                                                        .email("xyz@gmail.coms");
        Mockito.when(employeeService.getEmployeeById(Mockito.anyInt()))
               .thenReturn(expectedEmployee);
        String contentBody = mockMvc
                                    .perform(MockMvcRequestBuilders.get(BASE_PATH + "/1")
                                                                   .accept(MediaType.APPLICATION_JSON)
                                                                   .contentType(MediaType.APPLICATION_JSON))
                                    .andExpect(MockMvcResultMatchers.status().isOk())
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();
        Assertions.assertFalse(StringUtils.isEmpty(contentBody));
        EmployeeDto actualEmployee = mapper.readValue(contentBody, EmployeeDto.class);
        Assertions.assertNotNull(actualEmployee);
        Assertions.assertEquals(expectedEmployee.getId(), actualEmployee.getId());
        Assertions.assertEquals(expectedEmployee.getFirstName(), actualEmployee.getFirstName());
        Assertions.assertEquals(expectedEmployee.getLastName(), actualEmployee.getLastName());
        Assertions.assertEquals(expectedEmployee.getBirthDate(), actualEmployee.getBirthDate());
        Assertions.assertEquals(expectedEmployee.getEmail(), actualEmployee.getEmail());
        Assertions.assertEquals(expectedEmployee.getDepartmentId(), actualEmployee.getDepartmentId());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void test_deleteEmployeeById_UNAUTHORIZED() throws Exception {
        mockMvc
               .perform(MockMvcRequestBuilders.delete(BASE_PATH + "/1")
                                              .accept(MediaType.APPLICATION_JSON)
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void test_deleteEmployeeById_OK() throws Exception {
        mockMvc
               .perform(MockMvcRequestBuilders.delete(BASE_PATH + "/1")
                                              .accept(MediaType.APPLICATION_JSON)
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void test_createEmployee_UNAUTHORIZED() throws Exception {
        mockMvc
               .perform(MockMvcRequestBuilders.post(BASE_PATH + "/1")
                                              .accept(MediaType.APPLICATION_JSON)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(mapper.writeValueAsString(new EmployeeDto())))
               .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void test_createEmployee_BAD_REQUEST_FIRST_NAME_NULL() throws Exception {
        String contentBody = mockMvc
                                    .perform(MockMvcRequestBuilders.post(BASE_PATH)
                                                                   .accept(MediaType.APPLICATION_JSON)
                                                                   .contentType(MediaType.APPLICATION_JSON)
                                                                   .content(mapper.writeValueAsString(new EmployeeDto().firstName(null)
                                                                                                                       .lastName("xyz")
                                                                                                                       .birthDate(LocalDate.of(1990,
                                                                                                                                               12,
                                                                                                                                               21))
                                                                                                                       .email("xyz@gmail.coms")
                                                                                                                       .departmentId(1))))
                                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();
        Assertions.assertFalse(StringUtils.isEmpty(contentBody));
        List<ErrorMessageDto> errors = mapper.readValue(contentBody, new TypeReference<List<ErrorMessageDto>>() {});
        Assertions.assertNotNull(errors);
        Assertions.assertNotNull(errors.get(0).getField());
        Assertions.assertEquals("firstName", errors.get(0).getField());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void test_createEmployee_BAD_REQUEST_LAST_NAME_NULL() throws Exception {
        String contentBody = mockMvc
                                    .perform(MockMvcRequestBuilders.post(BASE_PATH)
                                                                   .accept(MediaType.APPLICATION_JSON)
                                                                   .contentType(MediaType.APPLICATION_JSON)
                                                                   .content(mapper.writeValueAsString(new EmployeeDto().firstName("abc")
                                                                                                                       .lastName(null)
                                                                                                                       .birthDate(LocalDate.of(1990,
                                                                                                                                               12,
                                                                                                                                               21))
                                                                                                                       .email("xyz@gmail.coms")
                                                                                                                       .departmentId(1))))
                                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();
        Assertions.assertFalse(StringUtils.isEmpty(contentBody));
        List<ErrorMessageDto> errors = mapper.readValue(contentBody, new TypeReference<List<ErrorMessageDto>>() {});
        Assertions.assertNotNull(errors);
        Assertions.assertNotNull(errors.get(0).getField());
        Assertions.assertEquals("lastName", errors.get(0).getField());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void test_createEmployee_BAD_REQUEST_EMAIL_NULL() throws Exception {
        String contentBody = mockMvc
                                    .perform(MockMvcRequestBuilders.post(BASE_PATH)
                                                                   .accept(MediaType.APPLICATION_JSON)
                                                                   .contentType(MediaType.APPLICATION_JSON)
                                                                   .content(mapper.writeValueAsString(new EmployeeDto().firstName("abc")
                                                                                                                       .lastName("xyz")
                                                                                                                       .birthDate(LocalDate.of(1990,
                                                                                                                                               12,
                                                                                                                                               21))
                                                                                                                       .email(null)
                                                                                                                       .departmentId(1))))
                                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();
        Assertions.assertFalse(StringUtils.isEmpty(contentBody));
        List<ErrorMessageDto> errors = mapper.readValue(contentBody, new TypeReference<List<ErrorMessageDto>>() {});
        Assertions.assertNotNull(errors);
        Assertions.assertNotNull(errors.get(0).getField());
        Assertions.assertEquals("email", errors.get(0).getField());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void test_createEmployee_BAD_REQUEST_BIRTH_DATE_NULL() throws Exception {
        String contentBody = mockMvc
                                    .perform(MockMvcRequestBuilders.post(BASE_PATH)
                                                                   .accept(MediaType.APPLICATION_JSON)
                                                                   .contentType(MediaType.APPLICATION_JSON)
                                                                   .content(mapper.writeValueAsString(new EmployeeDto().firstName("abc")
                                                                                                                       .lastName("xyz")
                                                                                                                       .birthDate(null)
                                                                                                                       .email("abx@xys.com")
                                                                                                                       .departmentId(1))))
                                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();
        Assertions.assertFalse(StringUtils.isEmpty(contentBody));
        List<ErrorMessageDto> errors = mapper.readValue(contentBody, new TypeReference<List<ErrorMessageDto>>() {});
        Assertions.assertNotNull(errors);
        Assertions.assertNotNull(errors.get(0).getField());
        Assertions.assertEquals("birthDate", errors.get(0).getField());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void test_createEmployee_BAD_REQUEST_DEPT_ID_NULL() throws Exception {
        String contentBody = mockMvc
                                    .perform(MockMvcRequestBuilders.post(BASE_PATH)
                                                                   .accept(MediaType.APPLICATION_JSON)
                                                                   .contentType(MediaType.APPLICATION_JSON)
                                                                   .content(mapper.writeValueAsString(new EmployeeDto().firstName("abc")
                                                                                                                       .lastName("xyz")
                                                                                                                       .birthDate(LocalDate.of(1990,
                                                                                                                                               12,
                                                                                                                                               21))
                                                                                                                       .email("abx@xys.com")
                                                                                                                       .departmentId(null))))
                                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();
        Assertions.assertFalse(StringUtils.isEmpty(contentBody));
        List<ErrorMessageDto> errors = mapper.readValue(contentBody, new TypeReference<List<ErrorMessageDto>>() {});
        Assertions.assertNotNull(errors);
        Assertions.assertNotNull(errors.get(0).getField());
        Assertions.assertEquals("departmentId", errors.get(0).getField());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void test_createEmployee_BAD_REQUEST_EMAIL_FORMAT_INCORRECT() throws Exception {
        String contentBody = mockMvc
                                    .perform(MockMvcRequestBuilders.post(BASE_PATH)
                                                                   .accept(MediaType.APPLICATION_JSON)
                                                                   .contentType(MediaType.APPLICATION_JSON)
                                                                   .content(mapper.writeValueAsString(new EmployeeDto().firstName("abc")
                                                                                                                       .lastName("xyz")
                                                                                                                       .birthDate(LocalDate.of(1990,
                                                                                                                                               12,
                                                                                                                                               21))
                                                                                                                       .email("abx")
                                                                                                                       .departmentId(1))))
                                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();
        Assertions.assertFalse(StringUtils.isEmpty(contentBody));
        List<ErrorMessageDto> errors = mapper.readValue(contentBody, new TypeReference<List<ErrorMessageDto>>() {});
        Assertions.assertNotNull(errors);
        Assertions.assertNotNull(errors.get(0).getField());
        Assertions.assertEquals("email", errors.get(0).getField());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void test_createEmployee_BAD_REQUEST_BIRTH_DATE_IN_FUTURE() throws Exception {
        String contentBody = mockMvc
                                    .perform(MockMvcRequestBuilders.post(BASE_PATH)
                                                                   .accept(MediaType.APPLICATION_JSON)
                                                                   .contentType(MediaType.APPLICATION_JSON)
                                                                   .content(mapper.writeValueAsString(new EmployeeDto().firstName("abc")
                                                                                                                       .lastName("xyz")
                                                                                                                       .birthDate(LocalDate.of(2020,
                                                                                                                                               12,
                                                                                                                                               21))
                                                                                                                       .email("abx@gmail.com")
                                                                                                                       .departmentId(1))))
                                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();
        Assertions.assertFalse(StringUtils.isEmpty(contentBody));
        List<ErrorMessageDto> errors = mapper.readValue(contentBody, new TypeReference<List<ErrorMessageDto>>() {});
        Assertions.assertNotNull(errors);
        Assertions.assertNotNull(errors.get(0).getField());
        Assertions.assertEquals("birthDate", errors.get(0).getField());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void test_createEmployee_OK() throws Exception {
        mockMvc
               .perform(MockMvcRequestBuilders.post(BASE_PATH)
                                              .accept(MediaType.APPLICATION_JSON)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(mapper.writeValueAsString(new EmployeeDto().firstName("abc")
                                                                                                  .lastName("xyz")
                                                                                                  .birthDate(LocalDate.of(1990, 12, 21))
                                                                                                  .email("awwx@gmail.com")
                                                                                                  .departmentId(1))))
               .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void test_updateEmployee_UNAUTHORIZED() throws Exception {
        mockMvc
               .perform(MockMvcRequestBuilders.put(BASE_PATH + "/1")
                                              .accept(MediaType.APPLICATION_JSON)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(mapper.writeValueAsString(new EmployeeDto())))
               .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void test_updateEmployee_OK() throws Exception {
        mockMvc
               .perform(MockMvcRequestBuilders.put(BASE_PATH + "/1")
                                              .accept(MediaType.APPLICATION_JSON)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(mapper.writeValueAsString(new EmployeeDto().firstName("abc")
                                                                                                  .lastName("xyz")
                                                                                                  .birthDate(LocalDate.of(1990, 12, 21))
                                                                                                  .email("abx@gmail.com")
                                                                                                  .departmentId(1))))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
