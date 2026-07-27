package raisetech.student.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student.management.data.StudentCourseStatus;
import raisetech.student.management.data.enums.EnumCourseStatus;

@MybatisTest
class StudentCourseStatusRepositoryTest {

  @Autowired
  private StudentCourseStatusRepository sut;

  @Test
  void 受講コースの申込状況の全件検索が行えること() {
    List<StudentCourseStatus> actual = sut.searchStatus ();

    assertThat(actual).hasSize(9);
  }

  @Test
  void 受講コースIDに紐づく申込状況の検索が行えること(){
    List<StudentCourseStatus> actual = sut.searchStatusByCourseId(List.of(1));

    assertThat(actual).isNotNull();
    assertThat(actual.get(0).getStatus()).isEqualTo(EnumCourseStatus.MAIN_APPLICATION);
  }

  @Test
  void コースの申込状況の登録ができること(){
    StudentCourseStatus status = new StudentCourseStatus();
    status.setCourseId(10);

    sut.registerCourseStatus(status);

    List<StudentCourseStatus> actual = sut.searchStatusByCourseId(List.of(10));

    assertThat(actual.get(0).getStatus()).isEqualTo(EnumCourseStatus.TEMPORARY_APPLICATION);
  }

  @Test
  void コースの申込状況の更新ができること(){
    List<StudentCourseStatus> statusList = sut.searchStatusByCourseId(List.of(1));
    StudentCourseStatus status = statusList.get(0);
    status.setStatus(EnumCourseStatus.IN_PROGRESS);

    sut.updateCourseStatus(status);

    List<StudentCourseStatus> actual = sut.searchStatusByCourseId(List.of(1));

    assertThat(actual.get(0).getStatus()).isEqualTo(EnumCourseStatus.IN_PROGRESS);
  }
}