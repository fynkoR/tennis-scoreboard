package pet.project.storage;

import pet.project.exceptions.MatchNotFoundException;
import pet.project.models.Match;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MatchStorage {
    private ConcurrentMap<UUID, Match> matches;

    public MatchStorage(){
        matches = new ConcurrentHashMap<>();
    }

    public void addMatch(UUID uuid, Match match){
        this.matches.put(uuid, match);
    }

    public Match findMatchByUUID(UUID uuid){
        Match match = this.matches.get(uuid);
        if(match == null){
            throw new MatchNotFoundException(uuid);
        }
        return match;
    }

    public ConcurrentMap<UUID, Match> getMatches() {
        return matches;
    }

    public void setMatches(ConcurrentHashMap<UUID, Match> matches) {
        this.matches = matches;
    }
}
