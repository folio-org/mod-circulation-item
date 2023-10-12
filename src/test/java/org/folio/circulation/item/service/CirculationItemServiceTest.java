package org.folio.circulation.item.service;

import org.folio.circulation.item.domain.entity.Item;
import org.folio.circulation.item.domain.mapper.CirculationItemMapper;
import org.folio.circulation.item.exception.IdMismatchException;
import org.folio.circulation.item.repository.CirculationItemRepository;
import org.folio.circulation.item.service.impl.CirculationItemServiceImpl;
import org.folio.spring.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.folio.circulation.item.utils.EntityUtils.createCirculationEntityItem;
import static org.folio.circulation.item.utils.EntityUtils.createCirculationEntityItemForUpdate;
import static org.folio.circulation.item.utils.EntityUtils.createCirculationItem;
import static org.folio.circulation.item.utils.EntityUtils.createCirculationItemForUpdate;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CirculationItemServiceTest {

    @InjectMocks
    private CirculationItemServiceImpl circulationItemServiceImpl;
    @Mock
    private CirculationItemRepository circulationItemRepository;

    @Mock
    private CirculationItemMapper circulationItemMapper;

    @Test
    void shouldCreateCirculationItemTest(){
        var ciIdUnique = UUID.randomUUID();
        when(circulationItemMapper.mapDtoToEntity(any())).thenReturn(createCirculationEntityItem(ciIdUnique));
        when(circulationItemMapper.mapEntityToDto(any())).thenReturn(createCirculationItem(ciIdUnique));
        when(circulationItemRepository.save(any()))
                .thenReturn(createCirculationEntityItem(ciIdUnique));

        var ciInstance = circulationItemServiceImpl.createCirculationItem(String.valueOf(ciIdUnique), createCirculationItem(ciIdUnique));
        assertNotNull(ciInstance);
        assertEquals("TEST", ciInstance.getStatus());
    }

    @Test
    void shouldUpdateCirculationItemTest(){
        var ciIdUnique = UUID.randomUUID();

        var updCiDto = createCirculationItemForUpdate(ciIdUnique);
        var updCiEntity = createCirculationEntityItemForUpdate(ciIdUnique);

        when(circulationItemMapper.mapDtoToEntity(any()))
                .thenReturn(updCiEntity);
        when(circulationItemMapper.mapEntityToDto(any()))
                .thenReturn(updCiDto);

        when(circulationItemRepository.existsById(ciIdUnique)).thenReturn(true);
        when(circulationItemRepository.save(updCiEntity))
                .thenReturn(updCiEntity);
        var ciInstanceUpdated = circulationItemServiceImpl.updateCirculationItem(String.valueOf(ciIdUnique), createCirculationItem(ciIdUnique));

        assertNotNull(ciInstanceUpdated);
        assertEquals("TEST_UPD", ciInstanceUpdated.getStatus());
    }

    @Test
    void shouldReturnAnyCirculationItemByIdTest(){
        var ciIdUnique = UUID.randomUUID();
        when(circulationItemRepository.findById(ciIdUnique))
                .thenReturn(Optional.ofNullable(createCirculationEntityItem(ciIdUnique)));
        when(circulationItemMapper.mapEntityToDto(any())).thenReturn(createCirculationItem(ciIdUnique));


        var ciInstance = circulationItemServiceImpl.getCirculationItemById(String.valueOf(ciIdUnique));
        assertNotNull(ciInstance);
        assertEquals("TEST", ciInstance.getStatus());
    }

  @Test
  void shouldReturnCirculationItemByBarcodeTest(){
    var ciIdUnique = UUID.randomUUID();
    Item item = createCirculationEntityItem(ciIdUnique);
    String barcode = item.getItemBarcode();
    Optional<Item> itemOptional = Optional.ofNullable(item);
    when(circulationItemRepository.findByItemBarcode(barcode)).thenReturn(itemOptional);
    when(circulationItemMapper.mapEntityToDto(any())).thenReturn(createCirculationItem(ciIdUnique));

    var ciInstance = circulationItemServiceImpl.getCirculationItemByBarcode(item.getItemBarcode());
    assertNotNull(ciInstance);
    assertEquals(barcode, ciInstance.getItemBarcode());
  }

    @Test
    void getCirculationItemEmptyResultTest(){
        var ciIdUnique = UUID.randomUUID();
        when(circulationItemRepository.findById(ciIdUnique))
                .thenReturn(Optional.empty());

        var ciIdUniqueString = String.valueOf(ciIdUnique);
        var ciInstance = circulationItemServiceImpl.getCirculationItemById(ciIdUniqueString);
        assertNull(ciInstance);
    }

    @Test
    void idMismatchExceptionWhileCirculationItemCreationTest(){
        var ciIdUnique_1 = UUID.randomUUID().toString();
        var ciIdUnique_2 = UUID.randomUUID();
        var circulationItem = createCirculationItem(ciIdUnique_2);

        Throwable exception = assertThrows(
                IdMismatchException.class, () -> circulationItemServiceImpl.createCirculationItem(ciIdUnique_1, circulationItem)
        );

        Assertions.assertEquals(String.format("Request id= %s and entity id= %s are not equal", ciIdUnique_1, ciIdUnique_2), exception.getMessage());
    }

    @Test
    void circulationItemDoesntExistDuringUpdateNotFoundExceptionTest(){
        var ciIdUnique = UUID.randomUUID();
        var ciIdUniqueString = String.valueOf(ciIdUnique);
        var circulationItem = createCirculationItem(ciIdUnique);

        when(circulationItemRepository.existsById(ciIdUnique)).thenReturn(false);


        Throwable exception = assertThrows(
                NotFoundException.class, () -> circulationItemServiceImpl.updateCirculationItem(ciIdUniqueString, circulationItem)
        );

        Assertions.assertEquals(String.format("Circulation item with id %s doesn't exist", ciIdUnique), exception.getMessage());
    }
}
