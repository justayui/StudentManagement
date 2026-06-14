package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.Student;


@Mapper
public interface StudentRepository {


  @Select("SELECT * FROM students")
  List<Student> search();

  @Insert("INSERT INTO students (name, name_kana, nickname, age, gender, place_of_residence, email, remark, is_deleted) " +
      "VALUES (#{name}, #{nameKana}, #{nickname}, #{age}, #{gender}, #{placeOfResidence}, #{email}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);


}
