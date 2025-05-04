package com.maxexplode.store;

import com.maxexplode.model.Match;
import com.maxexplode.model.MatchKey;

import java.util.Collection;
import java.util.Optional;

/**
 * Abstraction for storing and retrieving live match data.
 * Implementations may be in-memory, persistent, or distributed.
 */
public interface MatchStore {

    /**
     * Retrieves a match by its unique key.
     *
     * @param key the key representing the match (home and away teams)
     * @return an Optional containing the match if found, otherwise empty
     */
    Optional<Match> get(MatchKey key);

    /**
     * Saves or updates the match in the store.
     *
     * @param key the key representing the match
     * @param match the match to store
     */
    void put(MatchKey key, Match match);

    /**
     * Removes the match from the store.
     *
     * @param key the key representing the match to remove
     */
    void remove(MatchKey key);

    /**
     * Checks whether a match with the given key exists.
     *
     * @param key the key to check for existence
     * @return true if the match exists, false otherwise
     */
    boolean contains(MatchKey key);

    /**
     * Retrieves all currently stored matches.
     *
     * @return a collection of all matches
     */
    Collection<Match> getAll();
}
