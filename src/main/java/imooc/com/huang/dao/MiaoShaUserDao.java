package imooc.com.huang.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import imooc.com.huang.domain.MiaoShaUser;

@Mapper
public interface MiaoShaUserDao {
    @Select("select * from miaosha_user where id = #{id}")
	public MiaoShaUser getById(@Param("id") Long id);
    
    //注册到数据库
    @Insert("insert into miaosha_user(id,password,nickname,salt) value(#{id},#{password},#{nickname},#{salt})")
    public boolean setById(@Param("id")Long id,@Param("password")String password ,@Param("nickname")String nickname,@Param("salt")String saltP );
    @Update("update miaosha_user set password=#{password} where id = #{id}")
	public void update(MiaoShaUser updateUser);

}
