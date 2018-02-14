package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Heo, Jin Han
 * @since 2018-02-11
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Team {

  @Id
  @Column(name = "TEAM_ID")
  private String id;

  private String name;

  @OneToMany(mappedBy = "team")
  private List<Member> members = new ArrayList<>();

  public void addMember(Member member) {
    this.members.add(member);

    // 무한루프에 빠지지 않도록 체크
    if (member.getTeam() != this) {
      member.setTeam(this);
    }
  }

  public Team(String id, String name) {
    this.id = id;
    this.name = name;
  }
}
