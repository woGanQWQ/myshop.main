package com.zb.client;

import com.zb.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-server")
public interface UserInfoFeginClient {

    @GetMapping("/user/updatemoney/{id}/{money}")
    public Result updatemoney(@PathVariable("id") Integer id, @PathVariable("money") Integer money) throws Exception;

}
