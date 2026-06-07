package raisetech.student.management.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
  private int id;
  private String name;
  private int age;
  private String nameKana;
  private String nickname;
  private String email;
  private String gender;
  private String placeOfResidence;
}
