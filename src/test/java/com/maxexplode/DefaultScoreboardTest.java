package com.maxexplode;

import com.maxexplode.core.DefaultScoreboard;
import com.maxexplode.exception.InvalidMatchStateException;
import com.maxexplode.model.Match;
import com.maxexplode.store.InMemoryMatchStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DefaultScoreboardTest {
    private DefaultScoreboard defaultScoreboard;

    @BeforeEach
    void setUp() {
        defaultScoreboard = new DefaultScoreboard(new InMemoryMatchStore());
    }

    @Test
    void whenStartNewMatchInitializesScoreZeroZero() {
        defaultScoreboard.startMatch("Home", "Away");
        Optional<Match> match = defaultScoreboard.getMatch("Home", "Away");
        assertTrue(match.isPresent());
        assertEquals(0, match.get().getHomeScore());
        assertEquals(0, match.get().getAwayScore());
    }

    @Test
    void whenUpdateScoreChangesScores() {
        defaultScoreboard.startMatch("A", "B");
        defaultScoreboard.updateScore("A", "B", 2, 3);
        Optional<Match> match = defaultScoreboard.getMatch("A", "B");
        assertTrue(match.isPresent());
        assertEquals(2, match.get().getHomeScore());
        assertEquals(3, match.get().getAwayScore());
    }

    @Test
    void whenFinishMatchRemovesItFromScoreboard() {
        defaultScoreboard.startMatch("X", "Y");
        defaultScoreboard.finishMatch("X", "Y");
        Optional<Match> match = defaultScoreboard.getMatch("X", "Y");
        assertFalse(match.isPresent());
    }

    @Test
    void getSummaryOrdersByTotalScore() {
        defaultScoreboard.startMatch("A", "B");
        defaultScoreboard.updateScore("A", "B", 1, 1);

        defaultScoreboard.startMatch("C", "D");
        defaultScoreboard.updateScore("C", "D", 3, 2);

        defaultScoreboard.startMatch("E", "F");
        defaultScoreboard.updateScore("E", "F", 4, 3);

        List<Match> summary = defaultScoreboard.getSummary();

        assertEquals(3, summary.size());

        assertEquals("E", summary.get(0).getHomeTeam());
        assertEquals("F", summary.get(0).getAwayTeam());

        assertEquals("C", summary.get(1).getHomeTeam());
        assertEquals("D", summary.get(1).getAwayTeam());

        assertEquals("A", summary.get(2).getHomeTeam());
        assertEquals("B", summary.get(2).getAwayTeam());
    }

    @Test
    void getSummaryOrdersByRecencyWhenTotalScoreIsSame() {
        defaultScoreboard.startMatch("A", "B");
        defaultScoreboard.updateScore("A", "B", 1, 1);

        defaultScoreboard.startMatch("C", "D");
        defaultScoreboard.updateScore("C", "D", 3, 2);

        defaultScoreboard.startMatch("E", "F");
        defaultScoreboard.updateScore("E", "F", 3, 2);

        List<Match> summary = defaultScoreboard.getSummary();

        assertEquals(3, summary.size());

        assertEquals("E", summary.get(0).getHomeTeam());
        assertEquals("F", summary.get(0).getAwayTeam());

        assertEquals("C", summary.get(1).getHomeTeam());
        assertEquals("D", summary.get(1).getAwayTeam());

        assertEquals("A", summary.get(2).getHomeTeam());
        assertEquals("B", summary.get(2).getAwayTeam());
    }

    @Test
    void shouldThrowWhenOneTeamIsAlreadyInAnotherMatch() {
        defaultScoreboard.startMatch("A", "B");

        assertThrows(InvalidMatchStateException.class, () ->
                defaultScoreboard.startMatch("B", "C"));

        assertThrows(InvalidMatchStateException.class, () ->
                defaultScoreboard.startMatch("A", "D"));

        defaultScoreboard.startMatch("C", "D");
    }
}
