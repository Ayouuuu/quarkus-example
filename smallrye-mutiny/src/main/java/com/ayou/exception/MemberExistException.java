package com.ayou.exception;

public class MemberExistException extends PartyException{
    public MemberExistException() {
        super("该玩家已存在您的队伍当中！");
    }
}
