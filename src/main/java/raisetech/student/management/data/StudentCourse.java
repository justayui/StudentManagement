package raisetech.student.management.data;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

    private int id;
    private String courseName;
    private LocalDate startDate;
    private LocalDate endDate;
    private int studentId;

}
