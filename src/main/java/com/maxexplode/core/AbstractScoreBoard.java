package com.maxexplode.core;

import com.maxexplode.model.Match;
import com.maxexplode.model.MatchKey;
import com.maxexplode.repository.MatchStore;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractScoreBoard implements IScoreboard {

    protected final MatchStore matchStore;

    protected AbstractScoreBoard(MatchStore matchStore) {
        this.matchStore = matchStore;
    }

    protected void withMatch(MatchKey key, Consumer<Match> ifPresent, Supplier<? extends RuntimeException> ifAbsent) {
        Optional<Match> match = matchStore.get(key);
        if (match.isPresent()) {
            ifPresent.accept(match.get());
        } else {
            throw ifAbsent.get();
        }
    }

    protected void ifMatchAbsent(MatchKey key, Runnable ifAbsent, Supplier<? extends RuntimeException> ifPresent) {
        if (matchStore.contains(key)) {
            throw ifPresent.get();
        } else {
            ifAbsent.run();
        }
    }

}

