package com.its.dsrc.mapper;

import com.its.dsrc.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public abstract interface DsrcSectMapper {

    public abstract int selectCount();
    public abstract List<voDsrcSect> selectAll();
    public abstract int insertRseSectTrafHs(@Param("obj") voDsrcSectTraf obj);
    public abstract int insertRseSectPassHs(@Param("obj") voDsrcSectPass obj);
}
