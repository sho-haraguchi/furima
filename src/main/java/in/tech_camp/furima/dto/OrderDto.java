package in.tech_camp.furima.dto;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderDto {

    // バリデーションの優先順位
    public interface Priority1 {} // NotBlank
    public interface Priority2 {} // Pattern
    public interface Priority3 {} // size

    // Priority1, Priority2, Priority3の順番でチェックするよう定義
    @GroupSequence({ Priority1.class, Priority2.class, Priority3.class })
    public interface ValidationOrder {}

    //// カード情報 ////
    @NotBlank(message = "Token can't be blank", groups = Priority1.class)
    private String token;

    //// 配送先情報 ////
    // 郵便番号
    @NotBlank(message = "Postal code can't be blank", groups = Priority1.class)
    @Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "Postal code is invalid. Enter it as follows (e.g. 123-4567)", groups = Priority2.class)
    private String postNumber;

    // 都道府県
    @NotNull(message = "Prefecture can't be blank", groups = Priority1.class)
    private Integer prefecture;

    // 市町村
    @NotBlank(message = "City can't be blank", groups = Priority1.class)
    private String city;

    // 番地
    @NotBlank(message = "Addresses can't be blank", groups = Priority1.class)
    private String block;

    // 建物（任意入力）
    private String building;

    // 携帯電話番号
    @NotBlank(message = "Phone number can't be blank", groups = Priority1.class)
    @Pattern(regexp = "^[0-9]+$", message = "Phone number is invalid. Input only number", groups = Priority2.class)
    @Size(min = 10, max = 11, message = "Phone number is too short", groups = Priority3.class)
    private String phone;
}