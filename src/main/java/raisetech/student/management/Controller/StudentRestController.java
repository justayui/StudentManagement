package raisetech.student.management.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.Service.StudentService;
import raisetech.student.management.domain.StudentDetail;


/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@RestController
public class StudentRestController {
  private final StudentService service;

  @Autowired
  public StudentRestController(StudentService service){
    this.service = service;
  }

  /**
   * 受講生一覧検索
   * 全件検索を行うため、条件指定は行いません
   *
   * @return 受講生一覧（全件）
   */
  @GetMapping("/api/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生検索
   * IDに紐づく任意の受講生の情報を取得します
   *
   * @param id 受講生ID
   * @return 受講生情報
   */
  @GetMapping("/api/student/detail/{id}")
  public StudentDetail showStudent(@PathVariable Integer id){
    return service.getStudentById(id);
  }

  @PostMapping("/api/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail){
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  @PostMapping("/api/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail){
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました");
  }
}
