package model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Heo, Jin Han
 * @since 2018-02-18
 */
@Entity
@Setter
@Getter
public class Product2 {

  @Id
  @Column(name = "PRODUCT_ID")
  private String id;

  private String name;

}
