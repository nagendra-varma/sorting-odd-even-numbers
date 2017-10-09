package com.techo.sortdata.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class SortingAlgorithmControllerTest {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webAppContext).build();
    }

    @Test
    public void shouldSortFollowingData() throws Exception {
        Integer[] mixedNumbers = new Integer[]{2, 35, 6, 7, 811, 20};
        mockMvc.perform(get(buildSortIntegersUrl(mixedNumbers)))
                .andDo(mvcResult -> {
                    MockHttpServletResponse response = mvcResult.getResponse();
                    assertEquals(response.getStatus(), HttpStatus.OK.value());
                    assertThat(Arrays.asList(mixedNumbers), containsInAnyOrder(stringToIntegerArray(response.getContentAsString())));
                    assertTrue(isSorted(response.getContentAsString()));
                });
    }

    @Test
    public void shouldSortIfAllNumbersAreOddNumbers() throws Exception {
        Integer[] oddNumbers = new Integer[]{35,7,811};
        mockMvc.perform(get(buildSortIntegersUrl(oddNumbers)))
                .andDo(mvcResult -> {
                    MockHttpServletResponse response = mvcResult.getResponse();
                    assertEquals(response.getStatus(), HttpStatus.OK.value());
                    assertThat(Arrays.asList(oddNumbers), containsInAnyOrder(stringToIntegerArray(response.getContentAsString())));
                    assertTrue(isSorted(response.getContentAsString()));
                });
    }

    @Test
    public void shouldSortIfAllNumbersAreEvenNumbers() throws Exception {
        Integer[] evenNumbers = new Integer[]{2, 4, 6, 8};
        mockMvc.perform(get(buildSortIntegersUrl(evenNumbers)))
                .andDo(mvcResult -> {
                    MockHttpServletResponse response = mvcResult.getResponse();
                    assertEquals(response.getStatus(), HttpStatus.OK.value());
                    assertThat(Arrays.asList(evenNumbers), containsInAnyOrder(stringToIntegerArray(response.getContentAsString())));
                    assertTrue(isSorted(response.getContentAsString()));
                });
    }

    @Test
    public void shouldReturnBadRequestIfNoArrayPassedAsArgument() throws Exception {
        Integer[] evenNumbers = new Integer[]{};
        mockMvc.perform(get(buildSortIntegersUrl(evenNumbers)))
                .andDo(mvcResult -> {
                    MockHttpServletResponse response = mvcResult.getResponse();
                    assertEquals(BAD_REQUEST.value(), response.getStatus());
                });
    }

    private boolean isSorted(String contentAsString) {
        Integer[] myTypeList = stringToIntegerArray(contentAsString);
        int index = 0;

        // Run till the even number is found.
        while((index < myTypeList.length) && (myTypeList[index] % 2 != 0)) {
            index++;
        }

        // break if odd number found
        while((index < myTypeList.length)) {
            if (myTypeList[index] %2 != 0) {
                return false;
            }
            index++;
        }
        return true;
    }

    private Integer[] stringToIntegerArray(String contentAsString) {
        ObjectMapper mapper = new ObjectMapper();
        List<Integer> list = null;
        try {
            list = mapper.readValue(contentAsString, new TypeReference<List<Integer>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(list);
        return list.toArray(new Integer[list.size()]);
    }

    /**
     * Utility method for constructing a url with input as an array (without '[]')
     * @param evenNumbers
     * @return
     */
    private String buildSortIntegersUrl(Integer[] evenNumbers) {
        return "/sortintegers?array="
                .concat(Arrays.toString(evenNumbers)
                        .replace("[", "")
                        .replace("]", ""));
    }

}