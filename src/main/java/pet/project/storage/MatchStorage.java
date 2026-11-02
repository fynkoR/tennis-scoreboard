package pet.project.storage;

import pet.project.exceptions.MatchNotFoundException;
import pet.project.models.ActiveMatch;
import pet.project.models.Match;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MatchStorage {
    private ConcurrentMap<UUID, ActiveMatch> matches;

    public MatchStorage(){
        matches = new ConcurrentHashMap<>();
    }

    public void addMatch(UUID uuid, ActiveMatch activeMatch){
        this.matches.put(uuid, activeMatch);
    }

    public ActiveMatch findMatchByUUID(UUID uuid){
        ActiveMatch match = this.matches.get(uuid);
        if(match == null){
            throw new MatchNotFoundException(uuid);
        }
        return match;
    }

    public ConcurrentMap<UUID, ActiveMatch> getMatches() {
        return matches;
    }

    public void setMatches(ConcurrentHashMap<UUID, ActiveMatch> matches) {
        this.matches = matches;
    }
}
