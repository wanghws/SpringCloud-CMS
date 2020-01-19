package com.demo.platform.passport.service;

import com.demo.platform.account.api.AccountService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author github.com/wanghws
 * @date 20200108
 */
@FeignClient("account-service")
public interface AccountApiService extends AccountService {
}
