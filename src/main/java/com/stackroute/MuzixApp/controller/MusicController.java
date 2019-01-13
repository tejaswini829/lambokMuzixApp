package com.stackroute.MuzixApp.controller;

import com.stackroute.MuzixApp.domain.Track;
import com.stackroute.MuzixApp.exception.TrackAlreadyExistsException;
import com.stackroute.MuzixApp.exception.TrackDoesnotExistsException;
import com.stackroute.MuzixApp.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class MusicController {

    private MusicService musicService;
    @Autowired

    public MusicController(MusicService musicService){
this.musicService=musicService;
    }
public void setMusicService(MusicService musicService){
        this.musicService=musicService;
    }
    @PostMapping(value = "track")
    public ResponseEntity<?> saveTrack(@RequestBody Track track){
        ResponseEntity responseEntity;
        Track track1=null;
        try {
            track1 = musicService.saveTrack(track);
            responseEntity =  new ResponseEntity<Track>(track1, HttpStatus.OK);
        }
        catch (TrackAlreadyExistsException ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
            ex.printStackTrace();
        }
        return responseEntity;
    }

    @GetMapping(value = "track")
    public ResponseEntity<List<Track>> getAllTracks(){
        List<Track> listOfTracks = (List<Track>) musicService.displayAllTracks();
        return new ResponseEntity<List<Track>>(listOfTracks, HttpStatus.OK);
    }

    @PutMapping(value = "track/{comments}/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("comments") String comments, @PathVariable("id") int id){
        Track newTrack = null;
        ResponseEntity responseEntity;
        try {
            newTrack = musicService.updateComment(comments, id);
            responseEntity =  new ResponseEntity<Track>(newTrack, HttpStatus.OK);
        }
        catch(TrackDoesnotExistsException ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
            ex.printStackTrace();
        }

        return responseEntity;
    }

    @DeleteMapping(value = "track/{id}")
    public ResponseEntity<?> removeTrack(@PathVariable("id") int id){
        ResponseEntity responseEntity;

        try {
            boolean result = musicService.deleteTrack(id);
            responseEntity =  new ResponseEntity<Boolean>(result, HttpStatus.OK);
        }
        catch (TrackDoesnotExistsException ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
        }


        return responseEntity;
    }



}
