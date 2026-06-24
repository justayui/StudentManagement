package raisetech.student.management.Service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.Controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
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
  private final StudentCoursesRepository coursesRepository;
  public StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentCoursesRepository coursesRepository,StudentConverter converter) {
    this.repository = repository;
    this.coursesRepository = coursesRepository;
    this.converter = converter;
  }

  /**
   * 受講生一覧検索
   * 全件検索を行うため、条件指定は行いません
   *
   * @return 受講生一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentsCourses> studentsCoursesList = coursesRepository.searchCourse();
    return converter.convertStudentDetails(studentList, studentsCoursesList);
  }

  /**
   * 受講生検索
   * IDに紐づく任意の受講生の情報を取得した後、その受講生に紐づく受講生コース情報を取得し設定します。
   *
   * @param id 受講生ID
   * @return 受講生情報
   */
  public StudentDetail getStudentById(Integer id) {
    Student student = repository.searchStudent(id);
    List<StudentsCourses> studentsCourses = coursesRepository.searchStudentCourses(student.getId());
    return new StudentDetail(student,studentsCourses);
  }

  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.registerStudent(student);
    for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
      studentsCourses.setStudentId(student.getId());
      studentsCourses.setStartDate(LocalDate.now());
      studentsCourses.setEndDate(LocalDate.now().plusYears(1));
      coursesRepository.registerCourses(studentsCourses);
    }
    return studentDetail;
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for(StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
      coursesRepository.updateStudentCourses(studentsCourses);
    }
  }
}
