package com.demo.platform.commons.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by github.com/wanghws on 2019-03-11.
 */
public class SysExcetpion extends Exception {
    public SysExcetpion(String code){
        super(code);
        this.code = code;
    }
    @Getter
    @Setter
    private String code;
}
