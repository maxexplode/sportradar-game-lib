package com.maxexplode.core;

import com.maxexplode.exception.InvalidMatchRequestException;
import com.maxexplode.exception.InvalidMatchStateException;
import com.maxexplode.model.Match;
import com.maxexplode.model.MatchKey;
import com.maxexplode.store.MatchStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Default implementation of the scoreboard using a match store backend.
 * Supports starting, updating, finishing matches, and retrieving live summaries.
 */
public class DefaultScoreboard extends AbstractScoreBoard {

    private static final Logger log = LoggerFactory.getLogger(DefaultScoreboard.class);

    /**
     * Constructs a new scoreboard with the given match store.
     *
     * @param matchStore the storage mechanism for match data
     */
    public DefaultScoreboard(MatchStore matchStore) {
        super(matchStore);
    }

    /**
     * Starts a new match between the given home and away teams.
     * Initializes the score to 0-0 and stores it.
     *
     * @param homeTeam the name of the home team
     * @param awayTeam the name of the away team
     * @throws InvalidMatchStateException if a match already exists
     */
    public void startMatch(String homeTeam, String awayTeam) {
        MatchKey key = generateKey(homeTeam, awayTeam);
        ifMatchAbsent(key, () -> {
            matchStore.put(key, new Match(homeTeam, awayTeam));
            log.info("Started new match: {}", key);
        }, () -> new InvalidMatchStateException("Match %s already exists".formatted(key)));
    }

    /**
     * Updates the score of an existing match.
     *
     * @param homeTeam   the name of the home team
     * @param awayTeam   the name of the away team
     * @param homeScore  the updated score for the home team
     * @param awayScore  the updated score for the away team
     * @throws InvalidMatchRequestException if the match does not exist
     */
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        MatchKey key = generateKey(homeTeam, awayTeam);
        withMatch(key, match -> {
            match.updateScore(homeScore, awayScore);
            log.info("Updated score for {}: {}-{}", key, homeScore, awayScore);
        }, () -> new InvalidMatchRequestException("Match %s not found".formatted(key)));
    }

    /**
     * Finishes an ongoing match and removes it from the scoreboard.
     *
     * @param homeTeam the name of the home team
     * @param awayTeam the name of the away team
     * @throws InvalidMatchRequestException if the match does not exist
     */
    public void finishMatch(String homeTeam, String awayTeam) {
        MatchKey key = generateKey(homeTeam, awayTeam);
        withMatch(key, match -> {
            matchStore.remove(key);
            log.info("Finished match: {}", key);
        }, () -> new InvalidMatchRequestException("Match %s not found".formatted(key)));
    }

    /**
     * Returns a list of all live matches, sorted by total score (descending),
     * then by recency (most recent match first within each score group).
     *
     * @return a sorted list of live matches
     */
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

        if (log.isDebugEnabled()) {
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

    /**
     * Retrieves a specific match by team combination.
     *
     * @param homeTeam the name of the home team
     * @param awayTeam the name of the away team
     * @return an Optional containing the match if it exists
     */
    public Optional<Match> getMatch(String homeTeam, String awayTeam) {
        MatchKey key = generateKey(homeTeam, awayTeam);
        Optional<Match> match = matchStore.get(key);
        log.debug("Lookup for match {} returned: {}", key, match.isPresent() ? "FOUND" : "NOT FOUND");
        return match;
    }

    /**
     * Creates a match key from home and away team names.
     *
     * @param homeTeam the home team name
     * @param awayTeam the away team name
     * @return the generated MatchKey
     */
    private MatchKey generateKey(String homeTeam, String awayTeam) {
        return new MatchKey(homeTeam, awayTeam);
    }
}
