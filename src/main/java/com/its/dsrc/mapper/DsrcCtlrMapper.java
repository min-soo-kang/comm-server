package com.its.dsrc.mapper;

import com.its.dsrc.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public abstract interface DsrcCtlrMapper {

    public abstract int selectCount();
    public abstract List<voDsrcCtlr> selectAll();
    public abstract List<voObuNonCrypt> selectObuNonCryptList();
    public abstract int updateRseCtlrStts(@Param("obj") voDsrcCtlrStts obj);
    public abstract int insertRseCtlrSttsHs(@Param("obj") voDsrcCtlrStts obj);
    public abstract int insertRseCtlrCntlHs(@Param("obj") voDsrcCtlrCntl obj);
    public abstract int insertRseCtlrLogHs(@Param("obj") voDsrcCtlrLog obj);
    public abstract int insertOBUGatherInfoHs(@Param("obj") voOBUGatherInfo obj);
}
