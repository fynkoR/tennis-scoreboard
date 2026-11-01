package pet.project.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pet.project.entities.Matches;

import java.util.List;

@Repository
public class MatchDaoImpl implements MatchDao{

    private static final String FIND_FINISHED_MATCHES =
            """
            SELECT m FROM Matches m
         JOIN FETCH m.player1
         JOIN FETCH m.player2
         JOIN FETCH m.winner
         """;
    private static final String FIND_FINISHED_MATCHES_BY_NAME =
             """
             WHERE m.player1.name =:playerName
            OR m.player2.name =:playerName
            """;
    private static final String COUNT_FINISHED_MATCHES = "SELECT COUNT(m) FROM Matches m";
    private static final String SORT = " ORDER BY m.id DESC";

    private final SessionFactory sessionFactory;

    @Autowired
    public MatchDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Matches matches){
        Session session = sessionFactory.getCurrentSession();
        session.persist(matches);
    }

    @Override
    public List<Matches> findFinishedMatches(int limit, int offset){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_FINISHED_MATCHES + SORT, Matches.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public List<Matches> findFinishedMatchesByPlayerName(int limit, int offset, String playerName){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_FINISHED_MATCHES + FIND_FINISHED_MATCHES_BY_NAME + SORT, Matches.class)
                .setParameter("playerName", playerName)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Long countAll(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(COUNT_FINISHED_MATCHES, Long.class).getSingleResult();
    }

    @Override
    public Long countAllByPlayerName(String playerName){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(COUNT_FINISHED_MATCHES + FIND_FINISHED_MATCHES_BY_NAME, Long.class)
                .setParameter("playerName", playerName)
                .getSingleResult();
    }
}
