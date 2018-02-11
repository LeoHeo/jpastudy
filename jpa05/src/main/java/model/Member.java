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

  @ManyToOne
  @JoinColumn(name = "TEAM_ID")
  private Team team;

  public Member(String id, String username) {
    this.id = id;
    this.username = username;
  }
}
