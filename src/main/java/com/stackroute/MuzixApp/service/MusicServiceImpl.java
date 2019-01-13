package com.stackroute.MuzixApp.service;

import com.stackroute.MuzixApp.domain.Track;
import com.stackroute.MuzixApp.exception.TrackAlreadyExistsException;
import com.stackroute.MuzixApp.exception.TrackDoesnotExistsException;
import com.stackroute.MuzixApp.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class MusicServiceImpl implements MusicService {

    private MusicRepository musicRepository;
    @Autowired
    public MusicServiceImpl(MusicRepository musicRepository){
        this.musicRepository=musicRepository;
    }


    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistsException {


        if(musicRepository.existsById(track.getTrackId())){
            throw new TrackAlreadyExistsException("Track Already Exists");
        }
        Track addTrack = musicRepository.save(track);


        return addTrack;
    }

    @Override
    public List<Track> displayAllTracks() {
        List<Track> allTracks = (List<Track>) musicRepository.findAll();
        return allTracks;
    }

    @Override
    public Track updateComment(String comment, int id) throws TrackDoesnotExistsException {
        Track track1 = null;
        if(musicRepository.existsById(id)){


            Track newTrack =   musicRepository.findById(id).get();
            newTrack.setTrackComments(comment);
            track1 = musicRepository.save(newTrack);


        }
        else{
            throw new TrackDoesnotExistsException("Track Not Found");
        }

        return track1;
    }


    @Override
    public boolean deleteTrack(int id) throws TrackDoesnotExistsException {

        boolean result = false;
        if(musicRepository.existsById(id)) {
            musicRepository.deleteById(id);
            result = true;
        }
        else
            throw new TrackDoesnotExistsException("Track does not Exist");
        return result;
    }

}
