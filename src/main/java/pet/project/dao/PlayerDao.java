package pet.project.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pet.project.entities.Player;

import java.util.List;
import java.util.Optional;

@Component
public class PlayerDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public PlayerDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Player> findAll(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Player p", Player.class).getResultList();
    }

    public Optional<Player> findByName(String name){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Player WHERE name =:name", Player.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }

    public void save(Player player){
        Session session = sessionFactory.getCurrentSession();
        session.persist(player);
    }
}
