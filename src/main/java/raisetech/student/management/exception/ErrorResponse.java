package raisetech.student.management.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
@JsonPropertyOrder({ "status", "message", "timestamp", "errors" })
public class ErrorResponse {

  private final int status;
  private final String message;
  private final LocalDateTime timestamp;
  private final List<String> errors;

  // コンストラクタ（詳細エラーがない時用）
  public ErrorResponse(int status, String message) {
    this.status = status;
    this.message = message;
    this.timestamp = LocalDateTime.now();
    this.errors = null;
  }

  // コンストラクタ（詳細エラーがある時用）
  public ErrorResponse(int status, String message, List<String> errors) {
    this.status = status;
    this.message = message;
    this.timestamp = LocalDateTime.now();
    this.errors = errors;
  }
}