package com.maxexplode;

import com.maxexplode.core.DefaultScoreboard;
import com.maxexplode.exception.InvalidMatchRequestException;
import com.maxexplode.exception.InvalidMatchStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DefaultScoreboardExceptionTest {
    private DefaultScoreboard defaultScoreboard;

    @BeforeEach
    void setUp() {
        defaultScoreboard = new DefaultScoreboard();
    }

    @Test
    void whenStartingExistingMatchThrowsException() {
        defaultScoreboard.startMatch("Team1", "Team2");
        assertThrows(InvalidMatchStateException.class, () ->
            defaultScoreboard.startMatch("Team1", "Team2")
        );
    }

    @Test
    void whenUpdatingNonexistentMatchThrowsException() {
        assertThrows(InvalidMatchRequestException.class, () ->
            defaultScoreboard.updateScore("No", "Match", 1, 1)
        );
    }

    @Test
    void whenFinishingNonexistentMatchThrowsException() {
        assertThrows(InvalidMatchRequestException.class, () ->
            defaultScoreboard.finishMatch("No", "Match")
        );
    }
}
