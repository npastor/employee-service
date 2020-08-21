package com.takeaway.challenge.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
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
import com.takeaway.challenge.dto.DepartmentDto;
import com.takeaway.challenge.service.DepartmentService;

@WebMvcTest(value = DepartmentController.class)
public class DepartmentControllerIntegrationTest {

    private static final String BASE_PATH = "/v1/departments";

    private final com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Test
    void test_getDepartments_ok() throws Exception {
        List<DepartmentDto> expectedDepartments = new ArrayList<DepartmentDto>();
        expectedDepartments.add(new DepartmentDto().id(1).name("dept1"));
        Mockito.when(departmentService.getDepartments())
               .thenReturn(expectedDepartments);

        String contentBody = mockMvc
                                    .perform(MockMvcRequestBuilders.get(BASE_PATH)
                                                                   .accept(MediaType.APPLICATION_JSON)
                                                                   .contentType(MediaType.APPLICATION_JSON))
                                    .andExpect(MockMvcResultMatchers.status().isOk())
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();
        Assertions.assertFalse(StringUtils.isEmpty(contentBody));
        List<DepartmentDto> actualDepartments = mapper.readValue(contentBody, new TypeReference<List<DepartmentDto>>() {});
        Assertions.assertNotNull(actualDepartments);
        Assertions.assertEquals(expectedDepartments.get(0).getName(), actualDepartments.get(0).getName());
        Assertions.assertEquals(expectedDepartments.get(0).getId(), actualDepartments.get(0).getId());

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void test_createDepartment_UNAUTHORIZED() throws Exception {
        mockMvc
               .perform(MockMvcRequestBuilders.post(BASE_PATH)
                                              .accept(MediaType.APPLICATION_JSON)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(mapper.writeValueAsString(new DepartmentDto().name("HR"))))
               .andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void test_createDepartment_OK() throws Exception {
        mockMvc
               .perform(MockMvcRequestBuilders.post(BASE_PATH)
                                              .accept(MediaType.APPLICATION_JSON)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(mapper.writeValueAsString(new DepartmentDto().name("HR"))))
               .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void test_createDepartment_BAD_REQUEST_NULL_NAME() throws Exception {
        mockMvc
               .perform(MockMvcRequestBuilders.post(BASE_PATH)
                                              .accept(MediaType.APPLICATION_JSON)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(mapper.writeValueAsString(new DepartmentDto().name(null))))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void test_createDepartment_BAD_REQUEST_EMPTY_NAME() throws Exception {
        mockMvc
               .perform(MockMvcRequestBuilders.post(BASE_PATH)
                                              .accept(MediaType.APPLICATION_JSON)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(mapper.writeValueAsString(new DepartmentDto().name(null))))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
