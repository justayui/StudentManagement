package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 受講生を条件設定して検索するためのレコードです。
 *
 * @param name 氏名（部分一致）
 * @param nameKana フリガナ（部分一致）
 * @param email メールアドレス（完全一致）
 */
@Schema(description = "受講生の検索条件")
public record StudentSearchCondition(
    @Schema(description = "検索条件に設定する氏名です。部分一致で可。",example = "田中")
    String name,

    @Schema(description = "検索条件に設定するフリガナです。部分一致で可。",example = "タナカ")
    String nameKana,

    @Schema(description = "検索条件に設定するメールアドレスです。完全一致が必要です。",example = "test@example.com")
    String email
    ){}
