package com.maxexplode;

import com.maxexplode.exception.InvalidMatchRequestException;
import com.maxexplode.exception.InvalidMatchStateException;
import com.maxexplode.model.Match;
import com.maxexplode.model.MatchKey;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Scoreboard {
    private final Map<MatchKey, Match> matches = new ConcurrentHashMap<>();

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

    private void withMatch(MatchKey key, Consumer<Match> ifPresent, Supplier<? extends RuntimeException> ifAbsent) {
        Match match = matches.get(key);
        if (match != null) {
            ifPresent.accept(match);
        } else {
            throw ifAbsent.get();
        }
    }

    private void ifMatchAbsent(MatchKey key, Runnable ifAbsent, Supplier<? extends RuntimeException> ifPresent) {
        if (matches.containsKey(key)) {
            throw ifPresent.get();
        } else {
            ifAbsent.run();
        }
    }
}
