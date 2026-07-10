package in.tech_camp.furima.dto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class OrderDtoTest {

    private Validator validator;

    // Validator準備
    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("すべての入力値が正しい場合、バリデーションを通過する")
    public void testOrderDto_Success() {
        // 準備
        OrderDto dto = new OrderDto();
        dto.setToken("tok_abcdefghijk000000000000000");
        dto.setPostNumber("123-4567");
        dto.setPrefecture(1); 
        dto.setCity("横浜市緑区");
        dto.setBlock("青山1-1-1");
        dto.setBuilding("柳ビル103");
        dto.setPhone("09012345678");
        
        Set<ConstraintViolation<OrderDto>> violations = validator.validate(dto);

        // 検証
        assertTrue(violations.isEmpty(), "エラーが発生してはいけない");
    }

    @Test
    @DisplayName("トークンが空欄の場合、エラーになる")
    public void testOrderDto_Token_Blank() {
        // 準備
        OrderDto dto = new OrderDto();
        dto.setToken(""); // 空にする
        dto.setPostNumber("123-4567");
        dto.setPrefecture(1);
        dto.setCity("横浜市緑区");
        dto.setBlock("青山1-1-1");
        dto.setPhone("09012345678");

        Set<ConstraintViolation<OrderDto>> violations = validator.validate(dto);

        // 検証
        boolean hasNotBlankError = violations.stream()
                .anyMatch(v -> v.getMessage().equals("Token can't be blank"));

        assertTrue(hasNotBlankError, "トークンの空欄エラーがない！");
    }

    @Test
    @DisplayName("郵便番号が空欄の場合、エラーになる")
    public void testOrderDto_PostNumber_Blank() {
        // 準備
        OrderDto dto = new OrderDto();
        dto.setToken("tok_abcdefghijk000000000000000");
        dto.setPostNumber(""); // 空にする
        dto.setPrefecture(1);
        dto.setCity("横浜市緑区");
        dto.setBlock("青山1-1-1");
        dto.setPhone("09012345678");

        Set<ConstraintViolation<OrderDto>> violations = validator.validate(dto);

        // 検証
        boolean hasNotBlankError = violations.stream()
        .anyMatch(v -> v.getMessage().equals("Postal code can't be blank"));
        
        boolean hasPatternError = violations.stream()
        .anyMatch(v -> v.getMessage().equals("Postal code is invalid. Enter it as follows (e.g. 123-4567)"));

        assertTrue(hasNotBlankError, "空欄エラーがない！");
        assertTrue(hasPatternError, "形式エラーがない！");
    }

    @Test
    @DisplayName("郵便番号の形式が不正な場合、エラーになる")
    public void testOrderDto_PostNumber_InvalidPattern() {
        // 準備
        OrderDto dto = new OrderDto();
        dto.setToken("tok_abcdefghijk000000000000000");
        dto.setPostNumber("1234567"); // ハイフンなしにする
        dto.setPrefecture(1);
        dto.setCity("横浜市緑区");
        dto.setBlock("青山1-1-1");
        dto.setPhone("09012345678");

        Set<ConstraintViolation<OrderDto>> violations = validator.validate(dto);

        // 検証
        boolean hasPatternError = violations.stream()
                .anyMatch(v -> v.getMessage().equals("Postal code is invalid. Enter it as follows (e.g. 123-4567)"));

        assertTrue(hasPatternError, "郵便番号の形式エラーがない！");
    }

    @Test
    @DisplayName("都道府県が未選択の場合、エラーになる")
    public void testOrderDto_Prefecture_Null() {
        // 準備
        OrderDto dto = new OrderDto();
        dto.setToken("tok_abcdefghijk000000000000000");
        dto.setPostNumber("123-4567");
        dto.setPrefecture(null); // 未選択にする
        dto.setCity("横浜市緑区");
        dto.setBlock("青山1-1-1");
        dto.setPhone("09012345678");

        Set<ConstraintViolation<OrderDto>> violations = validator.validate(dto);

        // 検証
        boolean hasNotNullError = violations.stream()
                .anyMatch(v -> v.getMessage().equals("Prefecture can't be blank"));

        assertTrue(hasNotNullError, "都道府県の未選択エラーがない！");
    }

    @Test
    @DisplayName("市区町村が空欄の場合、エラーになる")
    public void testOrderDto_City_Blank() {
        // 準備
        OrderDto dto = new OrderDto();
        dto.setToken("tok_abcdefghijk000000000000000");
        dto.setPostNumber("123-4567");
        dto.setPrefecture(1);
        dto.setCity(""); // 空にする
        dto.setBlock("青山1-1-1");
        dto.setPhone("09012345678");

        Set<ConstraintViolation<OrderDto>> violations = validator.validate(dto);

        // 検証
        boolean hasNotBlankError = violations.stream()
                .anyMatch(v -> v.getMessage().equals("City can't be blank"));

        assertTrue(hasNotBlankError, "市区町村の空欄エラーがない！");
    }

    @Test
    @DisplayName("番地が空欄の場合、エラーになる")
    public void testOrderDto_Block_Blank() {
        // 準備
        OrderDto dto = new OrderDto();
        dto.setToken("tok_abcdefghijk000000000000000");
        dto.setPostNumber("123-4567");
        dto.setPrefecture(1);
        dto.setCity("横浜市緑区");
        dto.setBlock(""); // 空にする
        dto.setPhone("09012345678");

        Set<ConstraintViolation<OrderDto>> violations = validator.validate(dto);

        // 検証
        boolean hasNotBlankError = violations.stream()
                .anyMatch(v -> v.getMessage().equals("Block can't be blank"));

        assertTrue(hasNotBlankError, "番地の空欄エラーがない！");
    }

    @Test
    @DisplayName("電話番号が空欄の場合、エラーになる")
    public void testOrderDto_Phone_Blank() {
        // 準備
        OrderDto dto = new OrderDto();
        dto.setToken("tok_abcdefghijk000000000000000");
        dto.setPostNumber("123-4567");
        dto.setPrefecture(1);
        dto.setCity("横浜市緑区");
        dto.setBlock("青山1-1-1");
        dto.setPhone(""); // 空にする

        Set<ConstraintViolation<OrderDto>> violations = validator.validate(dto);

        // 検証
        boolean hasNotBlankError = violations.stream()
                .anyMatch(v -> v.getMessage().equals("Phone number can't be blank"));

        boolean hasTooShortError = violations.stream()
        .anyMatch(v -> v.getMessage().equals("Phone number is too short"));

        boolean hasPatternError = violations.stream()
                .anyMatch(v -> v.getMessage().equals("Phone number is invalid. Input only number"));

        assertTrue(hasNotBlankError, "電話番号の空欄エラーがない！");
        assertTrue(hasTooShortError, "電話番号の文字数不足エラーがない！");
        assertTrue(hasPatternError, "電話番号の形式エラーがない！");
    }

    @Test
    @DisplayName("電話番号の形式が不正な場合、エラーになる")
    public void testOrderDto_Phone_InvalidPattern() {
        // 準備
        OrderDto dto = new OrderDto();
        dto.setToken("tok_abcdefghijk000000000000000");
        dto.setPostNumber("123-4567");
        dto.setPrefecture(1);
        dto.setCity("横浜市緑区");
        dto.setBlock("青山1-1-1");
        dto.setPhone("090-1234-56"); // ハイフンを入れる（HTMLでmaxを設定しているので数字が２桁分減るから普通はそこで気付くものだが、一応。）

        Set<ConstraintViolation<OrderDto>> violations = validator.validate(dto);

        // 検証
        boolean hasPatternError = violations.stream()
                .anyMatch(v -> v.getMessage().equals("Phone number is invalid. Input only number"));

        assertTrue(hasPatternError, "電話番号の形式エラーがない！");
    }
}