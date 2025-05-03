package com.maxexplode;

import com.maxexplode.model.Match;
import com.maxexplode.model.MatchKey;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Scoreboard {
    private final Map<MatchKey, Match> matches = new ConcurrentHashMap<>();

    public void startMatch(String homeTeam, String awayTeam) {
        MatchKey key = generateKey(homeTeam, awayTeam);
        if (matches.containsKey(key)) {
            throw new IllegalArgumentException("Match already exists");
        }
        matches.put(key, new Match(homeTeam, awayTeam));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        Match match = getMatch(homeTeam, awayTeam);
        if (match == null) {
            throw new IllegalArgumentException("Match not found");
        }
        match.updateScore(homeScore, awayScore);
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        MatchKey key = generateKey(homeTeam, awayTeam);
        if (!matches.containsKey(key)) {
            throw new IllegalArgumentException("Match not found");
        }
        matches.remove(key);
    }

    public List<Match> getSummary() {
        List<Match> liveMatches = new ArrayList<>(matches.values());
        liveMatches.sort(Comparator
                .comparingInt(Match::getTotalScore).reversed()
                .thenComparingLong(Match::getStartTime).reversed());
        return liveMatches;
    }

    public Match getMatch(String homeTeam, String awayTeam) {
        return matches.get(generateKey(homeTeam, awayTeam));
    }

    private MatchKey generateKey(String homeTeam, String awayTeam) {
        return new MatchKey(homeTeam, awayTeam);
    }
}
