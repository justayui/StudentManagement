package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.data.enums.EnumGender;

@Schema(description = "受講生")
@Getter
@Setter
public class Student {

  @Schema(description = "受講生を一意に識別するためのIDです。自動採番されます。",examples = "100")
  private int id;

  @NotBlank
  @Schema(description = "受講生氏名。",example ="山田太郎")
  private String name;

  @Schema(description = "受講生の年齢。空欄可。",example = "30")
  private int age;

  @NotBlank
  @Schema(description = "受講生氏名のフリガナ。カタカナ表記。",example = "ヤマダタロウ")
  private String nameKana;

  @NotBlank
  @Schema(description = "受講生のニックネーム。",example = "タロー")
  private String nickname;

  @NotBlank
  @Email
  @Schema(description = "受講生のメールアドレス。一意の形式である必要があります。",example = "taro@example.com")
  private String email;

  @Schema(description = "受講生の性別。男性・女性・その他の3つから選択します。空欄可。",example = "男性")
  private EnumGender gender;

  @NotBlank
  @Schema(description = "受講生の居住地。",example = "福岡県福岡市")
  private String placeOfResidence;

  @Schema(description = "受講生に関する特記事項やメモを記載する備考欄です。特になければ空欄。",example = "連絡は17時以降を希望。")
  private String remark;

  @Schema(description = "削除フラグ（true: 削除済み / false: 有効なデータ）", example = "false")
  private boolean isDeleted;
}
