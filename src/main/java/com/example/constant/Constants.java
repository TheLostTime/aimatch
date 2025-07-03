package com.example.constant;

public interface Constants {

    //0-求职者1-HR2-内推人3-运营管理员
    String USER_TYPE_EMPLOYEE = "0";
    String USER_TYPE_HR = "1";
    String USER_TYPE_INTRODUCE = "2";
    String USER_TYPE_OPERATION = "3";

    // message_type // 消息类型，必输  new_message：求职者打招呼  normal:普通发起聊天
    String MESSAGE_TYPE_NEW_MESSAGE = "new_message";
    String MESSAGE_TYPE_NORMAL = "normal";
    String MESSAGE_TYPE_HR_SAY_HELLO = "hr_say_hello";

    String VIP_NO = "NO";
    String VIP_NORMAL = "NORMAL";
    String VIP_HIGH = "HIGH";
    String VIP_EXPIRED = "EXPIRED";

    int POSITION_STATUS_DRAFT = 0;
    int POSITION_STATUS_AUDIT = 1;
    int POSITION_STATUS_ONLINE = 2;
    int POSITION_STATUS_REJECT = 3;
    int POSITION_STATUS_OFFLINE = 4;

    String COMPANY_STATUS_NO = "NO";
    String COMPANY_STATUS_YES = "YES";
    String COMPANY_STATUS_WAITING = "WAITING";
    String COMPANY_STATUS_PASS = "PASS";
    String COMPANY_STATUS_FAIL = "FAIL";


    // 简历状态：say_hello-我发起他未回、new_message-新招呼、communicating-沟通中、interview-已约面、not_suit-不合适
    String RESUME_STATUS_SAY_HELLO = "say_hello";
    String RESUME_STATUS_NEW_MESSAGE = "new_message";
    String RESUME_STATUS_COMMUNICATING = "communicating";
    String RESUME_STATUS_INTERVIEW = "interview";
    String RESUME_STATUS_NOT_SUIT = "not_suit";
}
