package com.maxexplode.core;

import com.maxexplode.exception.InvalidMatchRequestException;
import com.maxexplode.exception.InvalidMatchStateException;
import com.maxexplode.model.Match;
import com.maxexplode.model.MatchKey;
import com.maxexplode.repository.InMemoryMatchStore;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultScoreboard extends AbstractScoreBoard {

    public DefaultScoreboard() {
        super(new InMemoryMatchStore());
    }

    public void startMatch(String homeTeam, String awayTeam) {
        MatchKey key = generateKey(homeTeam, awayTeam);
        ifMatchAbsent(key, () -> matchStore.put(key, new Match(homeTeam, awayTeam)),
                () -> new InvalidMatchStateException("Match %s already exists".formatted(key)));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        MatchKey key = generateKey(homeTeam, awayTeam);
        withMatch(key, match -> match.updateScore(homeScore, awayScore),
                () -> new InvalidMatchRequestException("Match %s not found".formatted(key)));
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        MatchKey key = generateKey(homeTeam, awayTeam);
        withMatch(key, match -> matchStore.remove(key),
                () -> new InvalidMatchRequestException("Match %s not found".formatted(key)));
    }

    public List<Match>  getSummary() {
        List<Match> liveMatches = new ArrayList<>(matchStore.getAll());
        liveMatches.sort(Comparator
                .comparingInt(Match::getTotalScore).reversed());
        return liveMatches;
    }

    public Optional<Match> getMatch(String homeTeam, String awayTeam) {
        return matchStore.get(generateKey(homeTeam, awayTeam));
    }

    private MatchKey generateKey(String homeTeam, String awayTeam) {
        return new MatchKey(homeTeam, awayTeam);
    }
}
