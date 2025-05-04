package com.maxexplode.core;

import com.maxexplode.exception.InvalidMatchRequestException;
import com.maxexplode.exception.InvalidMatchStateException;
import com.maxexplode.model.Match;
import com.maxexplode.model.MatchKey;
import com.maxexplode.repository.InMemoryMatchStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class DefaultScoreboard extends AbstractScoreBoard {

    private static final Logger log = LoggerFactory.getLogger(DefaultScoreboard.class);

    public DefaultScoreboard() {
        super(new InMemoryMatchStore());
    }

    public void startMatch(String homeTeam, String awayTeam) {
        MatchKey key = generateKey(homeTeam, awayTeam);
        ifMatchAbsent(key, () -> {
            matchStore.put(key, new Match(homeTeam, awayTeam));
            log.info("Started new match: {}", key);
        }, () -> new InvalidMatchStateException("Match %s already exists".formatted(key)));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        MatchKey key = generateKey(homeTeam, awayTeam);
        withMatch(key, match -> {
            match.updateScore(homeScore, awayScore);
            log.info("Updated score for {}: {}-{}", key, homeScore, awayScore);
        }, () -> new InvalidMatchRequestException("Match %s not found".formatted(key)));
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        MatchKey key = generateKey(homeTeam, awayTeam);
        withMatch(key, match -> {
            matchStore.remove(key);
            log.info("Finished match: {}", key);
        }, () -> new InvalidMatchRequestException("Match %s not found".formatted(key)));
    }

    public List<Match> getSummary() {
        List<Match> liveMatches = new ArrayList<>(matchStore.getAll());
        log.debug("Fetched {} matches from store", liveMatches.size());

        liveMatches.sort(Comparator
                .comparingInt(Match::getTotalScore).reversed());

        Map<Integer, List<Match>> sortedGroups = liveMatches.stream()
                .collect(Collectors.groupingBy(
                        Match::getTotalScore,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        if(log.isDebugEnabled()) {
            log.debug("Grouped matches by total score. Distinct score groups: {}", sortedGroups.size());
            sortedGroups.keySet().stream().findFirst()
                    .ifPresent(topScore -> log.debug("Top score group: {}", topScore));
        }

        List<Match> sortedMatches = new ArrayList<>();

        for (Map.Entry<Integer, List<Match>> entry : sortedGroups.entrySet()) {
            List<Match> sortedByRecency = entry.getValue().stream()
                    .sorted(Comparator.comparing(Match::getStartTime).reversed())
                    .toList();
            sortedMatches.addAll(sortedByRecency);
        }

        log.info("Generated match summary. Total sorted matches: {}", sortedMatches.size());
        return sortedMatches;
    }

    public Optional<Match> getMatch(String homeTeam, String awayTeam) {
        MatchKey key = generateKey(homeTeam, awayTeam);
        Optional<Match> match = matchStore.get(key);
        log.debug("Lookup for match {} returned: {}", key, match.isPresent() ? "FOUND" : "NOT FOUND");
        return match;
    }

    private MatchKey generateKey(String homeTeam, String awayTeam) {
        return new MatchKey(homeTeam, awayTeam);
    }
}
