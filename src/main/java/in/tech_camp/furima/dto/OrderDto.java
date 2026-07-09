package in.tech_camp.furima.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderDto {

  

    //// カード情報 ////
    @NotBlank(message = "Token can't be blank")
    private String token;

    //// 配送先情報 ////
    // 郵便番号
    @NotBlank(message = "Postal code can't be blank")
    @Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "Postal code is invalid. Enter it as follows (e.g. 123-4567)")
    private String postNumber;

    // 都道府県
    @NotNull(message = "Prefecture can't be blank")
    private Integer prefecture;

    // 市町村
    @NotBlank(message = "City can't be blank")
    private String city;

    // 番地
    @NotBlank(message = "Addresses can't be blank")
    private String block;

    // 建物（任意入力）
    private String building;

    // 携帯電話番号
    @NotBlank(message = "Phone number can't be blank")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number is invalid. Input only number")
    @Size(min = 10, max = 11, message = "Phone number is too short")
    private String phone;
}