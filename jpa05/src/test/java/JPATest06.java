import static org.junit.Assert.assertEquals;

import model.Member;
import model.Team;
import org.junit.Test;

/**
 * @author Heo, Jin Han
 * @since 2018-02-14
 */
public class JPATest06 extends HibernateTest {

  @Test
  public void test_save_member () {
    em.getTransaction().begin();

    Team team = new Team("team1", "팀1");
    em.persist(team);

    Team team2 = new Team("team2", "팀2");
    em.persist(team2);

    Member member = new Member("member1", "test1");
    member.setTeam(team);
    member.setTeam(team2);
    em.persist(member);

    Member member2 = new Member("member2", "test2");
    member2.setTeam(team2);
    em.persist(member2);

    assertEquals(team.getMembers().size(), 0);
    assertEquals(team2.getMembers().size(), 2);

    em.getTransaction().commit();
  }
}
