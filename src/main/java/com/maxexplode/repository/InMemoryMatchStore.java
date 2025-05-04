package com.maxexplode.repository;

import com.maxexplode.model.Match;
import com.maxexplode.model.MatchKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMatchStore implements MatchStore {

    private static final Logger log = LoggerFactory.getLogger(InMemoryMatchStore.class);

    private final Map<MatchKey, Match> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Match> get(MatchKey key) {
        Match match = store.get(key);
        log.debug("Retrieving match for key {} -> {}", key, match != null ? "FOUND" : "NOT FOUND");
        return Optional.ofNullable(match);
    }

    @Override
    public void put(MatchKey key, Match match) {
        store.put(key, match);
        log.debug("Stored match under key {}: {}", key, match);
    }

    @Override
    public void remove(MatchKey key) {
        store.remove(key);
        log.debug("Removed match for key {}", key);
    }

    @Override
    public boolean contains(MatchKey key) {
        boolean exists = store.containsKey(key);
        log.debug("Checking existence of key {}: {}", key, exists);
        return exists;
    }

    @Override
    public Collection<Match> getAll() {
        log.debug("Retrieving all matches: count = {}", store.size());
        return store.values();
    }
}
