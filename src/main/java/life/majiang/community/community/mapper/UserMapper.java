package life.majiang.community.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import life.majiang.community.community.mode.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,bio) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{bio})")
     void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
}
