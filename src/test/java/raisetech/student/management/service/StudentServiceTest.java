package raisetech.student.management.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.exception.TestException;
import raisetech.student.management.repository.StudentCourseRepository;
import raisetech.student.management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
  @Mock
  private StudentRepository repository;

  @Mock
  private StudentCourseRepository courseRepository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before(){
    sut = new StudentService(repository, courseRepository, converter);
  }


//受講生詳細一覧検索に関するテスト
@Test
  void 受講生詳細の一覧検索＿リポジトリとコンバーターの処理が適切に呼び出せていること(){
    List<Student> studentList = new ArrayList<>();
    studentList.add(new Student());
    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourseList.add(new StudentCourse());
    List<StudentDetail> expectedDetailList = new ArrayList<>();
    expectedDetailList.add(new StudentDetail());
    when(repository.search()).thenReturn(studentList);
    when(courseRepository.searchCourse()).thenReturn(studentCourseList);
    when(converter.convertStudentDetails(studentList,studentCourseList)).thenReturn(expectedDetailList);

    List<StudentDetail> actualResult = sut.searchStudentList();

    Assertions.assertEquals(expectedDetailList,actualResult);

    verify(repository, times(1)).search();
    verify(courseRepository,times(1)).searchCourse();
    verify(converter,times(1)).convertStudentDetails(studentList,studentCourseList);
  }

@Test
  void 受講生詳細の一覧検索＿受講生が0件の場合＿適切な例外を投げることができていること(){
    List<Student> emptyList = new ArrayList<>();
    when(repository.search()).thenReturn(emptyList);

    Assertions.assertThrows(TestException.class,()->{sut.searchStudentList();
    });
}

//受講生詳細のID検索に関するテスト
@Test
  void IDに紐づく受講生詳細の検索＿登録処理が適切に呼び出せていること(){
    Student student = new Student();
    student.setId(1);
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.searchStudent(1)).thenReturn(student);
    when(courseRepository.searchStudentCourse(1)).thenReturn(studentCourseList);

    StudentDetail actualResult = sut.getStudentById(student.getId());

    Assertions.assertEquals(student,actualResult.getStudent());
    Assertions.assertEquals(studentCourseList,actualResult.getStudentCourseList());

    verify(repository, times(1)).searchStudent(1);
    verify(courseRepository, times(1)).searchStudentCourse(1);
  }

  @Test
  void IDに紐づく受講生詳細の検索＿IDに該当する生徒が存在しない場合＿適切な例外を投げることができていること(){
    when(repository.searchStudent(100)).thenReturn(null);

    Assertions.assertThrows(TestException.class,()->{sut.getStudentById(100);
    });
  }

  //受講生詳細の登録に関するテスト
  @Test
    void 受講生詳細の登録＿リポジトリに適切な情報を渡せていること(){
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> courseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student,courseList);

    sut.registerStudent(studentDetail);

    verify(repository, times(1)).registerStudent(student);
    verify(courseRepository, times(1)).registerCourse(studentCourse);
  }

  @Test
  void 受講生詳細の登録＿初期化処理が行われること(){
    int id = 999;
    Student student = new Student();
    student.setId(id);
    StudentCourse studentCourse = new StudentCourse();

    sut.initStudentCourse(studentCourse,student.getId());

    Assertions.assertEquals(999, studentCourse.getStudentId());
    //getTimeFormatter?を使ってより厳密に検証するのが望ましい。
    Assertions.assertEquals(LocalDate.now(),studentCourse.getStartDate());
    Assertions.assertEquals(LocalDate.now().plusYears(1), studentCourse.getEndDate());
  }

  //受講生詳細の更新
  @Test
    void 受講生詳細の更新＿受講生が存在する場合＿適切に更新処理が呼び出されること(){
    Student student = new Student();
    student.setId(1);
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> courseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student,courseList);

    when(repository.searchStudent(1)).thenReturn(student);

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(student);
    verify(courseRepository, times(1)).updateStudentCourse(studentCourse);
  }

  @Test
    void 受講生詳細の更新＿受講生が存在しない場合＿適切な例外を投げることができていること(){
    Student student = new Student();
    student.setId(100);
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);

    when(repository.searchStudent(100)).thenReturn(null);

    Assertions.assertThrows(TestException.class,()->{sut.updateStudent(studentDetail);
    });
  }
}
