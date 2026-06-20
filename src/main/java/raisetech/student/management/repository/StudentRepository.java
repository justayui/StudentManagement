package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.data.Student;


@Mapper
public interface StudentRepository {


  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(Integer id);

  @Insert("INSERT INTO students (name, name_kana, nickname, age, gender, place_of_residence, email, remark, is_deleted) " +
      "VALUES (#{name}, #{nameKana}, #{nickname}, #{age}, #{gender}, #{placeOfResidence}, #{email}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findById(Integer id);

  @Update("UPDATE students SET name=#{name}, name_kana=#{nameKana}, nickname=#{nickname}, age=#{age}, gender=#{gender}, place_of_Residence=#{placeOfResidence}, email=#{email}, remark=#{remark}, is_deleted=#{isDeleted} WHERE id=#{id}")
  void updateStudent(Student student);


}
