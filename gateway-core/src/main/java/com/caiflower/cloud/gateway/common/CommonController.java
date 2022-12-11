package com.caiflower.cloud.gateway.common;

import com.caiflower.commons.response.WebResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caiflower
 * @date 2022/12/9 15:56
 */
@RestController("common")
public class CommonController {

    @GetMapping("/healthy")
    public WebResponse healthy() {
        return WebResponse.generateWebResponse();
    }

}
