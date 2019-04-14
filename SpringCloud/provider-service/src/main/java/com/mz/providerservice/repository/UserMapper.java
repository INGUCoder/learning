package com.mz.providerservice.repository;

import com.mz.providerservice.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface UserMapper {


    //
    @Select("select * from  users where id = #{id}")
    User selectById(@Param("id") Long id);
}
