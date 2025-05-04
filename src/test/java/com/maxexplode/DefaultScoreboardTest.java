package com.maxexplode;

import com.maxexplode.core.DefaultScoreboard;
import com.maxexplode.model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DefaultScoreboardTest {
    private DefaultScoreboard defaultScoreboard;

    @BeforeEach
    void setUp() {
        defaultScoreboard = new DefaultScoreboard();
    }

    @Test
    void whenStartNewMatchInitializesScoreZeroZero() {
        defaultScoreboard.startMatch("Home", "Away");
        Match match = defaultScoreboard.getMatch("Home", "Away");
        assertNotNull(match);
        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }

    @Test
    void whenUpdateScoreChangesScores() {
        defaultScoreboard.startMatch("A", "B");
        defaultScoreboard.updateScore("A", "B", 2, 3);
        Match match = defaultScoreboard.getMatch("A", "B");
        assertEquals(2, match.getHomeScore());
        assertEquals(3, match.getAwayScore());
    }

    @Test
    void whenFinishMatchRemovesItFromScoreboard() {
        defaultScoreboard.startMatch("X", "Y");
        defaultScoreboard.finishMatch("X", "Y");
        assertNull(defaultScoreboard.getMatch("X", "Y"));
    }

    @Test
    void getSummaryOrdersByTotalScoreAndRecency() {
    }
}
