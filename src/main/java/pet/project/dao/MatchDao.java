package pet.project.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pet.project.entities.Matches;

import java.util.List;

@Component
public class MatchDao {

    private final SessionFactory sessionFactory;
    private static final String SQL_FIND_FINISHED_MATCHES = """
            SELECT m FROM Matches m
         JOIN FETCH m.player1
         JOIN FETCH m.player2
         JOIN FETCH m.winner
         """;
    private static final String SQL_FIND_FINISHED_MATCHES_BY_NAME ="""
             WHERE m.player1.name =:playerName
            OR m.player2.name =:playerName
            """;
    private static final String SQL_COUNT_FINISHED_MATCHES = "SELECT COUNT(m) FROM Matches m";
    private static final String SQL_SORT = " ORDER BY m.id ASC";

    @Autowired
    public MatchDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Matches matches){
        Session session = sessionFactory.getCurrentSession();
        session.persist(matches);
    }

    public List<Matches> findFinishedMatches(int limit, int offset){
        Session session = sessionFactory.getCurrentSession();
        Query<Matches> query = session.createQuery(SQL_FIND_FINISHED_MATCHES + SQL_SORT, Matches.class);
        query.setMaxResults(limit);
        query.setFirstResult(offset);
        return query.getResultList();
    }

    public List<Matches> findFinishedMatchesByPlayerName(int limit, int offset, String playerName){
        Session session = sessionFactory.getCurrentSession();
        Query<Matches> query = session.createQuery(SQL_FIND_FINISHED_MATCHES + SQL_FIND_FINISHED_MATCHES_BY_NAME + SQL_SORT, Matches.class);
        query.setParameter("playerName", playerName);
        query.setMaxResults(limit);
        query.setFirstResult(offset);
        return query.getResultList();
    }

    public Long countByFindFinishedMatches(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(SQL_COUNT_FINISHED_MATCHES, Long.class).getSingleResult();
    }

    public Long countByFindFinishedMatchesByPlayerName(String playerName){
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery(SQL_COUNT_FINISHED_MATCHES + SQL_FIND_FINISHED_MATCHES_BY_NAME, Long.class);
        query.setParameter("playerName", playerName);
        return query.getSingleResult();
    }
}
