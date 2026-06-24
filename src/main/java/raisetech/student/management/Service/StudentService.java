package raisetech.student.management.Service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.Controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentCoursesRepository;
import raisetech.student.management.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです
 * 受講生の検索・登録・更新処理を行います
 */
@Service
public class StudentService {

  private final StudentRepository repository;
  private final StudentCoursesRepository courseRepository;
  public StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentCoursesRepository courseRepository,StudentConverter converter) {
    this.repository = repository;
    this.courseRepository = courseRepository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索
   * 全件検索を行うため、条件指定は行いません
   *
   * @return 受講生詳細一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = courseRepository.searchCourse();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 受講生詳細の検索
   * IDに紐づく任意の受講生の情報を取得した後、その受講生に紐づく受講生コース情報を取得し設定します。
   *
   * @param id 受講生ID
   * @return 受講生情報
   */
  public StudentDetail getStudentById(Integer id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourseList = courseRepository.searchStudentCourse(student.getId());
    return new StudentDetail(student, studentCourseList);
  }

  /**
   * 受講生詳細の登録
   * 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づけるための値とコース開始日、コース終了日を設定します。
   *
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();

    repository.registerStudent(student);
    studentDetail.getStudentCourseList().forEach(studentCourse -> {
      initStudentCourse(studentCourse, student);
      courseRepository.registerCourse(studentCourse);
    });
    return studentDetail;
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する。
   *
   * @param studentCourse 受講生コース情報
   * @param student 受講生
   */
  private static void initStudentCourse(StudentCourse studentCourse, Student student) {
    LocalDate now = LocalDate.now();

    studentCourse.setStudentId(student.getId());
    studentCourse.setStartDate(now);
    studentCourse.setEndDate(now.plusYears(1));
  }

  /**
   * 受講生詳細の更新
   * 受講生と受講生コース情報をそれぞれ更新します。
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourseList().forEach(courseRepository::updateStudentCourse);
  }
}
