package com.its.dsrc.service.restfull;

import org.springframework.transaction.annotation.Transactional;


@Transactional(rollbackFor = {Exception.class})
public class ControlRestApiService {

    //@Autowired
    //private CommServerMapper tscSsipApiMapper;

    //@Autowired
    //private TscSsipApiDao tscOpenApiDao;

 /*   public List<voNode> getNodeInfoList() throws Exception {
        //return tscSsipApiMapper.getNodeInfoList();
        return null;
    }*//*   public List<voNode> getNodeInfoList() throws Exception {
        //return tscSsipApiMapper.getNodeInfoList();
        return null;
    }*/

    //public List<voNode> getNodeInfoListFromDao() throws Exception {
    //    return tscOpenApiDao.getNodeInfoList();
    //}
/*
    public voAuthorized getApiAuthorizedInfo(String apiId, String apiToken) throws Exception {
        //return tscSsipApiMapper.getApiAuthorizedInfo(apiId, apiToken);
        return null;
    }

    public voBroker getBorkerInfo(String apiToken) {
        //return tscSsipApiMapper.getBrokerInfo(apiToken);
        return null;
    }

    public int insertInvokeHs(voApiInvoke voInvoke){
        //return tscSsipApiMapper.insertInvokeHs(voInvoke);
        return 1;
    }*/
}
