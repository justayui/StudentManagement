package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.student.management.data.StudentCourse;

/**
 * 受講生コーステーブルと紐づくリポジトリです。
 */
@Mapper
public interface StudentCourseRepository {

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生コース情報（全件）
   */
  List<StudentCourse> searchCourse();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> searchStudentCourse(Integer studentId);

  /**
   * 受講生コース情報を新規登録します。IDに関しては自動採番を行います。
   *
   * @param studentCourse 受講生コース情報
   */
      void registerCourse(StudentCourse studentCourse);

  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param studentCourse 受講生コース情報
   */
   void updateStudentCourse(StudentCourse studentCourse);
}
