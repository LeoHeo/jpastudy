package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
public class Product {

  @Id
  @Column(name = "PRODUCT_ID")
  private String id;

  private String name;

  @ManyToMany(mappedBy = "products")
  private List<Member2> members = new ArrayList<>();
}
