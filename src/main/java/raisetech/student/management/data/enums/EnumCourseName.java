package raisetech.student.management.data.enums;

import lombok.Getter;

@Getter
public enum EnumCourseName {
  JAVA_FULL("Javaフルコース"),
  AWS("AWSコース"),
  WEB_DEVELOPMENT("Web開発コース");

  private final String label;

  EnumCourseName(String label){
    this.label = label;
  }
}
