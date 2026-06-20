package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.data.StudentsCourses;

@Mapper
public interface StudentCoursesRepository {

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchCourse();

  @Select("SELECT * FROM students_courses WHERE id = #{id}")
  StudentsCourses searchStudentCourses(Integer id);

  @Insert("INSERT INTO students_courses(id,course_name,start_date,end_date,student_id)"
      + "VALUES(#{id},#{courseName},#{startDate},#{endDate},#{studentId})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
      void registerCourses(StudentsCourses studentsCourses);

  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentsCourses> findByStudentId(Integer studentId);

  @Update("UPDATE students_courses SET course_name = #{courseName} WHERE id=#{id}")
   void updateStudentCourses(StudentsCourses studentsCourses);
}
