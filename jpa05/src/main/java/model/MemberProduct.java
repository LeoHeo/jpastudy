package model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Heo, Jin Han
 * @since 2018-02-18
 */

@Entity
@IdClass(MemberProductId.class)
@Setter
@Getter
public class MemberProduct {

  @Id
  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  private Member3 member; // MemberProduct.member와 연결

  @Id
  @ManyToOne
  @JoinColumn(name = "PRODUCT_ID")
  private Product2 product; // MemberProduct.product와 연결

  private int orderAmount;

  @Temporal(TemporalType.DATE)
  private Date orderDate;

}
