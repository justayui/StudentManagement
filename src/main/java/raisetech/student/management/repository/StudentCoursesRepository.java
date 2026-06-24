package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.data.StudentCourse;

/**
 * 受講生コーステーブルと紐づくリポジトリです。
 */
@Mapper
public interface StudentCoursesRepository {

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生コース情報（全件）
   */
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchCourse();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentCourse> searchStudentCourse(Integer studentId);

  /**
   * 受講生コース情報を新規登録します。IDに関しては自動採番を行います。
   *
   * @param studentCourse 受講生コース情報
   */
  @Insert("INSERT INTO students_courses(id,course_name,start_date,end_date,student_id)"
      + "VALUES(#{id},#{courseName},#{startDate},#{endDate},#{studentId})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
      void registerCourse(StudentCourse studentCourse);

  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param studentCourse 受講生コース情報
   */
  @Update("UPDATE students_courses SET course_name = #{courseName} WHERE id=#{id}")
   void updateStudentCourse(StudentCourse studentCourse);
}
