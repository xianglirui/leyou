package com.mapper;

import com.github.pagehelper.Page;
import com.pojo.Comment;
import com.pojo.Sell_Test;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface Sell_TestMapper {
    void add(Sell_Test sell_test);
    Page<Sell_Test> queryByUid(int uid);
}