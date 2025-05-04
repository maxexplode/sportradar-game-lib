package com.maxexplode.repository;

import com.maxexplode.model.Match;
import com.maxexplode.model.MatchKey;

import java.util.Collection;
import java.util.Optional;

public interface MatchStore {
    Optional<Match> get(MatchKey key);
    void put(MatchKey key, Match match);
    void remove(MatchKey key);
    boolean contains(MatchKey key);
    Collection<Match> getAll();
}
