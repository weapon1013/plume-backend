package com.plume.common.util;

import jakarta.annotation.PostConstruct;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.naming.Context;

@Component
@RequiredArgsConstructor
public class MailUtils {

    @Autowired
    private JavaMailSender postSender;

    private static JavaMailSender sender;

    @PostConstruct
    private void initStatic(){
        sender = this.postSender;
    }

    public static String sendMail() throws Exception {

        String authCode = RandomStringUtils.randomAlphabetic(6);

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

        messageHelper.setSubject("[PLUME] 회원가입 인증번호입니다.");
        messageHelper.setFrom("acorn_calendar@naver.com");
        messageHelper.setText(authCode,true);
        messageHelper.setTo("gksmf4721@naver.com");

        sender.send(message);

        return authCode;
    }
}
