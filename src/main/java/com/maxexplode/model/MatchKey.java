package com.maxexplode.model;

import java.util.Objects;

public record MatchKey(String homeTeam, String awayTeam) {


    //Location
    //Team Category

    //Addition or attrs should not change the behavior of hash and equals unintentionally

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatchKey other)) return false;
        return Objects.equals(homeTeam, other.homeTeam) &&
                Objects.equals(awayTeam, other.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam);
    }

}
