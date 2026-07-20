package raisetech.student.management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.data.enums.EnumCourseName;
import raisetech.student.management.data.enums.EnumGender;
import raisetech.student.management.domain.StudentDetail;

class StudentConverterTest {
  private StudentConverter sut;

  @BeforeEach
  void before(){
    sut = new StudentConverter();
  }

  @Test
  void 受講生と受講生コース情報を受講生詳細に変換できること(){
    Student student = createStudent();

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(1);
    studentCourse.setStudentId(1);
    studentCourse.setCourseName(EnumCourseName.JAVA_FULL);
    studentCourse.setStartDate(LocalDate.now());
    studentCourse.setEndDate(LocalDate.now().plusYears(1));

    List<Student> studentList = List.of(student);
    List<StudentCourse> courseList = List.of(studentCourse);

    List<StudentDetail> actualResult = sut.convertStudentDetails(studentList,courseList);

    assertThat(actualResult.get(0).getStudent()).isEqualTo(student);
    assertThat(actualResult.get(0).getStudentCourseList()).isEqualTo(courseList);
  }

  @Test
  void 受講生のリストと受講生コース情報のリストを渡した時に紐づかない受講生コース情報は除外されること(){
    Student student = createStudent();

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(1);
    studentCourse.setStudentId(2);
    studentCourse.setCourseName(EnumCourseName.JAVA_FULL);
    studentCourse.setStartDate(LocalDate.now());
    studentCourse.setEndDate(LocalDate.now().plusYears(1));

    List<Student> studentList = List.of(student);
    List<StudentCourse> courseList = List.of(studentCourse);

    List<StudentDetail> actualResult = sut.convertStudentDetails(studentList,courseList);

    assertThat(actualResult.get(0).getStudent()).isEqualTo(student);
    assertThat(actualResult.get(0).getStudentCourseList()).isEmpty();
  }

  private static @NonNull Student createStudent() {
    Student student = new Student();
    student.setId(1);
    student.setName("田中太郎");
    student.setAge(20);
    student.setNameKana("タナカタロウ");
    student.setNickname("タロー");
    student.setEmail("test@example.com");
    student.setGender(EnumGender.MALE);
    student.setPlaceOfResidence("福岡");
    student.setRemark("");
    student.setDeleted(false);
    return student;
  }
}