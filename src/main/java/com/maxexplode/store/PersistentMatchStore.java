package com.maxexplode.store;

import com.maxexplode.model.Match;
import com.maxexplode.model.MatchKey;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Persistent implementation of {@link MatchStore}, intended to be backed by a database
 * or external storage system such as JPA, JDBC, or a NoSQL store.
 * <p>
 * All methods currently throw {@link UnsupportedOperationException}
 * until properly implemented with a backing repository.
 */
public class PersistentMatchStore implements MatchStore {

    /**
     * Retrieves a match by its key from the persistent store.
     *
     * @param key the match key
     * @return the match if found, otherwise an empty Optional
     * @throws UnsupportedOperationException until implemented
     */
    @Override
    public Optional<Match> get(MatchKey key) {
        // return repository.findById(key);
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Saves or updates a match in the persistent store.
     *
     * @param key   the match key
     * @param match the match to store
     * @throws UnsupportedOperationException until implemented
     */
    @Override
    public void put(MatchKey key, Match match) {
        // repository.save(match);
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Removes a match from the persistent store.
     *
     * @param key the key of the match to remove
     * @throws UnsupportedOperationException until implemented
     */
    @Override
    public void remove(MatchKey key) {
        // repository.deleteById(key);
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Checks if a match exists for the given key in the persistent store.
     *
     * @param key the match key to check
     * @return true if match exists, false otherwise
     * @throws UnsupportedOperationException until implemented
     */
    @Override
    public boolean contains(MatchKey key) {
        // return repository.existsById(key);
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Retrieves all stored matches from the persistent store.
     *
     * @return a collection of matches
     * @throws UnsupportedOperationException until implemented
     */
    @Override
    public Collection<Match> getAll() {
        // return repository.findAll();
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Collection<Match> getAll(Comparator<Match> comparator) {
        // return repository.findAll();
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
