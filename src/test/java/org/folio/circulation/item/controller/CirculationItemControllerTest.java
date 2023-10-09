package org.folio.circulation.item.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.folio.circulation.item.utils.EntityUtils.createCirculationItem;
import static org.folio.circulation.item.utils.EntityUtils.createCirculationItemForUpdate;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CirculationItemControllerTest extends BaseIT {

    private static final String URI_TEMPLATE_CIRCULATION_ITEM = "/circulation-item/";
    @Test
    void createCirculationItemTest() throws Exception {
        var id = UUID.randomUUID();
        this.mockMvc.perform(
                        post(URI_TEMPLATE_CIRCULATION_ITEM + id)
                                .content(asJsonString(createCirculationItem(id)))
                                .headers(defaultHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("TEST"))
                .andExpect(jsonPath("$.materialTypeId").value("materialTypeId_TEST"))
                .andExpect(jsonPath("$.itemBarcode").value("itemBarcode_TEST"));

        //Trying to create another circulation item with same circulation item id
        this.mockMvc.perform(
                        post(URI_TEMPLATE_CIRCULATION_ITEM + id)
                                .content(asJsonString(createCirculationItem(id)))
                                .headers(defaultHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().is4xxClientError(),
                        jsonPath("$.errors[0].code", is("DUPLICATE_ERROR")));
    }

    @Test
    void retrieveCirculationItemSuccessTest() throws Exception {
        var id = UUID.randomUUID();
        this.mockMvc.perform(
                        post(URI_TEMPLATE_CIRCULATION_ITEM + id)
                                .content(asJsonString(createCirculationItem(id)))
                                .headers(defaultHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("TEST"))
                .andExpect(jsonPath("$.materialTypeId").value("materialTypeId_TEST"))
                .andExpect(jsonPath("$.itemBarcode").value("itemBarcode_TEST"));

        mockMvc.perform(
                        get(URI_TEMPLATE_CIRCULATION_ITEM + id)
                                .headers(defaultHeaders())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void retrieveCirculationItemNotFoundTest() throws Exception {
        var id = UUID.randomUUID();
        mockMvc.perform(
                        get(URI_TEMPLATE_CIRCULATION_ITEM + id)
                                .headers(defaultHeaders())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateCirculationItemTest() throws Exception {
        var id = UUID.randomUUID();
        //Set up brand-new circulation item.
        this.mockMvc.perform(
                        post(URI_TEMPLATE_CIRCULATION_ITEM + id)
                                .content(asJsonString(createCirculationItem(id)))
                                .headers(defaultHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("TEST"))
                .andExpect(jsonPath("$.materialTypeId").value("materialTypeId_TEST"))
                .andExpect(jsonPath("$.itemBarcode").value("itemBarcode_TEST"));

        //Update existed circulation item with success.
        this.mockMvc.perform(
                        put(URI_TEMPLATE_CIRCULATION_ITEM + id)
                                .content(asJsonString(createCirculationItemForUpdate(id)))
                                .headers(defaultHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("TEST_UPD"))
                .andExpect(jsonPath("$.materialTypeId").value("materialTypeId_TEST_UPD"))
                .andExpect(jsonPath("$.itemBarcode").value("itemBarcode_TEST_UPD"));

        //Update existed circulation item with different ids provided by request. IdMismatchException expected.
        this.mockMvc.perform(
                        put(URI_TEMPLATE_CIRCULATION_ITEM + id)
                                .content(asJsonString(createCirculationItemForUpdate(UUID.randomUUID())))
                                .headers(defaultHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().is4xxClientError(),
                        jsonPath("$.errors[0].code", is("VALIDATION_ERROR")));

        //Update not existed circulation item. NotFoundException expected.
        var notExistedItemId = UUID.randomUUID();
        this.mockMvc.perform(
                        put(URI_TEMPLATE_CIRCULATION_ITEM + notExistedItemId)
                                .content(asJsonString(createCirculationItemForUpdate(notExistedItemId)))
                                .headers(defaultHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().is4xxClientError(),
                        jsonPath("$.errors[0].code", is("NOT_FOUND_ERROR")));
    }

}
