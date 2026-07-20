package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.data.enums.EnumCourseName;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

    @Schema(description = "受講生コース情報を一意に識別するためのIDです。自動採番されます。",example = "1")
    private int id;

    @NotNull
    @Schema(description = "受講コース名。Javaフルコース・AWSコース・Web開発コースの3つから選択します。",example = "Javaフルコース")
    private EnumCourseName courseName;

    @Schema(description = "受講開始日。LocalDateが自動で設定されます。",example = "2026-07-05",accessMode = AccessMode.READ_ONLY)
    private LocalDate startDate;

    @Schema(description = "受講終了日。LocalDateから1年後が自動で設定されます。",example = "2027-07-04",accessMode = AccessMode.READ_ONLY)
    private LocalDate endDate;

    @Schema(description = "受講生コース情報に紐づく受講生IDです。",example = "100")
    private int studentId;

}
