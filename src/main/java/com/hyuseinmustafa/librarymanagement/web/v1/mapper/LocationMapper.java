package com.hyuseinmustafa.librarymanagement.web.v1.mapper;

import com.hyuseinmustafa.librarymanagement.domain.Location;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetLocationDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostLocationDto;
import org.mapstruct.Mapper;

@Mapper
public interface LocationMapper {

    GetLocationDto toGetLocationDto(Location location);

    Location toLocation(PostLocationDto postLocationDto);
}
