package raisetech.student.management.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import raisetech.student.management.Controller.converter.StudentConverter;
import raisetech.student.management.Service.StudentService;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;

@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model){
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentCourseList();

    model.addAttribute("studentList",converter.convertStudentDetails(students, studentsCourses));
    return "studentList";
  }

  @GetMapping("/studentsCoursesList")
  public List<StudentsCourses> getStudentCourseList() {
    return service.searchStudentCourseList();
  }
}
