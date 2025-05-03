package com.maxexplode;

import com.maxexplode.exception.InvalidMatchRequestException;
import com.maxexplode.exception.InvalidMatchStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScoreboardExceptionTest {
    private Scoreboard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new Scoreboard();
    }

    @Test
    void whenStartingExistingMatchThrowsException() {
        scoreboard.startMatch("Team1", "Team2");
        assertThrows(InvalidMatchStateException.class, () ->
            scoreboard.startMatch("Team1", "Team2")
        );
    }

    @Test
    void whenUpdatingNonexistentMatchThrowsException() {
        assertThrows(InvalidMatchRequestException.class, () ->
            scoreboard.updateScore("No", "Match", 1, 1)
        );
    }

    @Test
    void whenFinishingNonexistentMatchThrowsException() {
        assertThrows(InvalidMatchRequestException.class, () ->
            scoreboard.finishMatch("No", "Match")
        );
    }
}
