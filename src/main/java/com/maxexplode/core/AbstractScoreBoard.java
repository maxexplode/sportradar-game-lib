package com.maxexplode.core;

import com.maxexplode.model.Match;
import com.maxexplode.model.MatchKey;
import com.maxexplode.store.MatchStore;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Abstract base class for scoreboard implementations.
 * Provides shared utility methods for working with the match store.
 */
public abstract class AbstractScoreBoard implements IScoreboard {

    final Set<String> activeTeams = ConcurrentHashMap.newKeySet();

    /**
     * The underlying store for match data.
     */
    protected final MatchStore matchStore;

    /**
     * Constructs the scoreboard with the given match store.
     *
     * @param matchStore the match store implementation (in-memory or persistent)
     */
    protected AbstractScoreBoard(MatchStore matchStore) {
        this.matchStore = matchStore;
    }

    /**
     * Executes the given action if a match is found for the provided key,
     * or throws an exception from the given supplier if not.
     *
     * @param key       the key to look up
     * @param ifPresent consumer to apply if match exists
     * @param ifAbsent  exception supplier if match does not exist
     */
    protected void withMatch(MatchKey key,
                             Consumer<Match> ifPresent,
                             Supplier<? extends RuntimeException> ifAbsent) {
        Optional<Match> match = matchStore.get(key);
        if (match.isPresent()) {
            ifPresent.accept(match.get());
        } else {
            throw ifAbsent.get();
        }
    }

    /**
     * Executes the given action if the match does not exist for the key,
     * or throws an exception from the given supplier if it already exists.
     *
     * @param key        the match key to check
     * @param ifAbsent   runnable to execute if match does not exist
     * @param ifPresent  exception supplier if match already exists
     */
    protected void ifMatchAbsent(MatchKey key,
                                 Runnable ifAbsent,
                                 Supplier<? extends RuntimeException> ifPresent) {
        boolean teamConflict = activeTeams.contains(key.homeTeam()) || activeTeams.contains(key.awayTeam());

        if (matchStore.contains(key) || teamConflict) {
            throw ifPresent.get();
        } else {
            ifAbsent.run();
        }
    }
}
