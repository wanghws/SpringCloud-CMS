package com.demo.platform.account;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author github.com/wanghws
 */
@SpringCloudApplication
@ComponentScan(basePackages={
		"com.demo.platform.commons",
		"com.demo.platform.account"
})
public class AccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}
}
