package raisetech.student.management.Service;

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

  private StudentRepository repository;
  private StudentCoursesRepository coursesRepository;

  @Autowired
  public StudentService(StudentRepository repository,StudentCoursesRepository coursesRepository) {
    this.repository = repository;
    this.coursesRepository = coursesRepository;
  }

  public List<Student> searchStudentList(){
  List<Student> studentList = repository.search().stream()
      .toList();
    return studentList;
  }
  public List<StudentsCourses> searchStudentCourseList() {
  List<StudentsCourses> studentsCoursesList = coursesRepository.searchCourse().stream()
      .filter(studentsCourses -> "Javaフルコース".equals(studentsCourses.getCourseName()))
      .toList();
    return studentsCoursesList;
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail){
    Student student = studentDetail.getStudent();
    repository.registerStudent(student);

  }
}
