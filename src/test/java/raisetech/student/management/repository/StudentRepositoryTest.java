package raisetech.student.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;
  @Autowired
  private StudentCourseRepository courseRepository;

  //StudentRepository.Test
  @Test
  void 受講生の全件検索が行えること(){
    List<Student> actual = sut.search();

    assertThat(actual).hasSize(5);
  }

  @Test
  void 受講生のID検索が行えること(){
    Student actual = sut.searchStudent(1);

    assertThat(actual).isNotNull();
    assertThat(actual.getName()).isEqualTo("田中 太郎");
    assertThat(actual.getAge()).isEqualTo(20);
    assertThat(actual.getEmail()).isEqualTo("tanaka.taro@example.com");
  }

  @Test
  void 受講生の登録が行えること(){
    Student student = new Student();
    student.setName("田中 太郎");
    student.setAge(20);
    student.setNameKana("タナカタロウ");
    student.setNickname("タロー");
    student.setEmail("test@example.com");
    student.setGender("male");
    student.setPlaceOfResidence("福岡");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);

    List<Student> actual = sut.search();
    assertThat(actual).hasSize(6);
  }

  @Test
  void 受講生の更新ができること(){
    Student student = sut.searchStudent(1);
    student.setNickname("タロ");

    sut.updateStudent(student);

    Student actual = sut.searchStudent(1);

    assertThat(actual).isNotNull();
    assertThat(actual.getNickname()).isEqualTo("タロ");
    assertThat(actual.getName()).isEqualTo("田中 太郎");
    assertThat(actual.getAge()).isEqualTo(20);
  }

  //StudentCourseRepository.Test
  @Test
  void 受講生コース情報が全件検索できること(){
    List<StudentCourse> actual = courseRepository.searchCourse();
    assertThat(actual).hasSize(9);
  }

  @Test
  void 受講生IDに紐づく受講生コース情報の検索ができること(){
    List<StudentCourse> actual = courseRepository.searchStudentCourse(1);

    assertThat(actual).hasSize(2);
    assertThat(actual).extracting("courseName").containsExactlyInAnyOrder("Javaフルコース","AWS基礎コース");
  }

  @Test
  void 受講生コース情報の登録ができること(){
    StudentCourse course = new StudentCourse();
    course.setCourseName("Javaフルコース");
    course.setStartDate(LocalDate.now());
    course.setEndDate(LocalDate.now().plusYears(1));

    courseRepository.registerCourse(course);

    List<StudentCourse> actual = courseRepository.searchCourse();
    assertThat(actual).hasSize(10);
  }

  @Test
  void 受講生コースの更新ができること(){
    List<StudentCourse> courseList =courseRepository.searchStudentCourse(1);
    assertThat(courseList).hasSize(2);

    StudentCourse targetCourse = null;
    for(StudentCourse course : courseList){
      if("Javaフルコース".equals(course.getCourseName())){
        targetCourse = course;
        break;
      }
    }

    assertThat(targetCourse).isNotNull();

    targetCourse.setCourseName("Web開発コース");
    courseRepository.updateStudentCourse(targetCourse);

    List<StudentCourse> actual = courseRepository.searchStudentCourse(1);
    assertThat(actual).hasSize(2);
    assertThat(actual).extracting("courseName").containsExactlyInAnyOrder("Web開発コース","AWS基礎コース");
  }
}
