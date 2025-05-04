package com.maxexplode.store;

import com.maxexplode.model.Match;
import com.maxexplode.model.MatchKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of {@link MatchStore} using a thread-safe {@link ConcurrentHashMap}.
 * Primarily intended for testing, local use, or transient storage.
 */
public class InMemoryMatchStore implements MatchStore {

    private static final Logger log = LoggerFactory.getLogger(InMemoryMatchStore.class);

    private final Map<MatchKey, Match> store = new ConcurrentHashMap<>();

    /**
     * Retrieves a match for the specified key.
     *
     * @param key the match key (home and away team combination)
     * @return an Optional containing the match if present, otherwise empty
     */
    @Override
    public Optional<Match> get(MatchKey key) {
        Match match = store.get(key);
        log.debug("Retrieving match for key {} -> {}", key, match != null ? "FOUND" : "NOT FOUND");
        return Optional.ofNullable(match);
    }

    /**
     * Stores a match associated with the given key.
     * If a match already exists for the key, it is replaced.
     *
     * @param key   the match key
     * @param match the match to store
     */
    @Override
    public void put(MatchKey key, Match match) {
        store.put(key, match);
        log.debug("Stored match under key {}: {}", key, match);
    }

    /**
     * Removes the match associated with the given key, if it exists.
     *
     * @param key the match key to remove
     */
    @Override
    public void remove(MatchKey key) {
        store.remove(key);
        log.debug("Removed match for key {}", key);
    }

    /**
     * Checks whether a match exists for the given key.
     *
     * @param key the match key to check
     * @return true if a match exists for the key, false otherwise
     */
    @Override
    public boolean contains(MatchKey key) {
        boolean exists = store.containsKey(key);
        log.debug("Checking existence of key {}: {}", key, exists);
        return exists;
    }

    /**
     * Returns all stored matches currently in memory.
     *
     * @return a collection of all live matches
     */
    @Override
    public Collection<Match> getAll() {
        log.debug("Retrieving all matches: count = {}", store.size());
        return store.values();
    }
    /**
     * Returns all stored matches currently in memory sorted by input comparator.
     *
     * @return a collection of all live matches
     */
    @Override
    public Collection<Match> getAll(Comparator<Match> comparator) {
        log.debug("Retrieving all matches: count = {}", store.size());
        return store.values().stream().sorted(comparator).collect(Collectors.toList());
    }
}
