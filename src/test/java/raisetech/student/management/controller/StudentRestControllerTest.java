package raisetech.student.management.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.exception.TestException;
import raisetech.student.management.service.StudentService;

@WebMvcTest(StudentRestController.class)
class StudentRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  //受講生詳細一覧検索に関するテスト
  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/studentList"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細一覧検索＿登録データが0件で例外が発生したときに404エラーが返ること()
      throws Exception {
    when(service.searchStudentList()).thenThrow(
        new TestException("現在、登録されている学生情報は0件です。"));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/studentList"))
        .andExpect(status().isNotFound());

    verify(service, times(1)).searchStudentList();
  }

  //IDに紐づく受講生詳細の検索に関するテスト
  @Test
  void IDに紐づく受講生が存在するとき＿該当する受講生情報が返ってくること() throws Exception {
    when(service.getStudentById(1)).thenReturn(new StudentDetail());

    mockMvc.perform(MockMvcRequestBuilders.get("/api/student/detail/{id}", 1))
        .andExpect(status().isOk());

    verify(service, times(1)).getStudentById(1);
  }

  @Test
  void IDに紐づく受講生が存在しないとき＿404エラーが返ってくること() throws Exception {
    when(service.getStudentById(500)).thenThrow(
        new TestException("指定されたIDに該当する受講生データが存在しません。"));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/student/detail/{id}", 500))
        .andExpect(status().isNotFound());

    verify(service, times(1)).getStudentById(500);
  }

  @Test
  void IDに下限値未満の数値が渡されたとき＿入力チェックにかかり400エラーが返ること()
      throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/student/detail/{id}", 0))
        .andExpect(status().isBadRequest());
  }

  @Test
  void IDに上限値を超える数値が渡されたとき＿入力チェックにかかり400エラーが返ること()
      throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/student/detail/{id}", 1000))
        .andExpect(status().isBadRequest());
  }

  //受講生詳細の登録に関するテスト
  @Test
  void 受講生詳細の登録が実行できて空で返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/api/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "student":{
                     "name" : "田中太郎",
                     "age" : 20,
                     "nameKana" : "タナカタロウ",
                     "nickname" : "タロー",
                     "email" : "test@example.com",
                     "gender" : "male",
                     "placeOfResidence" : "福岡",
                     "remark" : ""
                  },
                  "studentCourseList":[
                     {
                       "courseName" : "Javaフルコース"
                     }
                  ]
                }
                """
            ))
        .andExpect(status().isCreated());

    verify(service, times(1)).registerStudent(any());
  }

  @Test
  void 受講生詳細の更新が実行できること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/api/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "student":{
                     "id" : "10",
                     "name" : "田中太郎",
                     "age" : 20,
                     "nameKana" : "タナカタロウ",
                     "nickname" : "タロー",
                     "email" : "test@example.com",
                     "gender" : "male",
                     "placeOfResidence" : "福岡",
                     "remark" : ""
                  },
                  "studentCourseList":[
                     {
                       "id" : 20,
                       "studentId" : 10,
                       "courseName" : "Javaフルコース",
                       "startDate" : "2026-07-07",
                       "endDate" : "2027-07-07"
                     }
                  ]
                }
                """
            ))
        .andExpect(status().isOk());

    verify(service, times(1)).updateStudent(any());
  }

  //登録・更新窓口の全項目バリデーションテスト
  @Test
  void 受講生情報に適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId(1);
    student.setName("田中太郎");
    student.setAge(20);
    student.setNameKana("タナカタロウ");
    student.setNickname("タロー");
    student.setEmail("test@example.com");
    student.setGender("male");
    student.setPlaceOfResidence("福岡");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations).hasSize(0);
  }

  @ParameterizedTest
  @CsvSource({
      "name, ''",              // 1. 氏名が空（@NotBlank）
      "nameKana, ''",          // 2. フリガナが空（@NotBlank）
      "nickname, ''",          // 3. ニックネームが空（@NotBlank）
      "email, ''",             // 4. メールアドレスが空（@NotBlank）
      "email, 'invalid-email'",// 5. メールアドレスの形式不正（@Email）
      "placeOfResidence, ''",   // 6. 居住地が空（@NotBlank）
      "courseName, ''"         //
  })
  void 受講生登録＿すべての必須項目および形式チェックでバリデーションエラー時に400を返すこと(
      String field, String invalidValue) throws Exception {
    String name = field.equals("name") ? invalidValue : "田中太郎";
    String nameKana = field.equals("nameKana") ? invalidValue : "タナカタロウ";
    String nickname = field.equals("nickname") ? invalidValue : "タロー";
    String email = field.equals("email") ? invalidValue : "test@example.com";
    String placeOfResidence = field.equals("placeOfResidence") ? invalidValue : "福岡県福岡市";
    String courseName = field.equals("courseName") ? invalidValue : "Javaコース";

    String jsonContent = String.format("""
        {
          "student": {
            "name": "%s",
            "nameKana": "%s",
            "nickname": "%s",
            "email": "%s",
            "placeOfResidence": "%s",
            "age": 20,
            "gender": "男性"
          },
            "studentCourseList": [
                {
                 "courseName": "%s"
                 }
              ]
        }
        """, name, nameKana, nickname, email, placeOfResidence, courseName);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonContent))
        .andExpect(status().isBadRequest());
  }

//コンバーターのテスト
@Test
void 受講生と受講生コース情報を受講生詳細に変換できること() throws Exception{
    Student student = new Student();
    student.setId(1);
    List<Student> studentList = new ArrayList<>();
    studentList.add(student);
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId(1);
    List<StudentCourse> courseList = new ArrayList<>();
    courseList.add(studentCourse);
    List<StudentDetail> expectedDetailList = new ArrayList<>();
    expectedDetailList.add(new StudentDetail());

    StudentConverter converter = new StudentConverter();
    List<StudentDetail> actualResult = converter.convertStudentDetails(studentList,courseList);

    Assertions.assertEquals(1,actualResult.size());
    StudentDetail actualDetail = actualResult.get(0);
    Assertions.assertEquals(1,actualDetail.getStudent().getId());

}

}
