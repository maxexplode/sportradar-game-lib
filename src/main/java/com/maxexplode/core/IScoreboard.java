package com.maxexplode.core;

import com.maxexplode.model.Match;

import java.util.List;

public interface IScoreboard {
    void startMatch(String homeTeam, String awayTeam);
    void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore);
    void finishMatch(String homeTeam, String awayTeam);
    List<Match> getSummary();
    Match getMatch(String homeTeam, String awayTeam);
}
