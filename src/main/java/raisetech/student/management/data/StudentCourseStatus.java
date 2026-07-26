package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.data.enums.EnumCourseStatus;

@Schema(description = "受講生コースの申込状況")
@Getter
@Setter
public class StudentCourseStatus {

  @Schema(description = "受講生コースの申込状況を一意に識別するためのIDです。自動採番されます。",example = "1")
  private int id;

  @Schema(description = "申込状況に紐づく受講生コースIDです。", example = "1")
  private int courseId;

  @Schema(description = "受講生コースの申込状況です。仮申込・本申込・受講中・受講終了の4つから選択します。初期値は仮申込です。", example = "受講中")
  private EnumCourseStatus status = EnumCourseStatus.TEMPORARY_APPLICATION;
}
