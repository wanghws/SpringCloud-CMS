package com.demo.platform.passport.service;

import com.demo.platform.account.dto.PassportDTO;
import com.demo.platform.account.dto.UserDetailDTO;
import com.demo.platform.commons.constants.GlobalResult;
import com.demo.platform.passport.entity.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author github.com/wanghws
 * @date 20200108
 */
@Slf4j
@Service
public class UserServiceDetailService implements UserDetailsService {

    @Resource
    private AccountApiService accountApiService;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        PassportDTO passportDTO = new PassportDTO();
        passportDTO.setLoginName(loginName);
        UserDetailDTO userDetailDTO = accountApiService.login(passportDTO);

        if (null == userDetailDTO){
            throw new UsernameNotFoundException(GlobalResult.USER_NOT_FOUND);
        }
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUsername(userDetailDTO.getLoginName());
        BeanUtils.copyProperties(userDetailDTO,securityUser);

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission: userDetailDTO.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        securityUser.setAuthorities(authorities);

        return securityUser;
    }
}
