package in.tech_camp.furima.form;

import in.tech_camp.furima.validation.GroupOrder;
import in.tech_camp.furima.validation.PasswordEquals;
import in.tech_camp.furima.validation.ValidDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordEquals(groups = GroupOrder.Group3.class)
@ValidDate(groups = GroupOrder.Group1.class)
public class RegisterForm {

    @NotBlank(message = "ニックネームを入力してください")
    private String nickname;

    @NotBlank(message = "メールアドレスを入力してください", groups = GroupOrder.Group1.class)
    @Email(message = "メールアドレスは「@」を含む正しい形式で入力してください", groups = GroupOrder.Group2.class)
    private String email;

    @NotBlank(message = "パスワードを入力してください", groups = GroupOrder.Group1.class)
    @Size(min = 6, message = "パスワードは6文字以上で入力してください", groups = GroupOrder.Group2.class)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$", message = "パスワードは半角英数字混合で入力してください", groups = GroupOrder.Group2.class)
    private String password;

    @NotBlank(message = "パスワード（確認）を入力してください", groups = GroupOrder.Group1.class)
    private String passwordConfirmation;

    @NotBlank(message = "名字を入力してください", groups = GroupOrder.Group1.class)
    @Pattern(regexp = "^[ぁ-んァ-ヶ一-龥々ー]+$", message = "名字は全角（漢字・ひらがな・カタカナ）で入力してください", groups = GroupOrder.Group2.class)
    private String lastName;

    @NotBlank(message = "名前を入力してください", groups = GroupOrder.Group1.class)
    @Pattern(regexp = "^[ぁ-んァ-ヶ一-龥々ー]+$", message = "名前は全角（漢字・ひらがな・カタカナ）で入力してください", groups = GroupOrder.Group2.class)
    private String firstName;

    @NotBlank(message = "名字（カナ）を入力してください", groups = GroupOrder.Group1.class)
    @Pattern(regexp = "^[ァ-ヶー]+$", message = "名字（カナ）は全角カタカナで入力してください", groups = GroupOrder.Group2.class)
    private String lastNameKana;

    @NotBlank(message = "名前（カナ）を入力してください", groups = GroupOrder.Group1.class)
    @Pattern(regexp = "^[ァ-ヶー]+$", message = "名前（カナ）は全角カタカナで入力してください", groups = GroupOrder.Group2.class)
    private String firstNameKana;

    @NotNull(message = "誕生年を選択してください", groups = GroupOrder.Group1.class)
    private Integer birthYear;

    @NotNull(message = "誕生月を選択してください", groups = GroupOrder.Group1.class)
    private Integer birthMonth;

    @NotNull(message = "誕生日を選択してください", groups = GroupOrder.Group1.class)
    private Integer birthDay;
}