package com.maxexplode;

import com.maxexplode.core.DefaultScoreboard;
import com.maxexplode.core.IScoreboard;
import com.maxexplode.store.InMemoryMatchStore;
import com.maxexplode.store.MatchStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScoreBoardBuilder {

    private static final Logger log = LoggerFactory.getLogger(ScoreBoardBuilder.class);

    private MatchStore matchStore;
    private IScoreboard scoreboard;

    /**
     * Use a custom match store (e.g., persistent store).
     */
    public ScoreBoardBuilder withMatchStore(MatchStore matchStore) {
        this.matchStore = matchStore;
        return this;
    }

    /**
     * Provide a pre-initialized scoreboard directly.
     * If this is set, matchStore will be ignored in `build()`.
     */
    public ScoreBoardBuilder withScoreBoard(IScoreboard scoreboard) {
        this.scoreboard = scoreboard;
        return this;
    }

    /**
     * Finalize the build. Defaults to InMemoryMatchStore + DefaultScoreboard if nothing provided.
     */
    public IScoreboard build() {
        if (scoreboard != null) {
            log.debug("Returning provided scoreboard instance.");
            return scoreboard;
        }

        if (matchStore == null) {
            log.info("No MatchStore provided. Using InMemoryMatchStore.");
            matchStore = new InMemoryMatchStore();
        } else {
            log.info("Using custom MatchStore: {}", matchStore.getClass().getSimpleName());
        }

        IScoreboard defaultScoreboard = new DefaultScoreboard(matchStore);
        log.info("Created DefaultScoreboard with {}", matchStore.getClass().getSimpleName());
        return defaultScoreboard;
    }
}
