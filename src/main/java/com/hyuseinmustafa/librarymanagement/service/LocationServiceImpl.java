package com.hyuseinmustafa.librarymanagement.service;

import com.hyuseinmustafa.librarymanagement.domain.Location;
import com.hyuseinmustafa.librarymanagement.exception.NotFoundException;
import com.hyuseinmustafa.librarymanagement.repository.LocationRepository;
import com.hyuseinmustafa.librarymanagement.web.v1.mapper.LocationMapper;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetLocationDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostLocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Override
    public List<GetLocationDto> getAll() {
        return StreamSupport.stream(locationRepository.findAll().spliterator(), false)
                .map(locationMapper::toGetLocationDto).collect(Collectors.toList());
    }

    @Override
    public GetLocationDto getById(Long id) {
        return locationMapper.toGetLocationDto(locationRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public GetLocationDto create(PostLocationDto postLocationDto) {
        return locationMapper.toGetLocationDto(locationRepository.save(locationMapper.toLocation(postLocationDto)));
    }

    @Override
    public Pair<GetLocationDto, ContentUpdateStatus> update(Long id, PostLocationDto postLocationDto) {
        Location location = locationRepository.findById(id).orElse(new Location());
        Location locationNew = locationMapper.toLocation(postLocationDto);
        location.setRoomNumber(locationNew.getRoomNumber());
        location.setShelfNumber(locationNew.getShelfNumber());
        ContentUpdateStatus status = location.getId() == null ?
                ContentUpdateStatus.CREATED_NEW : ContentUpdateStatus.UPDATED;
        return Pair.of(locationMapper.toGetLocationDto(locationRepository.save(location)), status);
    }
}
