package raisetech.student.management.Service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentCoursesRepository;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository repository;
  private final StudentCoursesRepository coursesRepository;

  @Autowired
  public StudentService(StudentRepository repository, StudentCoursesRepository coursesRepository) {
    this.repository = repository;
    this.coursesRepository = coursesRepository;
  }

  public List<Student> searchStudentList() {
    List<Student> studentList = repository.search().stream()
        .toList();
    return studentList;
  }

  public List<StudentsCourses> searchStudentCourseList() {
    List<StudentsCourses> studentsCoursesList = coursesRepository.searchCourse().stream()
        .toList();
    return studentsCoursesList;
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.registerStudent(student);
    for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
      studentsCourses.setStudentId(student.getId());
      studentsCourses.setStartDate(LocalDate.now());
      studentsCourses.setEndDate(LocalDate.now().plusYears(1));
      coursesRepository.registerCourses(studentsCourses);
    }
  }

  public StudentDetail getStudentById(Integer id) {
    Student student = repository.findById(id);
    List<StudentsCourses> studentsCoursesList = coursesRepository.findByStudentId(id);
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentsCoursesList);
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
