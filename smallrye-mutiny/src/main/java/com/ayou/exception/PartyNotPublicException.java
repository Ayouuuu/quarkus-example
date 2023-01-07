package com.ayou.exception;

public class PartyNotPublicException extends PartyException{
    public PartyNotPublicException() {
        super("抱歉！队伍没有开放公共邀请");
    }
}
