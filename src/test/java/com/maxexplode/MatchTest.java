package com.maxexplode;

import com.maxexplode.model.Match;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    @Test
    void equalsAndHashCodeBasedOnTeams() {
        Match m1 = new Match("TeamA", "TeamB");
        Match m2 = new Match("TeamA", "TeamB");
        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    void totalScoreIsSumOfHomeAndAway() {
        Match match = new Match("T1", "T2");
        match.updateScore(5, 6);
        assertEquals(11, match.getTotalScore());
    }
}
