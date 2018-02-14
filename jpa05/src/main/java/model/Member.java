package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Heo, Jin Han
 * @since 2018-02-10
 */
@Entity
// @org.hibernate.annotations.DynamicUpdate -> 필드가 많거나 저장되는 내용이 클때 수정된 필드만 동적으로 update sql 생성하는 전략
@Table(name = "MEMBER")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Member {

  @Id
  @Column(name = "MEMBER_ID")
  private String id;

  private String username;

  public void setTeam(Team team) {
    /**
     * 영속컨텍스가 살아있는 컨텍스트에서
     * 즉, 한 트랜잭션내에서 setTeam을 두번 할 경우
     * 두번째 setTeam을 할경우에
     * this.team != null이다.
     *
     * this.team != null인 경우에는
     * 예전 this.team.getMembers() 정보에서 member를 삭제하고
     * 새로운 team.getMembers()에 add를 해야한다.
     */
    if (this.team != null) {
      this.team.getMembers().remove(this);
    }

    // 무한루프에 빠지지 않도록 체크
    if (!team.getMembers().contains(this)) {
      team.getMembers().add(this);
    }

    this.team = team;
  }

  @ManyToOne
  @JoinColumn(name = "TEAM_ID")
  private Team team;

  public Member(String id, String username) {
    this.id = id;
    this.username = username;
  }
}
