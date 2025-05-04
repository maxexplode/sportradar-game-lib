package com.maxexplode;

import com.maxexplode.model.MatchKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchKeyTest {

    @Test
    void matchKeysWithSameTeamsAreEqual() {
        MatchKey key1 = new MatchKey("France", "Brazil");
        MatchKey key2 = new MatchKey("France", "Brazil");

        assertEquals(key1, key2, "MatchKeys with same teams should be equal");
        assertEquals(key1.hashCode(), key2.hashCode(), "HashCodes should be equal for equal keys");
    }

    @Test
    void matchKeysWithDifferentTeamsAreNotEqual() {
        MatchKey key1 = new MatchKey("France", "Brazil");
        MatchKey key2 = new MatchKey("Germany", "Brazil");

        assertNotEquals(key1, key2, "MatchKeys with different home teams should not be equal");
    }

    @Test
    void hashCodeIsConsistent() {
        MatchKey key = new MatchKey("Spain", "Italy");

        int initialHash = key.hashCode();
        int repeatedHash = key.hashCode();

        assertEquals(initialHash, repeatedHash, "HashCode should be consistent across invocations");
    }

    @Test
    void matchKeyNotEqualToNullOrDifferentType() {
        MatchKey key = new MatchKey("Argentina", "Australia");

        assertNotEquals(null, key, "MatchKey should not be equal to null");
        assertNotEquals("Argentina-Australia", key, "MatchKey should not be equal to different object type");
    }
}
