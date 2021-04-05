package com.its.dsrc.mapper;

import com.its.dsrc.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public abstract interface MultimediaMapper {

    public abstract List<voDsrcOffrSect> selectDsrcOffrSectInfo();
    public abstract List<voDsrcOffrSectTraf> selectDsrcOffrSectTraf();
    public abstract int insertDsrcOffrInfrHs(@Param("obj") voDsrcOffrInfrHs obj);
}
