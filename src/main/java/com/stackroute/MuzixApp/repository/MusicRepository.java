package com.stackroute.MuzixApp.repository;

import com.stackroute.MuzixApp.domain.Track;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MusicRepository extends CrudRepository<Track,Integer> {
}
