package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.StudentsCourses;

@Mapper
public interface StudentCoursesRepository {

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchCourse();

  @Insert("INSERT INTO students_courses(id,course_name,start_date,end_date,student_id)"
      + "VALUES(#{id},#{courseName},#{startDate},#{endDate},#{studentId})")
      void registerCourses(StudentsCourses studentsCourses);

}
