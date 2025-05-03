package com.maxexplode.model;

import java.util.Objects;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore = 0;
    private int awayScore = 0;
    private final long startTime;

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = Objects.requireNonNull(homeTeam);
        this.awayTeam = Objects.requireNonNull(awayTeam);
        this.startTime = System.nanoTime();
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public long getStartTime() {
        return startTime;
    }

    public void updateScore(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public void updateScoreDelta(int homeDelta, int awayDelta) {
        int newHome = this.homeScore + homeDelta;
        int newAway = this.awayScore + awayDelta;

        if (newHome < 0 || newAway < 0) {
            throw new IllegalArgumentException("Resulting score cannot be negative");
        }

        this.homeScore = newHome;
        this.awayScore = newAway;
    }

    public int getTotalScore() {
        return homeScore + awayScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match other)) return false;
        return homeTeam.equals(other.homeTeam) && awayTeam.equals(other.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam);
    }

    @Override
    public String toString() {
        return "%s %d - %d %s".formatted(homeTeam, homeScore, awayScore, awayTeam);
    }
}
