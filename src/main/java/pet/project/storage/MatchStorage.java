package pet.project.storage;

import org.springframework.stereotype.Component;
import pet.project.exceptions.MatchIsNotCompletedException;
import pet.project.exceptions.MatchNotFoundException;
import pet.project.models.ActiveMatch;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class MatchStorage {
    private final ConcurrentMap<UUID, ActiveMatch> matches;

    public MatchStorage(){
        matches = new ConcurrentHashMap<>();
    }

    public UUID addMatch(ActiveMatch activeMatch){
        UUID uuid = UUID.randomUUID();
        matches.put(uuid, activeMatch);

        return uuid;
    }

    public ActiveMatch findMatchByUUID(UUID uuid){
        ActiveMatch match = matches.get(uuid);
        if(match == null){
            throw new MatchNotFoundException(uuid);
        }
        return match;
    }

    public void deleteFinishedMatch(ActiveMatch activeMatch){
        if(activeMatch.isMatchCompleted()){
            matches.entrySet().removeIf(entry -> entry.getValue().equals(activeMatch));
        }
        else{
            throw new MatchIsNotCompletedException();
        }

        //если по uuid: matches.remove(uuid);
    }
}
