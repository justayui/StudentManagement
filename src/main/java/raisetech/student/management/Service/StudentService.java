package raisetech.student.management.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList(){
  List<Student> studentList = repository.search().stream()
      .toList();
    return studentList;
  }

  public List<StudentsCourses> searchStudentCourseList() {
  List<StudentsCourses> studentsCoursesList = repository.searchCourse().stream()
      .filter(studentsCourses -> "Javaフルコース".equals(studentsCourses.getCourseName()))
      .toList();
    return studentsCoursesList;
  }
}
