package com.demo.platform.account.api;


import com.demo.platform.account.dto.PassportDTO;
import com.demo.platform.account.dto.UserDetailDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author github.com/wanghws
 * @date 20191224
 * @implNote  在SpringMVC中@RequestBody @RequestParam @RequestHeader注解，在Feign中，这个value必须明确指定，否则会报错
 */
@RequestMapping("account")
public interface AccountService {

    @PostMapping("login")
    UserDetailDTO login(@RequestBody PassportDTO passportDTO);
}
