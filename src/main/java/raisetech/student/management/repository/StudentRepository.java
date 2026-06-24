package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.data.Student;


/**
 * 受講生テーブルと紐づくリポジトリです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return 受講生一覧（全件）
   */
  @Select("SELECT * FROM students")
  List<Student> search();

  /**
   * 受講生検索を行います。
   *
   * @param id 受講生ID
   * @return 受講生情報
   */
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(Integer id);

  /**
   * 受講生を新規登録します。IDに関しては自動採番を行います。
   *
   * @param student 受講生
   */
  @Insert("INSERT INTO students (name, name_kana, nickname, age, gender, place_of_residence, email, remark, is_deleted) " +
      "VALUES (#{name}, #{nameKana}, #{nickname}, #{age}, #{gender}, #{placeOfResidence}, #{email}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  @Update("UPDATE students SET name=#{name}, name_kana=#{nameKana}, nickname=#{nickname}, age=#{age}, gender=#{gender}, place_of_Residence=#{placeOfResidence}, email=#{email}, remark=#{remark}, is_deleted=#{isDeleted} WHERE id=#{id}")
  void updateStudent(Student student);


}
