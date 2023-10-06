package org.folio.circulation.item.service;

import org.folio.circulation.item.domain.mapper.CirculationItemsMapper;
import org.folio.circulation.item.exception.IdMismatchException;
import org.folio.circulation.item.repository.CirculationItemsRepository;
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
class CirculationItemsServiceTest {

    @InjectMocks
    private CirculationItemsService circulationItemsService;
    @Mock
    private CirculationItemsRepository circulationItemsRepository;

    @Mock
    private CirculationItemsMapper circulationItemsMapper;

    @Test
    void shouldCreateCirculationItemTest(){
        var ciIdUnique = UUID.randomUUID();
        when(circulationItemsMapper.mapDtoToEntity(any())).thenReturn(createCirculationEntityItem(ciIdUnique));
        when(circulationItemsMapper.mapEntityToDto(any())).thenReturn(createCirculationItem(ciIdUnique));
        when(circulationItemsRepository.save(any()))
                .thenReturn(createCirculationEntityItem(ciIdUnique));

        var ciInstance = circulationItemsService.createCirculationItem(String.valueOf(ciIdUnique), createCirculationItem(ciIdUnique));
        assertNotNull(ciInstance);
        assertEquals("TEST", ciInstance.getStatus());
    }

    @Test
    void shouldUpdateCirculationItemTest(){
        var ciIdUnique = UUID.randomUUID();

        var updCiDto = createCirculationItemForUpdate(ciIdUnique);
        var updCiEntity = createCirculationEntityItemForUpdate(ciIdUnique);

        when(circulationItemsMapper.mapDtoToEntity(any()))
                .thenReturn(updCiEntity);
        when(circulationItemsMapper.mapEntityToDto(any()))
                .thenReturn(updCiDto);

        when(circulationItemsRepository.existsById(ciIdUnique)).thenReturn(true);
        when(circulationItemsRepository.save(updCiEntity))
                .thenReturn(updCiEntity);
        var ciInstanceUpdated = circulationItemsService.updateCirculationItem(String.valueOf(ciIdUnique), createCirculationItem(ciIdUnique));

        assertNotNull(ciInstanceUpdated);
        assertEquals("TEST_UPD", ciInstanceUpdated.getStatus());
    }

    @Test
    void shouldReturnAnyCirculationItemByIdTest(){
        var ciIdUnique = UUID.randomUUID();
        when(circulationItemsRepository.findById(ciIdUnique))
                .thenReturn(Optional.ofNullable(createCirculationEntityItem(ciIdUnique)));
        when(circulationItemsMapper.mapEntityToDto(any())).thenReturn(createCirculationItem(ciIdUnique));


        var ciInstance = circulationItemsService.getCirculationItemById(String.valueOf(ciIdUnique));
        assertNotNull(ciInstance);
        assertEquals("TEST", ciInstance.getStatus());
    }

    @Test
    void getCirculationItemEmptyResultTest(){
        var ciIdUnique = UUID.randomUUID();
        when(circulationItemsRepository.findById(ciIdUnique))
                .thenReturn(Optional.empty());

        var ciIdUniqueString = String.valueOf(ciIdUnique);
        var ciInstance = circulationItemsService.getCirculationItemById(ciIdUniqueString);
        assertNull(ciInstance);
    }

    @Test
    void idMismatchExceptionWhileCirculationItemCreationTest(){
        var ciIdUnique_1 = UUID.randomUUID().toString();
        var ciIdUnique_2 = UUID.randomUUID();

        Throwable exception = assertThrows(
                IdMismatchException.class, () -> circulationItemsService.createCirculationItem(ciIdUnique_1, createCirculationItem(ciIdUnique_2))
        );

        Assertions.assertEquals(String.format("Request id= %s and entity id= %s are not equal", ciIdUnique_1, ciIdUnique_2), exception.getMessage());
    }

    @Test
    void circulationItemDoesntExistDuringUpdateNotFoundExceptionTest(){
        var ciIdUnique = UUID.randomUUID();
        var ciIdUniqueString = String.valueOf(ciIdUnique);

        when(circulationItemsRepository.existsById(ciIdUnique)).thenReturn(false);

        Throwable exception = assertThrows(
                NotFoundException.class, () -> circulationItemsService.updateCirculationItem(ciIdUniqueString, createCirculationItem(ciIdUnique))
        );

        Assertions.assertEquals(String.format("Circulation item with id %s doesn't exist", ciIdUnique), exception.getMessage());
    }
}
