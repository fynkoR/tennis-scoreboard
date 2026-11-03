package pet.project.dao;

import pet.project.entities.Player;

import java.util.Optional;

public interface PlayerDao {
    Optional<Player> findByName(String name);
    void save(Player player);
    boolean existsByName(String name);
}
