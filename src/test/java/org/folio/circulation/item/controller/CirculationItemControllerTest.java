package org.folio.circulation.item.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.folio.circulation.item.domain.dto.CirculationItem;
import org.folio.circulation.item.domain.dto.ItemStatus;
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
      var barcode = RandomStringUtils.randomAlphabetic(10).toUpperCase();
      var circulationItemRequest = createCirculationItem(id);
      circulationItemRequest.setBarcode(barcode);
      this.mockMvc.perform(
                      post(URI_TEMPLATE_CIRCULATION_ITEM + id)
                              .content(asJsonString(circulationItemRequest))
                              .headers(defaultHeaders())
                              .contentType(MediaType.APPLICATION_JSON)
                              .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.status.name").value(ItemStatus.NameEnum.AVAILABLE.getValue()))
              .andExpect(jsonPath("$.materialTypeId").value("materialTypeId_TEST"))
              .andExpect(jsonPath("$.barcode").value(barcode));

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
  void createCirculationItemWithSameBarcodeTest() throws Exception {
    var id = UUID.randomUUID();
    var barcode = RandomStringUtils.randomAlphabetic(10).toUpperCase();
    var circulationItemRequest = createCirculationItem(id);
    circulationItemRequest.setBarcode(barcode);
    this.mockMvc.perform(
        post(URI_TEMPLATE_CIRCULATION_ITEM + id)
          .content(asJsonString(circulationItemRequest))
          .headers(defaultHeaders())
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.status.name").value(ItemStatus.NameEnum.AVAILABLE.getValue()))
      .andExpect(jsonPath("$.materialTypeId").value("materialTypeId_TEST"))
      .andExpect(jsonPath("$.barcode").value(barcode));

    //Trying to create another circulation item with same barcode and different item id
    id = UUID.randomUUID();
    circulationItemRequest.setId(id);
    this.mockMvc.perform(
        post(URI_TEMPLATE_CIRCULATION_ITEM + id)
          .content(asJsonString(circulationItemRequest))
          .headers(defaultHeaders())
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
      .andExpectAll(status().is4xxClientError(),
        jsonPath("$.errors[0].code", is("DUPLICATE_ERROR")));
  }

  @Test
  void retrieveCirculationItemSuccessTest() throws Exception {
    var id = UUID.randomUUID();
    var barcode = RandomStringUtils.randomAlphabetic(10).toUpperCase();
    var circulationItemRequest = createCirculationItem(id);
    circulationItemRequest.setBarcode(barcode);
      this.mockMvc.perform(
                      post(URI_TEMPLATE_CIRCULATION_ITEM + id)
                              .content(asJsonString(circulationItemRequest))
                              .headers(defaultHeaders())
                              .contentType(MediaType.APPLICATION_JSON)
                              .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.status.name").value(ItemStatus.NameEnum.AVAILABLE.getValue()))
              .andExpect(jsonPath("$.materialTypeId").value("materialTypeId_TEST"))
              .andExpect(jsonPath("$.barcode").value(barcode));

      mockMvc.perform(
                      get(URI_TEMPLATE_CIRCULATION_ITEM + id)
                              .headers(defaultHeaders())
                              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
  }

  @Test
  void retrieveCirculationItemSuccessByQueryTest() throws Exception {
    var id = UUID.randomUUID();
    CirculationItem item = createCirculationItem(id);
    item.setBarcode("0000");
    this.mockMvc.perform(
        post(URI_TEMPLATE_CIRCULATION_ITEM + id)
          .content(asJsonString(item))
          .headers(defaultHeaders())
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.status.name").value(ItemStatus.NameEnum.AVAILABLE.getValue()))
      .andExpect(jsonPath("$.materialTypeId").value("materialTypeId_TEST"))
      .andExpect(jsonPath("$.barcode").value("0000"));

    mockMvc.perform(
        get("/circulation-item?query=id=="+id)
          .headers(defaultHeaders())
          .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @Test
  void getCirculationItemByBarcodeUsingCqlQuery() throws Exception {
    var id = UUID.randomUUID();
    var barcode = RandomStringUtils.randomAlphabetic(10).toUpperCase();
    var circulationItemRequest = createCirculationItem(id);
    circulationItemRequest.setBarcode(barcode);
    this.mockMvc.perform(
        post(URI_TEMPLATE_CIRCULATION_ITEM + id)
          .content(asJsonString(circulationItemRequest))
          .headers(defaultHeaders())
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.status.name").value(ItemStatus.NameEnum.AVAILABLE.getValue()))
      .andExpect(jsonPath("$.materialTypeId").value("materialTypeId_TEST"))
      .andExpect(jsonPath("$.barcode").value(barcode));

    mockMvc.perform(
        get( "/circulation-item?query=barcode==" + circulationItemRequest.getBarcode())
          .headers(defaultHeaders())
          .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

    @Test
    void retrieveCirculationItemNotFoundTest() throws Exception {
        var id = UUID.randomUUID();
        mockMvc.perform(
                        get(URI_TEMPLATE_CIRCULATION_ITEM  + id)
                                .headers(defaultHeaders())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

  @Test
  void getCirculationItemByBarcodeNotFoundTest() throws Exception {
    var barcode = UUID.randomUUID();
    mockMvc.perform(
        get("/circulation-item?query=barcode==" + barcode)
          .headers(defaultHeaders())
          .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.totalRecords").value(0))
      .andExpect(jsonPath("$.items").isEmpty());
  }

    @Test
    void updateCirculationItemTest() throws Exception {
        var id = UUID.randomUUID();
        var barcode = RandomStringUtils.randomAlphabetic(10).toUpperCase();
        var circulationItemRequest = createCirculationItem(id);
        circulationItemRequest.setBarcode(barcode);
        //Set up brand-new circulation item.
        this.mockMvc.perform(
                        post(URI_TEMPLATE_CIRCULATION_ITEM + id)
                                .content(asJsonString(circulationItemRequest))
                                .headers(defaultHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status.name").value(ItemStatus.NameEnum.AVAILABLE.getValue()))
                .andExpect(jsonPath("$.materialTypeId").value("materialTypeId_TEST"))
                .andExpect(jsonPath("$.barcode").value(barcode));

        //Update existed circulation item with success.
        this.mockMvc.perform(
                        put(URI_TEMPLATE_CIRCULATION_ITEM + id)
                                .content(asJsonString(createCirculationItemForUpdate(id)))
                                .headers(defaultHeaders())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.name").value(ItemStatus.NameEnum.IN_TRANSIT.getValue()))
                .andExpect(jsonPath("$.materialTypeId").value("materialTypeId_TEST_UPD"))
                .andExpect(jsonPath("$.barcode").value("itemBarcode_TEST_UPD"));

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
