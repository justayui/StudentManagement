package raisetech.student.management.data;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private int age;
  private String nameKana;
  private String nickname;
  private String email;
  private String gender;
  private String placeOfResidence;
  private String remark;
  private boolean isDeleted;
}
