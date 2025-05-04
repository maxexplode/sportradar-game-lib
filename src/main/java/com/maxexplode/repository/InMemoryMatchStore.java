package com.maxexplode.repository;

import com.maxexplode.model.Match;
import com.maxexplode.model.MatchKey;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMatchStore implements MatchStore {

    private final Map<MatchKey, Match> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Match> get(MatchKey key) {
        return Optional.ofNullable(store.get(key));
    }

    @Override
    public void put(MatchKey key, Match match) {
        store.put(key, match);
    }

    @Override
    public void remove(MatchKey key) {
        store.remove(key);
    }

    @Override
    public boolean contains(MatchKey key) {
        return store.containsKey(key);
    }

    @Override
    public Collection<Match> getAll() {
        return store.values();
    }
}
