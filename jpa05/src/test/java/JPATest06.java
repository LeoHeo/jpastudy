import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;
import model.Member;
import model.Member2;
import model.Member3;
import model.MemberProduct;
import model.MemberProductId;
import model.Order;
import model.Product;
import model.Product2;
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

  @Test
  public void test_save_custom_many_to_many_member_product() {
    em.getTransaction().begin();

    // 회원 저장
    Member3 member1 = new Member3();
    member1.setId("member1");
    member1.setUsername("회원1");
    em.persist(member1);

    // 상품 저장
    Product2 productA = new Product2();
    productA.setId("productA");
    productA.setName("상품1");
    em.persist(productA);

    // 회원 상품 저장
    MemberProduct memberProduct = new MemberProduct();
    memberProduct.setMember(member1);     // 주문 회원 - 연관관계 설정
    memberProduct.setProduct(productA);   // 주문 상품 - 연관관계 설정
    memberProduct.setOrderAmount(2);      // 주문 수량
    memberProduct.setOrderDate(new Date()); // 주문 날짜
    em.persist(memberProduct);

    em.getTransaction().commit();
  }

  @Test
  public void find_save_custom_many_to_many_member_product() {
    test_save_custom_many_to_many_member_product();

    em.getTransaction().begin();

    MemberProductId memberProductId = new MemberProductId();
    memberProductId.setMember("member1");
    memberProductId.setProduct("productA");

    MemberProduct memberProduct = em.find(MemberProduct.class, memberProductId);

    assertNotNull(memberProduct);

    Member3 member3 = memberProduct.getMember();
    Product2 product2 = memberProduct.getProduct();

    System.out.println(member3.getUsername());
    System.out.println(product2.getName());
    System.out.println(memberProduct.getOrderAmount());

    assertNotNull(member3.getUsername());
    assertNotNull(product2.getName());
    assertNotNull(memberProduct.getOrderAmount());

    em.getTransaction().commit();
  }

  @Test
  public void test_save_custom_many_to_many_alternate_key_member_product() {
    em.getTransaction().begin();

    // 회원 저장
    Member3 member3 = new Member3();
    member3.setId("member1");
    member3.setUsername("회원1");
    em.persist(member3);

    // 상품 저장
    Product2 productA = new Product2();
    productA.setId("productA");
    productA.setName("상품1");
    em.persist(productA);

    Order order = new Order();
    order.setMember(member3);
    order.setProduct(productA);
    order.setOrderAmount(333);
    order.setOrderDate(new Date());
    em.persist(order);

    em.getTransaction().commit();
  }

  @Test
  public void test_find_custom_many_to_many_alternate_key_member_product() {
    test_save_custom_many_to_many_alternate_key_member_product();

    em.getTransaction().begin();

    Long orderId = 1L;
    Order order = em.find(Order.class, orderId);

    Member3 member3 = order.getMember();
    Product2 product2 = order.getProduct();

    assertNotNull(member3);
    assertNotNull(product2);
    assertNotNull(member3.getOrders());
    assertNotNull(order.getOrderAmount());

    em.getTransaction().commit();
  }

}
