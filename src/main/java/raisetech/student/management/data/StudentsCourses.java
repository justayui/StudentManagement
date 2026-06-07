package raisetech.student.management.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {

    private int id;
    private String courseName;
    private String startDate;
    private String endDate;
    private int studentId;

}
