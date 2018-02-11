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

    em.getTransaction().commit();
  }

}
