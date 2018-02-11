import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * @author Heo, Jin Han
 * @since 2018-02-10
 */
public class HibernateTest {
  protected static EntityManagerFactory emf;
  protected static EntityManager em;

  @BeforeClass
  public static void init() {
    emf = Persistence.createEntityManagerFactory("jpabook");
    em = emf.createEntityManager();
  }

  @AfterClass
  public static void tearDown() {
    em.close();
    emf.close();
  }

}
