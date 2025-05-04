package com.maxexplode.repository;

import com.maxexplode.model.Match;
import com.maxexplode.model.MatchKey;

import java.util.Collection;
import java.util.Optional;

public class PersistentMatchStore implements MatchStore {

    @Override
    public Optional<Match> get(MatchKey key) {
        // return repository.findById(key);
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void put(MatchKey key, Match match) {
        // repository.save(match);
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void remove(MatchKey key) {
        // repository.deleteById(key);
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean contains(MatchKey key) {
        // return repository.existsById(key);
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Collection<Match> getAll() {
        // return repository.findAll();
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
