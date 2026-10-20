package pet.project.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pet.project.dto.MatchDto;
import pet.project.entities.Matches;
import pet.project.models.Match;

import java.util.List;

@Component
public class MatchDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public MatchDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void save(Matches matches){
        Session session = sessionFactory.getCurrentSession();
        session.persist(matches);
    }

    @Transactional(readOnly = true)
    public List<Matches> findAll(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT m FROM Matches m", Matches.class).getResultList();
    }
}
