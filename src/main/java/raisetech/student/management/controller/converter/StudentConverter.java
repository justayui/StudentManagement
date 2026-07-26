package raisetech.student.management.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.data.StudentCourseStatus;
import raisetech.student.management.domain.StudentDetail;

/**
 * 受講生と受講生コース情報、受講コース申込状況を受講生詳細（StudentDetail）に変換するコンバーターです。
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講生コース情報およびその申込状況をマッピングし、受講生詳細（StudentDetail）のリストを構築する。
   * 受講生コース情報は受講生に対して複数存在する可能性があるため、ループを回して受講生詳細情報を組み立てる。
   *
   * @param students 受講生一覧
   * @param studentCourseList 受講生コース情報のリスト
   * @param statusList 受講コース申込状況のリスト
   * @return 受講生詳細情報のリスト
   */
  public @NonNull List<StudentDetail> convertStudentDetails(
      List<Student> students,
      List<StudentCourse> studentCourseList,
      List<StudentCourseStatus> statusList
  ) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentCourse> convertStudentCourseList = studentCourseList.stream()
          .filter(studentCourse -> student.getId() == studentCourse.getStudentId())
          .collect(Collectors.toList());
      studentDetail.setStudentCourseList(convertStudentCourseList);

      List<Integer> courseIds = convertStudentCourseList.stream()
          .map(StudentCourse::getId)
          .toList();

      StudentCourseStatus converterStatus = statusList.stream()
          .filter(status -> courseIds.contains(status.getCourseId()))
          .findFirst()
          .orElse(null);

      studentDetail.setStatus(converterStatus);

      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
}
