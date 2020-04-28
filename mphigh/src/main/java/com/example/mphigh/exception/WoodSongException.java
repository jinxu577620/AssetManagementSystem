package com.example.mphigh.exception;


import com.example.mphigh.result.CodeMsg;

public class WoodSongException extends RuntimeException {

    private Integer code;

    public WoodSongException(CodeMsg codeMsg) {
        super(codeMsg.getMsg());
        this.code = codeMsg.getCode();
    }

    public WoodSongException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}