package pet.project.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pet.project.entities.Player;

import java.util.Optional;

@Repository
public class PlayerDaoImpl implements PlayerDao {

    private final SessionFactory sessionFactory;
    private static final String FIND_BY_NAME = "FROM Player WHERE name =:name";

    @Autowired
    public PlayerDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Player> findByName(String name){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_BY_NAME, Player.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }

    public void save(Player player){
        Session session = sessionFactory.getCurrentSession();
        session.persist(player);
    }
}
