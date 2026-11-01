package pet.project.dao;

import pet.project.entities.Matches;

import java.util.List;

public interface MatchDao {
    void save(Matches matches);
    List<Matches> findFinishedMatches(int limit, int offset);
    List<Matches> findFinishedMatchesByPlayerName(int limit, int offset, String playerName);
    Long countAll();
    Long countAllByPlayerName(String playerName);
}
