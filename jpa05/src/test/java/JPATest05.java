import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import model.Member;
import model.Team;
import org.junit.Test;

/**
 * @author Heo, Jin Han
 * @since 2018-02-11
 */
public class JPATest05 extends HibernateTest {

  @Test
  public void test_member_save() {
     em.getTransaction().begin();

    // 팀1 저장
    Team team1 = new Team("team1", "팀1");
    em.persist(team1);

    // 회원1 저장
    Member member1 = new Member("member1", "회원1");

    /**
     *   insert into MEMBER
     *     (TEAM_ID, username, MEMBER_ID)
     *   values
     *     (?, ?, ?)
     *
     *  MEMBER에 insert하기 위해서는 TEAM_ID가 필요한데
     *  영속관계에서 setTeam을 하게 되면 참조한 팀의 id를 외래키로 사용해서
     *  적절한 등록쿼리를 생성해준다.
     */
    member1.setTeam(team1); // 연관관계 설정 member1 -> team1
    em.persist(member1);

    Member member2 = new Member("member2", "회원2");
    member2.setTeam(team1);
    em.persist(member2);

    assertNotNull(member1.getTeam());
    assertNotNull(member2.getTeam());

     em.getTransaction().commit();
  }

  @Test
  public void test_member_JPQL_query() {
    em.getTransaction().begin();

    Team team1 = new Team("team1", "팀1");
    em.persist(team1);

    Member member = new Member("member1", "테스트");
    member.setTeam(team1);
    em.persist(member);

    em.getTransaction().commit();

    String jpql = "select m from Member m join m.team t where t.name=:teamName";

    List<Member> resultList = em.createQuery(jpql, Member.class)
        .setParameter("teamName", "팀1")
        .getResultList();

    assertEquals(resultList.size(), 1);
  }

  @Test
  public void test_update_relation() {
    em.getTransaction().begin();

    Team team1 = new Team("team1", "팀1");
    em.persist(team1);

    Member member = new Member("member1", "테스트");
    member.setTeam(team1);
    em.persist(member);

    Team team2 = new Team("team2", "팀2");
    em.persist(team2);

    Member findMember = em.find(Member.class, "member1");
    findMember.setTeam(team2);

    em.getTransaction().commit();

    Member findMember2 = em.find(Member.class, "member1");

    assertEquals(findMember2.getTeam().getId(), "team2");
  }

  @Test
  public void test_delete_relation() {
    em.getTransaction().begin();

    Team team = new Team("team1", "team1");
    em.persist(team);

    Member member = new Member("member1", "테스트");
    member.setTeam(team);
    em.persist(member);

    // team1을 삭제하기 위해서는 기존의 member와의 연관관계를 삭제하고 team1을 삭제한다.
    member.setTeam(null);
    em.remove(team);

    Team team1 = em.find(Team.class, "team1");

    assertNull(team1);

    em.getTransaction().commit();
  }

  @Test
  public void test_bi_direction() {
    em.getTransaction().begin();

    Team team = new Team("team1", "team1");
    em.persist(team);

    Member member = new Member("member1", "test1");
    member.setTeam(team);
    em.persist(member);

    Member member2 = new Member("member2", "test2");
    member2.setTeam(team);
    em.persist(member2);

    Team findTeam = em.find(Team.class, "team1");
    List<Member> members = findTeam.getMembers();

    assertEquals(members.size(), 2);

    em.getTransaction().commit();
  }

  @Test
  public void test_bio_direction_not_remove_member() {
    em.getTransaction().begin();

    Team team = new Team("team1", "팀1");
    em.persist(team);

    Team team2 = new Team("team2", "팀2");
    em.persist(team2);

    Member member = new Member("member1", "test1");
    member.setTeam(team);
    member.setTeam(team2);
    em.persist(member);

    // 더이상 Team에 member1은 속하지 않는데 length가 1이 나옴
    assertEquals(team.getMembers().size(), 0);
    assertEquals(team2.getMembers().size(), 1);

    em.getTransaction().commit();
  }

}
