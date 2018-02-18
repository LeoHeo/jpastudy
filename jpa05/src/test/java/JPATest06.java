import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;
import model.Member;
import model.Member2;
import model.Product;
import model.Team;
import org.junit.Test;

/**
 * @author Heo, Jin Han
 * @since 2018-02-14
 */
public class JPATest06 extends HibernateTest {

  @Test
  public void test_save_member() {
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

  @Test
  public void test_many_to_many_member() {
    em.getTransaction().begin();

    Product productA = new Product();
    productA.setId("productA");
    productA.setName("상품A");
    em.persist(productA);

    Member2 member1 = new Member2();
    member1.setId("member1");
    member1.setUsername("회원1");
    member1.addProduct(productA); // 연관 관계 설정
    em.persist(member1);

    Member2 member2 = new Member2();
    member2.setId("member2");
    member2.setUsername("회원2");
    member2.addProduct(productA);
    em.persist(member2);

    em.getTransaction().commit();
  }

  @Test
  public void test_find_many_to_many_member() {
    test_many_to_many_member();

    em.getTransaction().begin();

    Member2 member = em.find(Member2.class, "member1");
    List<Product> products = member.getProducts(); // 객체 그래프 탐색

    for (Product product: products) {
      System.out.println(product.getName());
    }

    assertNotEquals(products.size(), 0);

    em.getTransaction().commit();

  }

  @Test
  public void test_find_inverse_many_to_many() {
    test_many_to_many_member();

    em.getTransaction().begin();

    Product product = em.find(Product.class, "productA");
    List<Member2> members = product.getMembers();

    Member2 member2 = em.find(Member2.class, "member1");
    List<Product> products = member2.getProducts();

    assertEquals(products.size(), 1);
    assertEquals(members.size(), 2);

    em.getTransaction().commit();
  }
}
