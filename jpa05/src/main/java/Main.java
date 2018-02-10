import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author Heo, Jin Han
 * @since 2018-02-10
 */
public class Main {

  public static void main(String[] args) {

    // 엔티티매니저 팩토리 생성
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction(); // 트랜잭션 가능 획득

    try {
      tx.begin();

      tx.commit();
    } catch (Exception e) {
      e.printStackTrace();
      tx.rollback();
    } finally {
      em.close();
    }
    emf.close();
  }
}
