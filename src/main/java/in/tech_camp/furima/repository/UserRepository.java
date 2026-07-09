package in.tech_camp.furima.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.furima.entity.UserEntity;

@Mapper
public interface UserRepository {

  // ユーザー情報の新規登録
  @Insert("INSERT INTO users (nickname, email, password, last_name, first_name, last_name_kana, first_name_kana, birthday) "
      +
      "VALUES (#{nickname}, #{email}, #{password}, #{lastName}, #{firstName}, #{lastNameKana}, #{firstNameKana}, #{birthday})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(UserEntity user);

  // メールアドレスからユーザー情報を検索
  @Select("SELECT * FROM users WHERE email = #{email}")
  UserEntity findByEmail(String email);
}