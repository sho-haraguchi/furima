package in.tech_camp.furima.validation;

import jakarta.validation.GroupSequence;

@GroupSequence({
    GroupOrder.Group1.class,
    GroupOrder.Group2.class,
    GroupOrder.Group3.class
})
public interface GroupOrder {
  interface Group1 {
  }

  interface Group2 {
  }

  interface Group3 {
  }
}
