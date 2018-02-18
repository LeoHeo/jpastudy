package model;

import java.io.Serializable;
import java.util.Objects;
import lombok.Setter;

/**
 * @author Heo, Jin Han
 * @since 2018-02-18
 */
@Setter
public class MemberProductId implements Serializable {

  private String member;  // MemberProduct.member와 연결
  private String product; // MemberProduct.product와 연결

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MemberProductId that = (MemberProductId) o;
    return Objects.equals(member, that.member) &&
        Objects.equals(product, that.product);
  }

  @Override
  public int hashCode() {

    return Objects.hash(member, product);
  }
}
