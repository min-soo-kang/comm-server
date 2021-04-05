package com.its.dsrc.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class voNode {
    private long   nodeId;      // 교차로 번호
    private String nodeName;    // 교차로 이름
    private String latitude;    // 위도(경위도)
    private String longitude;   // 경도(경위도)
}
