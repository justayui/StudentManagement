package raisetech.student.management.data.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 受講生の性別を表すEnumです。
 */
@Schema(description = "受講生の性別", enumAsRef = true)
@Getter
public enum EnumGender {
  @Schema(description = "男性")
  male("男性"),

  @Schema(description = "女性")
  female("女性"),

  @Schema(description = "その他")
  other("その他");

  private final String label;

  EnumGender(String label){
    this.label = label;
  }

}
