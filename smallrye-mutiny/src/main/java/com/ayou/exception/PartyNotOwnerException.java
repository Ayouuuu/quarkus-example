package com.ayou.exception;

public class PartyNotOwnerException extends PartyException{
    public PartyNotOwnerException() {
        super("抱歉您不是队长！无法使用该指令");
    }
}
