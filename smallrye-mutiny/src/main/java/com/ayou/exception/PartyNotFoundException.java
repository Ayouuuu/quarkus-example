package com.ayou.exception;

public class PartyNotFoundException extends PartyException{
    public PartyNotFoundException() {
        super("队伍找不到");
    }
}
