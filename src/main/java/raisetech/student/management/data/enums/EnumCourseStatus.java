package raisetech.student.management.data.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 受講コースの申込状況を表すEnumです。
 */
@Schema(description = "受講コース申込状況", enumAsRef = true)
@Getter
public enum EnumCourseStatus {
  /** 仮申込（初期値） */
  @Schema(description = "仮申込")
  TEMPORARY_APPLICATION("仮申込"),

  /** 本申込 */
  @Schema(description = "本申込")
  MAIN_APPLICATION("本申込"),

  /** 受講中 */
  @Schema(description = "受講中")
  IN_PROGRESS("受講中"),

  /** 受講終了 */
  @Schema(description = "受講終了")
  COMPLETED("受講終了");

  private final String label;

  EnumCourseStatus(String label){
    this.label = label;
  }
}
