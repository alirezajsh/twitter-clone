package com.example.demo5.network;

import java.io.Serializable;

public class NetworkPacket implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum RequestType {
        // احراز هویت
        LOGIN,
        SIGNUP,
        SYNC_HISTORY,  // ✅ اضافه شد

        // عملیات پست
        CREATE_POST,
        REPLY_POST,
        LIKE_POST,
        UNLIKE_POST,
        RETWEET,
        VIEW_POST,
        GET_FEED,
        GET_POST_BY_ID,
        GET_REPLIES_FOR_POST,
        GET_ALL_POSTS,

        // عملیات کاربران
        FOLLOW,
        UNFOLLOW,
        CHECK_FOLLOW,
        GET_ALL_USERS,
        GET_USER_BY_ID,
        FIND_USER_BY_USERNAME,
        FIND_USER_BY_USERNAME_OR_EMAIL,
        UPDATE_USER,
        GET_USER_BY_USERNAME,

        // اشتراک‌ها
        BUY_BLUE,
        BUY_GOLD,

        // هشتگ‌ها
        SET_USER_HASHTAGS,
        GET_HASHTAG_BY_ID,
        GET_HASHTAG_BY_NAME,
        GET_ALL_HASHTAGS,

        // گزارش‌ها
        REPORT_POST,
        REPORT_USER,
        GET_ALL_POST_REPORTS,
        GET_ALL_USER_REPORTS,
        DELETE_POST_REPORT,
        DELETE_USER_REPORT,

        // ادمین
        BLOCK_USER,
        UNBLOCK_USER,
        BLOCK_POST,
        UNBLOCK_POST,
        REFRESH_ADMIN_DATA,

        // اطلاعات اضافی
        GET_USER_FULLNAME,
        GET_USER_NAME,
        GET_USER_SYMBOL,
        GET_USER_BIOGRAPHY,
        GET_USER_FOLLOWERS_COUNT,
        GET_USER_FOLLOWING_COUNT,
        GET_USER_POST_COUNT,
        GET_USER_POST_BY_INDEX,
        GET_USER_POSTS_LIST,

        // قطع ارتباط
        DISCONNECT,

        NEW_MESSAGE,


        SEND_CHAT_MESSAGE,
        GET_CHAT_HISTORY,
        GET_UNREAD_MESSAGES,
        MARK_MESSAGE_AS_READ,
    }

    private RequestType type;
    private Object data;

    public NetworkPacket(RequestType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public RequestType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}