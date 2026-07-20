package raisetech.student.management.data.enums;

import lombok.Getter;

@Getter
public enum EnumGender {
  MALE("男性"),
  FEMALE("女性"),
  OTHER("その他");

  private final String label;

  EnumGender(String label){
    this.label = label;
  }

}
