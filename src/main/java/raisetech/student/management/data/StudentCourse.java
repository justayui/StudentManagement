package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
@Entity
public class StudentCourse {

    @Schema(description = "受講生コース情報を一意に識別するためのIDです。自動採番されます。",example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "受講コース名。",example = "Javaフルコース")
    @NotBlank(message = "コース名は必須入力です。")
    private String courseName;

    @Schema(description = "受講開始日。LocalDateが自動で設定されます。",example = "2026-07-05",accessMode = AccessMode.READ_ONLY)
    private LocalDate startDate;

    @Schema(description = "受講終了日。LocalDateから1年後が自動で設定されます。",example = "2027-07-04",accessMode = AccessMode.READ_ONLY)
    private LocalDate endDate;

    @Schema(description = "受講生コース情報に紐づく受講生IDです。",example = "100")
    private int studentId;

}
