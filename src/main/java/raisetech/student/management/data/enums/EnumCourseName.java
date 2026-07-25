package raisetech.student.management.data.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 受講コース名を表すEnumです
 */
@Schema(description = "受講コース名", enumAsRef = true)
@Getter
public enum EnumCourseName {
  @Schema(description = "Javaフルコース")
  JAVA_FULL("Javaフルコース"),

  @Schema(description = "AWS基礎コース")
  AWS("AWS基礎コース"),

  @Schema(description = "Web開発コース")
  WEB_DEVELOPMENT("Web開発コース");

  private final String label;

  EnumCourseName(String label){
    this.label = label;
  }
}
