package pro.laplacelab.mt4j.controller;

import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.laplacelab.mt4j.JsonMapper;
import pro.laplacelab.mt4j.exception.DuplicateAdvisorException;
import pro.laplacelab.mt4j.model.Advisor;
import pro.laplacelab.mt4j.service.AdvisorService;
import pro.laplacelab.mt4j.validation.ExceptionResponse;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdvisorController.class)
@RunWith(SpringRunner.class)
@DisplayName("Test Advisor controller")
public class AdvisorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvisorService advisorService;

    private final JsonMapper mapper = new JsonMapper();

    @Test
    @SneakyThrows
    @DisplayName("When advisor successfully added then AdvisorService called AdvisorService#add()")
    public void whenAdvisorSuccessfullyAddedThenAdvisorServiceCallAdd() {
        // given
        final Advisor advisor = new Advisor(1L, Lists.emptyList());
        when(advisorService.findByAdvisorId(advisor.getId())).thenReturn(Optional.of(advisor));

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/advisor/add")
                .content(mapper.toJson(advisor))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        // then
        verify(advisorService, times(1)).add(advisor);
    }

    @Test
    @SneakyThrows
    @DisplayName("When try to add duplicate advisor then return ExceptionResponse")
    public void whenTryToAddDuplicateAdvisorThenReturnExceptionResponse() {
        // given
        final Advisor advisor = new Advisor(1L, Lists.emptyList());
        when(advisorService.add(advisor)).thenThrow(new DuplicateAdvisorException(advisor.getMagic()));

        // when
        final String json = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/advisor/add")
                .content(mapper.toJson(advisor))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().is(HttpStatus.BAD_REQUEST.value())
        ).andReturn()
                .getResponse()
                .getContentAsString();

        final ExceptionResponse actual = mapper.readValue(json, ExceptionResponse.class);

        // then
        Assertions.assertTrue(actual.getIsCritical());
        Assertions.assertEquals("Advisor with MagicNumber 1 already exist", actual.getMessage());
    }
}