package org.jeecg.modules.demo.zmexpress.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zzh
 * @version 1.0.0
 * @ClassName ZmFileImport.java
 * @Description TODO
 * @createTime 2022年01月07日 13:34:00
 */
@Data
public class ZmFileImport {
    private String name;
    private MultipartFile file;
}
