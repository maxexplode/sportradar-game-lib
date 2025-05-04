package com.maxexplode.core;

import com.maxexplode.exception.InvalidMatchRequestException;
import com.maxexplode.exception.InvalidMatchStateException;
import com.maxexplode.model.Match;
import com.maxexplode.model.MatchKey;
import com.maxexplode.repository.InMemoryMatchStore;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultScoreboard extends AbstractScoreBoard {
    private final Map<MatchKey, Match> matches = new ConcurrentHashMap<>();

    public DefaultScoreboard() {
        super(new InMemoryMatchStore());
    }

    public void startMatch(String homeTeam, String awayTeam) {
        MatchKey key = generateKey(homeTeam, awayTeam);
        ifMatchAbsent(key, () -> matches.put(key, new Match(homeTeam, awayTeam)),
                () -> new InvalidMatchStateException("Match %s already exists".formatted(key)));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        MatchKey key = generateKey(homeTeam, awayTeam);
        withMatch(key, match -> match.updateScore(homeScore, awayScore),
                () -> new InvalidMatchRequestException("Match %s not found".formatted(key)));
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        MatchKey key = generateKey(homeTeam, awayTeam);
        withMatch(key, match -> matches.remove(key),
                () -> new InvalidMatchRequestException("Match %s not found".formatted(key)));
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
