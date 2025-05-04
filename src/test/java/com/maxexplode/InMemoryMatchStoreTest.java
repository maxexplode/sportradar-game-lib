package com.maxexplode;

import com.maxexplode.model.Match;
import com.maxexplode.model.MatchKey;
import com.maxexplode.store.InMemoryMatchStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryMatchStoreTest {

    private InMemoryMatchStore store;
    private MatchKey key;
    private Match match;

    @BeforeEach
    void setUp() {
        store = new InMemoryMatchStore();
        key = new MatchKey("TeamA", "TeamB");
        match = new Match("TeamA", "TeamB");
    }

    @Test
    void shouldPutAndGetMatch() {
        store.put(key, match);
        Optional<Match> result = store.get(key);
        assertTrue(result.isPresent());
        assertEquals(match, result.get());
    }

    @Test
    void shouldReturnEmptyWhenMatchNotFound() {
        assertTrue(store.get(key).isEmpty());
    }

    @Test
    void shouldContainMatchAfterPut() {
        store.put(key, match);
        assertTrue(store.contains(key));
    }

    @Test
    void shouldRemoveMatch() {
        store.put(key, match);
        store.remove(key);
        assertFalse(store.contains(key));
    }

    @Test
    void shouldReturnAllMatches() {
        store.put(key, match);
        store.put(new MatchKey("TeamC", "TeamD"), new Match("TeamC", "TeamD"));
        assertEquals(2, store.getAll().size());
    }
}
