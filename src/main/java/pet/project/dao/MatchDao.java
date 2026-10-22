package pet.project.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
    private static final int LIMIT_VALUE = 2;

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
    public List<Matches> findFinishedMatches(int page){
        Session session = sessionFactory.getCurrentSession();
        Query<Matches> query = session.createQuery("""
        SELECT m FROM Matches m
         JOIN FETCH m.player1
         JOIN FETCH m.player2
         JOIN FETCH m.winner
         ORDER BY m.id
""", Matches.class);
        query.setMaxResults(LIMIT_VALUE);
        query.setFirstResult(page * LIMIT_VALUE);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<Matches> findFinishedMatchesByPlayerName(int page, String playerName){
        Session session = sessionFactory.getCurrentSession();
        Query<Matches> query = session.createQuery("""
        SELECT m FROM Matches m
        JOIN FETCH m.player1
        JOIN FETCH m.player2
        JOIN FETCH m.winner
        WHERE m.player1.name =:playerName
        OR m.player2.name =:playerName
        ORDER BY m.id
""", Matches.class);
        query.setParameter("playerName", playerName);
        query.setMaxResults(LIMIT_VALUE);
        query.setFirstResult(page * LIMIT_VALUE);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public Long countByFindFinishedMatchesByPlayerName(String playerName){
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery("SELECT COUNT(m) FROM Matches m WHERE m.player1.name =:playerName " +
                "OR m.player2.name =:playerName", Long.class);
        query.setParameter("playerName", playerName);
        return query.getSingleResult();
    }

    @Transactional(readOnly = true)
    public Long countByFindFinishedMatches(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT COUNT(m) FROM Matches m", Long.class).getSingleResult();
    }
}
