package com.its.dsrc.mapper;

import com.its.dsrc.vo.voApiInvoke;
import com.its.dsrc.vo.voAuthorized;
import com.its.dsrc.vo.voBroker;
import com.its.dsrc.vo.voNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public abstract interface CommServerMapper {

    public abstract int selectCount();

    /*public abstract voAuthorized getApiAuthorizedInfo(@Param("apiId") String apiId, @Param("apiToken") String apiToken);
    public abstract List<voNode> getNodeInfoList();
    public abstract voBroker getBrokerInfo(@Param("apiToken") String apiToken);
    public abstract int insertInvokeHs(voApiInvoke voInvoke);*/
}
