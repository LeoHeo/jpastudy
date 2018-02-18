package model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Heo, Jin Han
 * @since 2018-02-18
 */
@Entity
@Setter
@Getter
@Table(name = "ORDERS")
public class Order {

  @Id
  @GeneratedValue
  @Column(name = "ORDER_ID")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  private Member3 member;

  @ManyToOne
  @JoinColumn(name = "PRODUCT_ID")
  private Product2 product;

  private int orderAmount;

  @Temporal(TemporalType.TIMESTAMP)
  private Date orderDate;
}
