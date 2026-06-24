package raisetech.student.management.Controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.student.management.Controller.converter.StudentConverter;
import raisetech.student.management.Service.StudentService;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;

@Controller
public class StudentController {

  private final StudentService service;
  private final StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model){
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentCourseList();

    model.addAttribute("studentList",converter.convertStudentDetails(students,studentsCourses));
    return "studentList";
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model){
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
    model.addAttribute("studentDetail",studentDetail);
    return "registerStudent";
  }

  @GetMapping("/student/detail/{id}")
  public String showStudent(@PathVariable Integer id, Model model){
    StudentDetail studentDetail = service.getStudentById(id);
    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result){
    if(result.hasErrors()){
      return "registerStudent";
    }
    service.registerStudent(studentDetail);
    return "redirect:/studentList";
  }

  @PostMapping("/updateStudent")
  public String updateStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "updateStudent";
    }
    service.updateStudent(studentDetail);
    return "redirect:studentList";
  }
}
