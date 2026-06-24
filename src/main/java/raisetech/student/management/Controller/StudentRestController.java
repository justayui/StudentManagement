package raisetech.student.management.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
   * 受講生詳細の一覧検索
   * 全件検索を行うため、条件指定は行いません
   *
   * @return 受講生詳細一覧（全件）
   */
  @GetMapping("/api/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生詳細の検索
   * IDに紐づく任意の受講生の情報を取得します
   *
   * @param id 受講生ID
   * @return 受講生情報
   */
  @GetMapping("/api/student/detail/{id}")
  public StudentDetail showStudent(@PathVariable Integer id){
    return service.getStudentById(id);
  }

  /**
   * 受講生詳細の登録
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @PostMapping("/api/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail){
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生詳細の更新
   * キャンセルフラグの更新もここで行います（論理削除）
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @PutMapping("/api/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail){
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました");
  }
}
