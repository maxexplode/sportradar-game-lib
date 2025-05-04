package com.maxexplode.core;

import com.maxexplode.model.Match;

import java.util.List;
import java.util.Optional;

/**
 * Interface defining core operations for a live match scoreboard.
 * Supports starting matches, updating scores, finishing matches, and retrieving summaries.
 */
public interface IScoreboard {

    /**
     * Starts a new match between the given teams.
     * Initializes the match with a 0-0 score.
     *
     * @param homeTeam the name of the home team
     * @param awayTeam the name of the away team
     * @throws com.maxexplode.exception.InvalidMatchStateException if a match between the given teams already exists
     */
    void startMatch(String homeTeam, String awayTeam);

    /**
     * Updates the score for an existing match.
     *
     * @param homeTeam  the name of the home team
     * @param awayTeam  the name of the away team
     * @param homeScore the updated score for the home team
     * @param awayScore the updated score for the away team
     * @throws com.maxexplode.exception.InvalidMatchRequestException if the match is not found or scores are invalid
     */
    void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore);

    /**
     * Finishes a match and removes it from the scoreboard.
     *
     * @param homeTeam the name of the home team
     * @param awayTeam the name of the away team
     * @throws com.maxexplode.exception.InvalidMatchRequestException if the match is not found
     */
    void finishMatch(String homeTeam, String awayTeam);

    /**
     * Retrieves a list of all currently active matches,
     * sorted by total score (descending), then by most recent start time.
     *
     * @return a sorted list of live matches
     */
    List<Match> getSummary();

    /**
     * Retrieves a match by the participating teams.
     *
     * @param homeTeam the name of the home team
     * @param awayTeam the name of the away team
     * @return an Optional containing the match if found, otherwise empty
     */
    Optional<Match> getMatch(String homeTeam, String awayTeam);
}
