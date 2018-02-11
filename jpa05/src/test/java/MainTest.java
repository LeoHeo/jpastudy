import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityTransaction;
import model.Member;
import org.junit.Test;

/**
 * @author Heo, Jin Han
 * @since 2018-02-10
 */
public class MainTest extends HibernateTest {


  @Test
  public void test_persist_success() {
    EntityTransaction tx = em.getTransaction();

    try {
      tx.begin();

      String id = "id1";
      Member member = new Member();
      member.setId(id);
      member.setUsername("진한");
      member.setAge(30);

      em.persist(member);

      Member findMember = em.find(Member.class, id);

      tx.commit();

      assertNotNull(findMember);
      assertEquals(findMember.getUsername(), member.getUsername());
    } catch (Exception e) {
      e.printStackTrace();
      tx.rollback();
    }
  }

  @Test
  public void test_findMember_success () {
    EntityTransaction tx = em.getTransaction();

    try {
      tx.begin();

      String id = "id1";

      Member findMember = em.find(Member.class, id);

      tx.commit();

      assertNotNull(findMember);
    } catch (Exception e) {
      e.printStackTrace();
      tx.rollback();
    }
  }
}
