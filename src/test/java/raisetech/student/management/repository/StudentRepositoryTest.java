package raisetech.student.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentSearchCondition;
import raisetech.student.management.data.enums.EnumGender;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること(){
    StudentSearchCondition condition = new StudentSearchCondition(null, null, null);

    List<Student> actual = sut.search(condition);

    assertThat(actual).hasSize(5);
  }

  @Test
  void 受講生の名前であいまい検索が行えること(){
    StudentSearchCondition condition = new StudentSearchCondition("山田", null, null);

    List<Student> actual = sut.search(condition);

    assertThat(actual).isNotEmpty();
    assertThat(actual).allMatch(student -> student.getName().contains("山田"));
  }

  @Test
  void 受講生のフリガナであいまい検索が行えること(){
    StudentSearchCondition condition = new StudentSearchCondition(null, "ヤマダ", null);

    List<Student> actual = sut.search(condition);

    assertThat(actual).isNotEmpty();
    assertThat(actual).allMatch(student -> student.getNameKana().contains("ヤマダ"));
  }

  @Test
  void メールアドレスで受講生の検索が行えること(){
    StudentSearchCondition condition = new StudentSearchCondition(null, null, "tanaka.taro@example.com");

    List<Student> actual = sut.search(condition);

    assertThat(actual).hasSize(1);
    assertThat(actual.get(0).getEmail()).isEqualTo("tanaka.taro@example.com");
  }

  @Test
  void 受講生の名前とメールアドレスの複数条件で絞込検索が行えること(){
    StudentSearchCondition condition = new StudentSearchCondition("田中", null, "tanaka.taro@example.com");

    List<Student> actual = sut.search(condition);

    assertThat(actual).hasSize(1);
    assertThat(actual.get(0).getName()).contains("田中");
    assertThat(actual.get(0).getEmail()).isEqualTo("tanaka.taro@example.com");
  }

  @Test
  void 受講生のID検索が行えること(){
    Student actual = sut.searchStudent(1);

    assertThat(actual).isNotNull();
    assertThat(actual.getName()).isEqualTo("田中 太郎");
    assertThat(actual.getAge()).isEqualTo(20);
    assertThat(actual.getEmail()).isEqualTo("tanaka.taro@example.com");
  }

  @Test
  void 受講生の登録が行えること(){
    Student student = new Student();
    student.setName("田中 太郎");
    student.setAge(20);
    student.setNameKana("タナカタロウ");
    student.setNickname("タロー");
    student.setEmail("test@example.com");
    student.setGender(EnumGender.male);
    student.setPlaceOfResidence("福岡");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);

    StudentSearchCondition condition = new StudentSearchCondition(null, null, null);
    List<Student> actual = sut.search(condition);
    assertThat(actual).hasSize(6);
  }

  @Test
  void 受講生の更新ができること(){
    Student student = sut.searchStudent(1);
    student.setNickname("タロ");

    sut.updateStudent(student);

    Student actual = sut.searchStudent(1);

    assertThat(actual).isNotNull();
    assertThat(actual.getNickname()).isEqualTo("タロ");
    assertThat(actual.getName()).isEqualTo("田中 太郎");
    assertThat(actual.getAge()).isEqualTo(20);
  }


}
