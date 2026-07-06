package raisetech.student.management.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Schema(description = "エラー発生時の共通レスポンス構造")
@Getter
@JsonPropertyOrder({ "status", "message", "timestamp", "errors" })
public class ErrorResponse {

  @Schema(description = "HTTPステータスコード",example = "400")
  private final int status;
  @Schema(description = "エラーメッセージ",example = "入力されたデータに不備があります。")
  private final String message;
  @Schema(description = "エラー発生日時",example = "2026-07-05T17:35:45Z")
  private final LocalDateTime timestamp;
  @Schema(description = "バリデーションエラーなどの詳細なメッセージリスト。不備がない場合は空になります。",example = "student.name:空白は許可されていません")
  private final List<String> errors;

  // コンストラクタ（詳細エラーがない時用）
  public ErrorResponse(int status, String message) {
    this.status = status;
    this.message = message;
    this.timestamp = LocalDateTime.now();
    this.errors = List.of();
  }

  // コンストラクタ（詳細エラーがある時用）
  public ErrorResponse(int status, String message, List<String> errors) {
    this.status = status;
    this.message = message;
    this.timestamp = LocalDateTime.now();
    this.errors = errors;
  }
}