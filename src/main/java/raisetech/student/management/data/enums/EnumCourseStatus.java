package raisetech.student.management.data.enums;

import lombok.Getter;

@Getter
public enum EnumCourseStatus {
  TEMPORARY_APPLICATION("仮申込"),
  MAIN_APPLICATION("本申込"),
  IN_PROGRESS("受講中"),
  COMPLETED("受講終了");

  private final String label;

  EnumCourseStatus(String label){
    this.label = label;
  }
}
