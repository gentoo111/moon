package com.moon.admin.service.impl;

import com.moon.admin.common.utils.ReciveOneMail;
import com.moon.admin.common.utils.UserUtil;
import com.moon.admin.dao.MailDao;
import com.moon.admin.domain.Mail;
import com.moon.admin.domain.WoWorksheet;
import com.moon.admin.service.MailService;
import com.moon.admin.service.SendMailSevice;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by szz on 2018/4/8 11:07.
 * Email szhz186@gmail.com
 */
@Service
public class MailServiceImpl implements MailService{
    private static final Logger log = LoggerFactory.getLogger("adminLogger");

    @Autowired
    private SendMailSevice sendMailSevice;
    @Autowired
    private MailDao mailDao;

    @Override
    @Transactional
    public void save(Mail mail, List<String> toUser) {
        mail.setUserId(UserUtil.getCurrentUser().getId());
        mailDao.save(mail);

        toUser.forEach(u -> {
            int status = 1;
            try {
                sendMailSevice.sendMail(u, mail.getSubject(), mail.getContent());
            } catch (Exception e) {
                log.error("发送邮件失败", e);
                status = 0;
            }

            mailDao.saveToUser(mail.getId(), u, status);
        });

    }

    @Override
    @Transactional
    public void reciveMailQuertz() throws Exception {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.163.com");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, null);
        URLName urln = new URLName("pop3", "pop3.163.com", 110, null,
                "17673125001", "gentoo111");
        Store store = session.getStore(urln);
        store.connect();
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        Message message[] = folder.getMessages();
        System.out.println("邮件的数量为: " + message.length);
        ReciveOneMail pmm = null;
        for (int i = 0; i < message.length; i++) {
            System.out.println("======================");
            pmm = new ReciveOneMail((MimeMessage) message[i]);
            //判断邮件没有被处理过才继续读,如果已经处理过就直接返回
            //只处理近三天之内的邮件
            System.out.println("该邮件的发送日期为:"+pmm.getSendDateSF());


            //通知客户,邮件已经被接收,等待处理
            //构造mail对象
            Mail mail = new Mail();
            mail.setSubject(pmm.getSubject());
            //给发件人回复邮件
            mail.setToUsers(pmm.getFrom());
            mail.setContent("此邮件为系统自动发送,不需回复!请求已由系统自动接收,请等待工作人员处理!");

            mail.setCreateTime(pmm.getSendDateSF());
            mail.setUpdateTime(pmm.getSendDateSF());

            //处理收件人列表
            String toUsers = mail.getToUsers().trim();
            if (StringUtils.isBlank(toUsers)) {
                throw new IllegalArgumentException("收件人不能为空");
            }

            toUsers = StringUtils.substringBetween(toUsers, "<", ">");

            String[] strings = toUsers.split(";");

            List<String> toUser = Arrays.asList(strings);
            toUser = toUser.stream().filter(u -> !StringUtils.isBlank(u)).map(u -> u.trim()).collect(Collectors.toList());
            //发送邮件
            //这个定时任务要求五分钟拉取一次邮箱,如果有新邮件就进行处理,
            //新邮件需要进行保存并将处理情况给客户进行回复
            //在保存邮件时,由系统自动处理,所以不管登录状态直接保存
            mail.setUserId(1l);
            mailDao.save(mail);

            toUser.forEach(u -> {
                int status = 1;
                try {
                    sendMailSevice.sendMail(u,"回复:"+ mail.getSubject(), mail.getContent());
                } catch (Exception e) {
                    log.error("发送邮件失败", e);
                    status = 0;
                }

                mailDao.saveToUser(mail.getId(), u, status);
            });

            //解析页面,生成工单
            WoWorksheet woWorksheet=new WoWorksheet();
            Elements links = Jsoup.parse(pmm.getBodyText()).select("b");
            // 使用循环遍历每个标签数据
            for (Iterator<Element> it = links.iterator(); it.hasNext();) {
                Element e = (Element) it.next();
                if (e.text().equals("Work Order:")){
                    Element e2 = (Element) it.next();
                    String workOrderNo=e2.text();
                    System.out.println(workOrderNo);
                }
                if (e.text().equals("Priority:")){
                    Element e2 = (Element) it.next();
                    String priority=e2.text();
                    System.out.println(priority);
                }
                if (e.text().equals("Service:")){
                    Element e2 = (Element) it.next();
                    String service=e2.text();
                    System.out.println(service);
                }
                if (e.text().equals("Work Type:")){
                    Element e2 = (Element) it.next();
                    String workType=e2.text();
                    System.out.println(workType);
                }
                if (e.text().equals("Description:")){
                    Element e2 = (Element) it.next();
                    String description=e2.text();
                    System.out.println(description);
                }
                if (e.text().equals("Status:")){
                    Element e2 = (Element) it.next();
                    String status=e2.text();
                    System.out.println(status);
                }
                if (e.text().equals("Target Start:")){
                    Element e2 = (Element) it.next();
                    String targetStart=e2.text();
                    System.out.println(targetStart);
                }
                if (e.text().equals("Target Finish:")){
                    Element e2 = (Element) it.next();
                    String targetFinish=e2.text();
                    System.out.println(targetFinish);
                }
                if (e.text().equals("Scheduled Start:")){
                    Element e2 = (Element) it.next();
                    String scheduledStart=e2.text();
                    System.out.println(scheduledStart);
                }
                if (e.text().equals("Scheduled Finish:")){
                    Element e2 = (Element) it.next();
                    String scheduledFinish=e2.text();
                    System.out.println(scheduledFinish);
                }
                if (e.text().equals("Customer:")){
                    Element e2 = (Element) it.next();
                    String customer=e2.text();
                    System.out.println(customer);
                }
                if (e.text().equals("Location:")){
                    Element e2 = (Element) it.next();
                    String location=e2.text();
                    System.out.println(location);
                }
                if (e.text().equals("Service Address:")){
                    Element e2 = (Element) it.next();
                    String serviceAddress=e2.text();
                    System.out.println(serviceAddress);
                }
                if (e.text().equals("Additional Details:")){
                    Element e2 = (Element) it.next();
                    String additionalDetails=e2.text();
                    System.out.println(additionalDetails);
                }
                if (e.text().equals("Originating Record:")){
                    Element e2 = (Element) it.next();
                    String originatingRecord=e2.text();
                    System.out.println(originatingRecord);
                }

            }
        }
    }
}
