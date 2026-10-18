package pet.project.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    public List<Player> findAll(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Player p", Player.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Player find(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.find(Player.class, id);
    }

    @Transactional(readOnly = true)
    public Optional<Player> findByName(String name){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Player WHERE name =:name", Player.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }

    @Transactional
    public void save(Player player){
        Session session = sessionFactory.getCurrentSession();
        session.persist(player);
    }
}
