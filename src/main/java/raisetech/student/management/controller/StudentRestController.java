package raisetech.student.management.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.data.StudentSearchCondition;
import raisetech.student.management.service.StudentService;
import raisetech.student.management.domain.StudentDetail;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@Validated
@RestController
public class StudentRestController implements StudentApi {
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
  @Override
  public List<StudentDetail> getStudentList(StudentSearchCondition condition){
    return service.searchStudentList(condition);
  }

  /**
   * 受講生詳細の検索
   * IDに紐づく任意の受講生の情報を取得します
   *
   * @param id 受講生ID
   * @return 受講生情報
   */
  @GetMapping("/api/student/detail/{id}")
  @Override
  public ResponseEntity<StudentDetail> showStudent(@PathVariable @Min(1) @Max(999) Integer id){
   return ResponseEntity.ok(service.getStudentById(id));
  }

  /**
   * 受講生詳細の登録
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @PostMapping("/api/registerStudent")
  @Override
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail){
    return ResponseEntity.status(HttpStatus.CREATED).body(service.registerStudent(studentDetail));
  }

  /**
   * 受講生詳細の更新
   * キャンセルフラグの更新もここで行います（論理削除）
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @PutMapping("/api/updateStudent")
  @Override
  public ResponseEntity<SuccessResponse> updateStudent(@RequestBody @Valid StudentDetail studentDetail){
    service.updateStudent(studentDetail);
    return ResponseEntity.ok(new SuccessResponse("更新処理が成功しました。"));
  }

  @Schema(description = "処理成功時の共通メッセージレスポンス")
  public record SuccessResponse(
      @Schema(description = "成功メッセージの中身", example = "更新が完了しました。")
      String message) {}

}
