package raisetech.student.management.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.Service.StudentService;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }


  @GetMapping("/studentList")
  public List<Student> getStudentList(){
    return service.searchStudentList();
  }

  @GetMapping("/studentsCoursesList")
  public List<StudentsCourses> getStudentCourseList() {
    return service.searchStudentCourseList();
  }
}
