package com.hyuseinmustafa.librarymanagement.web.v1.controller;

import com.hyuseinmustafa.librarymanagement.service.ContentUpdateStatus;
import com.hyuseinmustafa.librarymanagement.service.LocationService;
import com.hyuseinmustafa.librarymanagement.web.v1.model.GetLocationDto;
import com.hyuseinmustafa.librarymanagement.web.v1.model.PostLocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<List<GetLocationDto>> getAllLocations(){
        return new ResponseEntity(locationService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetLocationDto> getLocation(@PathVariable Long id){
        return new ResponseEntity(locationService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GetLocationDto> createLocation(@RequestBody @Valid PostLocationDto postLocationDto){
        return new ResponseEntity(locationService.create(postLocationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetLocationDto> updateLocation(@PathVariable Long id,
                                                         @RequestBody @Valid PostLocationDto postLocationDto){
        Pair pair = locationService.update(id, postLocationDto);
        return new ResponseEntity(pair.getFirst(),
                pair.getSecond() == ContentUpdateStatus.UPDATED ? HttpStatus.OK : HttpStatus.CREATED);
    }
}
