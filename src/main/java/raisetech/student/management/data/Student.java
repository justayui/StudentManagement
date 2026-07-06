package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter
@Entity
public class Student {

  @Schema(description = "受講生を一意に識別するためのIDです。自動採番されます。",examples = "100")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Schema(description = "受講生氏名。",example ="山田太郎")
  @NotBlank(message = "氏名は必須入力です。")
  private String name;

  @Schema(description = "受講生の年齢。空欄可。",example = "30")
  private int age;

  @Schema(description = "受講生氏名のフリガナ。カタカナ表記。",example = "ヤマダタロウ")
  @NotBlank(message = "フリガナは必須入力です。")
  private String nameKana;

  @Schema(description = "受講生のニックネーム。",example = "タロー")
  @NotBlank(message = "ニックネームは必須入力です。")
  private String nickname;

  @Schema(description = "受講生のメールアドレス。一意の形式である必要があります。",example = "taro@example.com")
  @NotBlank(message = "メールアドレスは必須入力です。")
  @Email(message = "メールアドレスの形式が不正です。")
  private String email;

  @Schema(description = "受講生の性別。空欄可。",example = "男性")
  private String gender;

  @Schema(description = "受講生の居住地。市まで入力します。",example = "福岡県福岡市")
  @NotBlank(message = "居住地は必須入力です。")
  private String placeOfResidence;

  @Schema(description = "受講生に関する特記事項やメモを記載する備考欄です。特になければ空欄。",example = "連絡は17時以降を希望。")
  private String remark;

  @Schema(description = "削除フラグ（true: 削除済み / false: 有効なデータ）", example = "false")
  private boolean isDeleted;
}
