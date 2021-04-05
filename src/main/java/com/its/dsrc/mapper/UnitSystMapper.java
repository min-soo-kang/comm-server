package com.its.dsrc.mapper;

import com.its.dsrc.vo.voUnitSyst;
import com.its.dsrc.vo.voUnitSystStts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public abstract interface UnitSystMapper {

    public abstract int selectCount();
    public abstract List<voUnitSyst> selectAll();
    public abstract int updateUnitSystStts(@Param("obj") voUnitSystStts obj);
    public abstract int insertUnitSystSttsHs(@Param("obj") voUnitSystStts obj);
}
