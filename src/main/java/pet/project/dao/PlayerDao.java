package pet.project.dao;

import pet.project.entities.Player;

import java.util.Optional;

public interface PlayerDao {
    boolean existsByName(String name);
    Optional<Player> findByName(String name);
    void save(Player player);
}
