package com.maxexplode.core;

import com.maxexplode.model.Match;

import java.util.List;
import java.util.Optional;

public interface IScoreboard {
    void startMatch(String homeTeam, String awayTeam);
    void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore);
    void finishMatch(String homeTeam, String awayTeam);
    List<Match> getSummary();
    Optional<Match> getMatch(String homeTeam, String awayTeam);
}
