package raisetech.student.management.exception;


import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

  //自作の例外処理用
  @ExceptionHandler(TestException.class)
  public ResponseEntity<ErrorResponse> handleTestException(TestException ex){
    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  //URLのIDに関する例外処理用
  @ExceptionHandler({MethodArgumentTypeMismatchException.class, HandlerMethodValidationException.class,
      ConstraintViolationException.class})
  public ResponseEntity<ErrorResponse> handlerTypeMismatchOrValidationException(Exception ex){
    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  //情報登録・更新時の入力チェックに関する例外処理及びJSONエラーによる例外処理
  @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
  public ResponseEntity<ErrorResponse> handlePostValidationException(Exception ex){
    List<String> details = null;
    if (ex instanceof MethodArgumentNotValidException notValidEx) {
      details = new ArrayList<>();
      for (FieldError error : notValidEx.getBindingResult().getFieldErrors()) {
        String message = error.getField() + ": " + error.getDefaultMessage();
        details.add(message);
      }
    }
    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"入力内容に不備があります、入力内容を確認してください。",details);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  //その他すべての予期しない例外を処理する
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAllException(Exception ex){
    log.error("予期せぬシステムエラーが発生しました", ex);

    ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"予期せぬシステムエラーが発生しました。");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}