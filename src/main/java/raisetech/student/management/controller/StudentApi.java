package raisetech.student.management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import raisetech.student.management.controller.StudentRestController.SuccessResponse;
import raisetech.student.management.data.StudentSearchCondition;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.exception.ErrorResponse;

@Tag(name = "受講生管理", description = "受講生の検索・登録・更新を行うAPI群")
public interface StudentApi {

  @Operation(summary = "受講生一覧検索",description = "受講生の一覧検索をします。")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "取得成功。受講生一覧を返却します。"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "エラー：現在、登録されている学生情報は0件です。",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "サーバー内部エラー。データベースへの接続失敗など、予期せぬシステム異常が発生した場合に返却されます。",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  List<StudentDetail> getStudentList(StudentSearchCondition condition);

  @Operation(summary = "受講生ID検索",description = "指定したIDに紐づく受講生の情報を検索します。")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "取得成功。該当する受講生詳細情報を返却します。"
      ),
      @ApiResponse(
          responseCode = "400",
          description = "エラー：リクエスト形式が不正です。IDには1～999までの数値を指定してください。",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "エラー：指定されたIDに該当する受講生データが存在しません。",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "サーバー内部エラー。データベースへの接続失敗など、予期せぬシステム異常が発生した場合に返却されます。",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))
      )
  })
  ResponseEntity<StudentDetail> showStudent(
      @Parameter(
          name = "id",
          description = "受講生を一意に識別するID(1～999の範囲)",
          required = true,
          example = "1"
      )@Min(1) @Max(999) Integer id
  );

  @Operation(summary = "受講生詳細登録", description = "新規の受講生情報とそれに紐づく受講コース情報の登録を行います。")
  @ApiResponses(value ={
      @ApiResponse(
          responseCode = "201",
          description = "登録成功。登録済みの受講生一覧を返却します。"
      ),
      @ApiResponse(
          responseCode = "400",
          description = "エラー：入力されたデータに不備があります。バリデーションエラーの詳細はレスポンスの 'errors' に格納されます。",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "サーバー内部エラー。データベースへの接続失敗など、予期せぬシステム異常が発生した場合に返却されます。",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))
      )
  })
  ResponseEntity<StudentDetail> registerStudent(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "登録する受講生の情報（名前、年齢、コースリストなど）",
          required = true
      )
      @Valid @RequestBody StudentDetail studentDetail
  );

  @Operation(summary = "受講生詳細更新", description = "受講生と受講生コース情報をそれぞれ更新します。キャンセルフラグの更新もここで行います（論理削除）")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "更新処理が成功しました。"
      ),
  @ApiResponse(
      responseCode = "400",
      description = "エラー：入力されたデータに不備があります。バリデーションエラーの詳細はレスポンスの 'errors' に格納されます。",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class))
      ),
  @ApiResponse(
      responseCode = "500",
      description = "サーバー内部エラーが発生しました。",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class))
      )
  })
  ResponseEntity<SuccessResponse> updateStudent(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "更新する受講生の情報",
          required = true
      )
      @Valid @RequestBody StudentDetail studentDetail
  );

}
