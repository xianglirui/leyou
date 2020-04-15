package com.mapper;

import com.pojo.Step;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper//这个注解表示了这是一个mybatis的mapper类
@Repository//将这个类加入到spring容器中
public interface StepMapper {
    Step queryStep(int uid);
    void update(Step step);
    void insert(Step step);
    void update1(int active,int uid);
}
