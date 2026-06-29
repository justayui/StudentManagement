package raisetech.student.management.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class CustomExceptionHandler {

  //自作の例外処理用
  @ExceptionHandler(TestException.class)
  public ResponseEntity<String> handleTestException(TestException ex){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  //URLのIDに関する例外処理用
  @ExceptionHandler({MethodArgumentTypeMismatchException.class, HandlerMethodValidationException.class,
      ConstraintViolationException.class})
  public ResponseEntity<String> handlerTypeMismatchOrValidationException(Exception ex){
    String errorMessage = "IDの入力値が正しくありません。3桁までの数値を入力してください。";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
  }

  //情報登録・更新時の入力チェックに関する例外処理及びJSONエラーによる例外処理
  @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
  public ResponseEntity<String> handlePostValidationException(Exception ex){
    String errorMessage = "入力内容に不備があります、入力内容を確認してください。";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
  }
}
