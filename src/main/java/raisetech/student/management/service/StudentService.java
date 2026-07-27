package raisetech.student.management.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.data.StudentCourseStatus;
import raisetech.student.management.data.StudentSearchCondition;
import raisetech.student.management.data.enums.EnumCourseStatus;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.exception.TestException;
import raisetech.student.management.repository.StudentCourseRepository;
import raisetech.student.management.repository.StudentCourseStatusRepository;
import raisetech.student.management.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです
 * 受講生の検索・登録・更新処理を行います
 */
@Service
public class StudentService {

  private final StudentRepository repository;
  private final StudentCourseRepository courseRepository;
  private final StudentCourseStatusRepository statusRepository;
  private final StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentCourseRepository courseRepository,StudentCourseStatusRepository statusRepository,StudentConverter converter) {
    this.repository = repository;
    this.courseRepository = courseRepository;
    this.statusRepository = statusRepository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索
   * 名前・フリガナ・メールアドレスの条件指定ができます。
   * 名前・フリガナは部分一致で可。メールアドレスは完全一致が必要です。
   *
   * @return 受講生詳細一覧（全件）
   */
  public List<StudentDetail> searchStudentList(StudentSearchCondition condition){
    List<Student> studentList = repository.search(condition);
    List<StudentCourse> studentCourseList = courseRepository.searchCourse();
    List<StudentCourseStatus> statusList = statusRepository.searchStatus();
    if(studentList.isEmpty()){
      throw new TestException("現在、登録されている学生情報は0件です。");
    }
    return converter.convertStudentDetails(studentList, studentCourseList, statusList);
  }

  /**
   * 受講生詳細の検索
   * IDに紐づく任意の受講生の情報を取得した後、その受講生に紐づく受講生コース情報とコースの申込状況を取得し設定します。
   *
   * @param id 受講生ID
   * @return 受講生情報
   */
  public StudentDetail getStudentById(Integer id){
    Student student = repository.searchStudent(id);
    if(student == null){
      throw new TestException("ID"+ id +"に該当する生徒情報はありませんでした。");
    }
    List<StudentCourse> studentCourseList = courseRepository.searchStudentCourse(student.getId());
    List<StudentCourseStatus> statusList = List.of();
    if (!studentCourseList.isEmpty()) {
      List<Integer> courseIds = studentCourseList.stream()
          .map(StudentCourse::getId)
          .toList();

      statusList = statusRepository.searchStatusByCourseId(courseIds);
  }
    return new StudentDetail(student, studentCourseList, statusList);
  }

  /**
   * 受講生詳細の登録
   * 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づけるための値とコース開始日、コース終了日を設定します。
   * 受講コースの申込状況はデフォルトで”仮申込”に設定されます。
   *
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();

    repository.registerStudent(student);

    studentDetail.getStudentCourseList().forEach(studentCourse -> {
      initStudentCourse(studentCourse, student.getId());
      courseRepository.registerCourse(studentCourse);

      StudentCourseStatus status = new StudentCourseStatus();
      status.setCourseId(studentCourse.getId());
      status.setStatus(EnumCourseStatus.TEMPORARY_APPLICATION);
      statusRepository.registerCourseStatus(status);
    });
    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定します。
   *
   * @param studentCourse 受講生コース情報
   * @param id 受講生ID
   */
  void initStudentCourse(StudentCourse studentCourse, Integer id) {
    LocalDate now = LocalDate.now();

    studentCourse.setStudentId(id);
    studentCourse.setStartDate(now);
    studentCourse.setEndDate(now.plusYears(1));
  }

  /**
   * 受講生詳細の更新
   * 受講生と受講生コース情報、コースの申込状況をそれぞれ更新します。
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail){
    Student existingStudent = repository.searchStudent(studentDetail.getStudent().getId());
    if(existingStudent == null){
      throw new TestException("指定されたIDの生徒が見つからないため、更新できません。");
    }
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourseList().forEach(courseRepository::updateStudentCourse);

    if (studentDetail.getStatusList() != null) {
      studentDetail.getStatusList().forEach(statusRepository::updateCourseStatus);
    }
  }
}
