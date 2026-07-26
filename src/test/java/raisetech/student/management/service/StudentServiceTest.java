package raisetech.student.management.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.data.StudentCourseStatus;
import raisetech.student.management.data.enums.EnumCourseStatus;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.exception.TestException;
import raisetech.student.management.repository.StudentCourseRepository;
import raisetech.student.management.repository.StudentCourseStatusRepository;
import raisetech.student.management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
  @Mock
  private StudentRepository repository;

  @Mock
  private StudentCourseRepository courseRepository;

  @Mock
  private StudentCourseStatusRepository statusRepository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before(){
    sut = new StudentService(repository, courseRepository, statusRepository, converter);
  }


//受講生詳細一覧検索に関するテスト
@Test
  void 受講生詳細の一覧検索＿リポジトリとコンバーターの処理が適切に呼び出せていること(){
    List<Student> studentList = List.of(new Student());
    List<StudentCourse> studentCourseList = List.of(new StudentCourse());
    List<StudentCourseStatus> statusList = List.of(new StudentCourseStatus());
    List<StudentDetail> expectedDetailList = List.of(new StudentDetail());
    when(repository.search()).thenReturn(studentList);
    when(courseRepository.searchCourse()).thenReturn(studentCourseList);
    when(statusRepository.searchStatus()).thenReturn(statusList);
    when(converter.convertStudentDetails(studentList,studentCourseList,statusList)).thenReturn(expectedDetailList);

    List<StudentDetail> actualResult = sut.searchStudentList();

    Assertions.assertEquals(expectedDetailList,actualResult);

    verify(repository, times(1)).search();
    verify(courseRepository,times(1)).searchCourse();
    verify(statusRepository, times(1)).searchStatus();
    verify(converter,times(1)).convertStudentDetails(studentList,studentCourseList,statusList);
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
  void IDに紐づく受講生詳細の検索＿検索処理が適切に呼び出せていること(){
    Student student = new Student();
    student.setId(1);
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(2);
    studentCourse.setStudentId(1);
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentCourseStatus status = new StudentCourseStatus();
    status.setId(2);
    status.setCourseId(2);
    List<StudentCourseStatus> expectedStatusList = List.of(status);

    when(repository.searchStudent(1)).thenReturn(student);
    when(courseRepository.searchStudentCourse(1)).thenReturn(studentCourseList);
    when(statusRepository.searchStatusByCourseId(List.of(2))).thenReturn(expectedStatusList);

    StudentDetail actualResult = sut.getStudentById(1);

    Assertions.assertEquals(student,actualResult.getStudent());
    Assertions.assertEquals(studentCourseList,actualResult.getStudentCourseList());
    Assertions.assertEquals(expectedStatusList,actualResult.getStatusList());

    verify(repository, times(1)).searchStudent(1);
    verify(courseRepository, times(1)).searchStudentCourse(1);
    verify(statusRepository, times(1)).searchStatusByCourseId(List.of(2));
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
    studentCourse.setId(10);
    List<StudentCourse> courseList = List.of(studentCourse);
    List<StudentCourseStatus> statusList = new ArrayList<>();

    StudentDetail studentDetail = new StudentDetail(student,courseList, statusList);

    sut.registerStudent(studentDetail);

    verify(repository, times(1)).registerStudent(student);
    verify(courseRepository, times(1)).registerCourse(studentCourse);

    ArgumentCaptor<StudentCourseStatus> statusCaptor = ArgumentCaptor.forClass(StudentCourseStatus.class);
    verify(statusRepository, times(1)).registerCourseStatus(statusCaptor.capture());

    StudentCourseStatus capturedStatus = statusCaptor.getValue();
    Assertions.assertEquals(10, capturedStatus.getCourseId());
    Assertions.assertEquals(EnumCourseStatus.TEMPORARY_APPLICATION, capturedStatus.getStatus());
  }

  @Test
  void 受講生詳細の登録＿初期化処理が行われること(){
    int id = 999;
    Student student = new Student();
    student.setId(id);
    StudentCourse studentCourse = new StudentCourse();

    sut.initStudentCourse(studentCourse,student.getId());

    Assertions.assertEquals(999, studentCourse.getStudentId());
    Assertions.assertEquals(LocalDate.now(),studentCourse.getStartDate());
    Assertions.assertEquals(LocalDate.now().plusYears(1), studentCourse.getEndDate());
  }

  @Test
  void 受講生登録の途中でエラーが発生した場合にロールバックされること(){
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(10);
    List<StudentCourse> courseList = List.of(studentCourse);
    List<StudentCourseStatus> statusList = new ArrayList<>();

    StudentDetail studentDetail = new StudentDetail(student,courseList,statusList);

    doThrow(new RuntimeException("DBエラー"))
        .when(courseRepository).registerCourse(any());

    Assertions.assertThrows(RuntimeException.class,()->{sut.registerStudent(studentDetail);
    });

    verify(repository,times(1)).registerStudent(any());
  }

  //受講生詳細の更新
  @Test
    void 受講生詳細の更新＿受講生が存在する場合＿適切に更新処理が呼び出されること(){
    Student student = new Student();
    student.setId(1);
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(2);
    studentCourse.setStudentId(1);
    List<StudentCourse> courseList = List.of(studentCourse);
    StudentCourseStatus status = new StudentCourseStatus();
    status.setId(2);
    status.setCourseId(2);
    List<StudentCourseStatus> statusList = List.of(status);
    StudentDetail studentDetail = new StudentDetail(student,courseList,statusList);

    when(repository.searchStudent(1)).thenReturn(student);

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(student);
    verify(courseRepository, times(1)).updateStudentCourse(studentCourse);
    verify(statusRepository, times(1)).updateCourseStatus(status);
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
