package com.its.dsrc.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class voApiInvoke {

    String apiId;
    String apiToken;
    String ipAddr;
    String eventDt;
    int error;

    public voApiInvoke(String apiId,String apiToken, String ipAddr, String eventDt, int error) {
        this.apiId = apiId;
        this.apiToken = apiToken;
        this.ipAddr = ipAddr;
        this.eventDt = eventDt;
        this.error = error;
    }

}
