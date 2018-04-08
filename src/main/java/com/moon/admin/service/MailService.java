package com.moon.admin.service;

import com.moon.admin.domain.Mail;

import java.util.List;

/**
 * Created by szz on 2018/4/8 11:06.
 * Email szhz186@gmail.com
 */
public interface MailService {
    void save(Mail mail, List<String> toUser);
    void reciveMailQuertz() throws Exception;
}
