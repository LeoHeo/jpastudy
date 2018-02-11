import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import model.Member;
import org.junit.Test;

/**
 * @author Heo, Jin Han
 * @since 2018-02-10
 */
public class JPATest02 extends HibernateTest {

  @Test
  public void test_persist_success() {
    em.getTransaction().begin();

    // insert test
    String id = "id1";
    Member member = new Member();
    member.setId(id);
    member.setUsername("진한");

    em.persist(member);

    Member findMember = em.find(Member.class, id);
    Member findMember2 = em.find(Member.class, id);

    assertEquals(findMember, findMember2);
    System.out.println("동일성 보장 : " + (findMember == findMember2));

    assertNotNull(findMember);
    assertEquals(findMember.getUsername(), member.getUsername());

    // update test
    // 엔티티를 조회하고 데이터만 변경해주면 된다.
    // update()라는 메소드는 존재하지 않는다.

    // 엔티티만 변경했는데 어떻게 데이터베이스에 update 쿼리가 날라가는걸까?
    // 엔티티의 변경사항을 데이터베이스에 자동으로 반영하는 기능을 변경 감지(dirty checking)이라 한다.
    // 변경감지는 영속성 컨텍스트가 관리하는 영속상태의 엔티티에만 적용된다.
    findMember.setUsername("테스트");

    assertEquals(findMember.getUsername(), "테스트");

    // JPQL 조회 테스트
    List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

    assertEquals(members.size(), 1);

    em.remove(member);

    // insert나 update랑 remove를 하지않고 쭉 쌓아놓고 있다가
    // transaction commit이 발생하면 모아둔 쿼리를 데이터베이스에 보낸다.
    // 이것을 트랜잭션을 지원하는 쓰기 지연(transactional write-behind)

    /**
     * JPA는 엔티티를 영속성 컨텍스트에 보관할 때, 최초 상태를 복사해서 저장해두는데 이것을 스냅샷이라 한다.
     * 그리고 플러시 시점에 스냅샷과 엔티티를 비교해서 변경된 엔티티를 찾는다.
     *
     * 1. 트랜잭션을 커밋하면 엔티티 매니저 내부에서 먼저 flush 메소드가 호출된다.
     * 2. 엔티티와 스냅샷을 비교해서 변경된 엔티티를 찾는다.
     * 3. 변경된 엔티티가 있으면 수정 쿼리를 생성해서 쓰기 지연 SQL 저장소에 보낸다.
     * 4. 쓰기 지연 저장소의 SQL를 데이터베이스에 보낸다.
     * 5. 데이터베이스 트랜잭션을 커밋한다.
     *
     * 업데이트 쿼리를 생성할때는 모든 필드를 업데이트 하는 update 쿼리를 작성한다.
     * 1. 모든 필드를 사용하면 수정 쿼리가 항상 같다(바인딩되는 데이터는 다르다.) -> 애플리케이션 로딩 시점에 수정 쿼리를 미리 생성해두고 재사용할 수 있다.
     * 2. 데이터베이스에 동일한 쿼리를 보내면 데이터베이스는 이전에 한 번 파싱된 쿼리를 재사용할 수 있다.
     *
     * 필드가 많거나 저장되는 내용이 너무 크면 수정된 데이터만 사용해서 동적으로 update SQL을 생성하는 전략 선택
     * 단 이때는 하이버네이트 확장 기능 사용 -> 한 테이블에 컬럼이 30개 이상이 되었을때
     * @org.hibernate.annoation.DynamicUpdate
     */
    em.getTransaction().commit();

    // 삭제 테스트
    Member removedMember = em.find(Member.class, id);

    assertNull(removedMember);
  }
}
