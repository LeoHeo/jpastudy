package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Heo, Jin Han
 * @since 2018-02-18
 */
@Entity
@Setter
@Getter
public class Member3 {

  @Id
  @Column(name = "MEMBER_ID")
  private String id;

  private String username;

//  @OneToMany(mappedBy = "member")
//  private List<MemberProduct> memberProducts = new ArrayList<>();

  @OneToMany(mappedBy = "member")
  private List<Order> orders = new ArrayList<>();
}
