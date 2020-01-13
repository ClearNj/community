package life.majiang.community.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import life.majiang.community.community.mode.User;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,bio) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{bio})")
    public void insert(User user);
}
