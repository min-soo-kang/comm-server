package com.its.dsrc.mapper;

import com.its.dsrc.vo.voOBUGatherInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public abstract interface OBUGatherMapper {

    public abstract int insertOBUGatherInfoHs(@Param("obj") voOBUGatherInfo obj);
}
