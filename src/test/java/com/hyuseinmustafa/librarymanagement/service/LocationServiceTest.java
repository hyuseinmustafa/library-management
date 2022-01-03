package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.domain.Location;
import com.hyuseinmustafa.librarymanagement.exception.NotFoundException;
import com.hyuseinmustafa.librarymanagement.repository.LocationRepository;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.LocationMapper;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.LocationMapperImpl;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetLocationDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostLocationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private LocationRepository repository;
    private LocationService service;
    private final Location location = Location.builder().id(1L).roomNumber(1L).shelfNumber(1L).build();
    private final PostLocationDto dto = PostLocationDto.builder().roomNumber(2L).shelfNumber(2L).build();

    @BeforeEach
    void setUp() {
        LocationMapper mapper = new LocationMapperImpl();
        service = new LocationServiceImpl(repository, mapper);
    }

    @Test
    void getAll() {
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(location));

        List<GetLocationDto> response = service.getAll();

        assertEquals(response.size(), 1);
    }

    @Test
    void getById() {
        Assertions.assertThrowsExactly(NotFoundException.class, () -> service.getById(1L));

        Mockito.when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(location));

        GetLocationDto response = service.getById(1L);

        assertThat(response).isNotNull();
    }

    @Test
    void create() {
        Mockito.when(repository.save(any(Location.class))).thenReturn(location);

        GetLocationDto response = service.create(dto);

        assertThat(response).isNotNull();
    }

    @Test
    void update() {
        Mockito.when(repository.save(any(Location.class))).thenReturn(location);
        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(location));
        Mockito.when(repository.findById(2L)).thenReturn(java.util.Optional.empty());

        Pair<GetLocationDto, ContentUpdateStatus> response = service.update(1L, dto);

        assertThat(response.getFirst().getRoomNumber()).isEqualTo(dto.getRoomNumber());
        assertThat(response.getFirst().getShelfNumber()).isEqualTo(dto.getShelfNumber());

        response = service.update(2L, dto);

        assertThat(response.getFirst().getRoomNumber()).isEqualTo(dto.getRoomNumber());
        assertThat(response.getFirst().getShelfNumber()).isEqualTo(dto.getShelfNumber());
    }
}