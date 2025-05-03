package com.maxexplode;

import com.maxexplode.model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScoreboardTest {
    private Scoreboard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new Scoreboard();
    }

    @Test
    void whenStartNewMatchInitializesScoreZeroZero() {
        scoreboard.startMatch("Home", "Away");
        Match match = scoreboard.getMatch("Home", "Away");
        assertNotNull(match);
        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }

    @Test
    void whenUpdateScoreChangesScores() {
        scoreboard.startMatch("A", "B");
        scoreboard.updateScore("A", "B", 2, 3);
        Match match = scoreboard.getMatch("A", "B");
        assertEquals(2, match.getHomeScore());
        assertEquals(3, match.getAwayScore());
    }

    @Test
    void whenFinishMatchRemovesItFromScoreboard() {
        scoreboard.startMatch("X", "Y");
        scoreboard.finishMatch("X", "Y");
        assertNull(scoreboard.getMatch("X", "Y"));
    }

    @Test
    void getSummaryOrdersByTotalScoreAndRecency() {
    }
}
