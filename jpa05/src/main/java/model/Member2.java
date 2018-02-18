package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Heo, Jin Han
 * @since 2018-02-18
 */
@Entity
@Setter
@Getter
public class Member2 {

  @Id
  @Column(name = "MEMBER_ID")
  private String id;

  private String username;

  @ManyToMany
  @JoinTable(
      name = "MEMBER_PRODUCT",
      joinColumns = @JoinColumn(name = "MEMBER_ID"),
      inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")
  )
  private List<Product> products = new ArrayList<>();
}
