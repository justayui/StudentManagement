package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.student.management.data.StudentCourseStatus;

/**
 * 受講コース申込状況と紐づくリポジトリです。
 */
@Mapper
public interface StudentCourseStatusRepository {

  /**
   * コースの申込状況の全件検索を行います。
   *
   * @return コース申込状況（全件）
   */
  List<StudentCourseStatus> searchStatus();

  /**
   * コースIDに紐づくコース申込状況を検索します。
   *
   * @param courseIds 受講コースID
   * @return コースIDに紐づくコース申込状況
   */
  List<StudentCourseStatus> searchStatusByCourseId(List<Integer> courseIds);

  /**
   * コース申込状況を新規登録します。
   * IDに関しては自動採番を行います。
   * 初期値はTEMPORARY_APPLICATION（’仮申込’）です。
   *
   * @param status 申込状況
   */
  void registerCourseStatus(StudentCourseStatus status);

  /**
   * 申込状況を更新します。
   *
   * @param status 申込状況
   */
  void updateCourseStatus(StudentCourseStatus status);
}
