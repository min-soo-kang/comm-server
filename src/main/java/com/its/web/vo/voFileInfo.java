package com.its.web.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class voFileInfo {
    private List<voFileInfo> fileInfos= new ArrayList<>();
    private int id;
    private String type;
    private String fileName;
    private long fileSize;
    private String filePath;
    private int parent;
}
