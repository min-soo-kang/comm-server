package com.its.dsrc.mapper;

import com.its.dsrc.vo.voDsrcOffrDrct;
import com.its.dsrc.vo.voDsrcOffrSect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public abstract interface DsrcOffrMapper {

    public abstract List<voDsrcOffrSect> selectRseOffrSectAll();
    public abstract List<voDsrcOffrDrct> selectRseOffrDrctAll();
}
