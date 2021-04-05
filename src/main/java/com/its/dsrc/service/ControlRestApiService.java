package com.its.dsrc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = {Exception.class})
public class ControlRestApiService {

    //@Autowired
    //private CommServerMapper tscSsipApiMapper;

    //@Autowired
    //private TscSsipApiDao tscOpenApiDao;

}
