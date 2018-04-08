package com.moon.admin.common.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
/**
 * Created by hasee on 2018/1/3.
 */

/**
 * 实时获取邮件,根据接收到的邮件自动生成工单
 * 如果生成工单失败，要自动发告警邮件到某个人邮箱
 * 读邮件后要发送回执给客户
 * 要将读到的邮件存在邮件表中,防止重复读
 */
public class ReciveOneMail {
    private MimeMessage mimeMessage = null;
    private String saveAttachPath = ""; //附件下载后的存放目录
    private StringBuffer bodytext = new StringBuffer();//存放邮件内容
    private String dateformat = "yyyy-MM-dd HH:mm:ss"; //默认的日期显示格式

    public ReciveOneMail(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    public void setMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    /**
     * 获得发件人的地址和姓名
     */
    public String getFrom() throws Exception {
        InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
        String from = address[0].getAddress();
        if (from == null)
            from = "";
        String personal = address[0].getPersonal();
        if (personal == null)
            personal = "";
        String fromaddr = personal + "<" + from + ">";
        return fromaddr;
    }

    /**
     * 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同 "to"----收件人 "cc"---抄送人地址 "bcc"---密送人地址
     */
    public String getMailAddress(String type) throws Exception {
        String mailaddr = "";
        String addtype = type.toUpperCase();
        InternetAddress[] address = null;
        if (addtype.equals("TO") || addtype.equals("CC")|| addtype.equals("BCC")) {
            if (addtype.equals("TO")) {
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);
            } else if (addtype.equals("CC")) {
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);
            } else {
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);
            }
            if (address != null) {
                for (int i = 0; i < address.length; i++) {
                    String email = address[i].getAddress();
                    if (email == null)
                        email = "";
                    else {
                        email = MimeUtility.decodeText(email);
                    }
                    String personal = address[i].getPersonal();
                    if (personal == null)
                        personal = "";
                    else {
                        personal = MimeUtility.decodeText(personal);
                    }
                    String compositeto = personal + "<" + email + ">";
                    mailaddr += "," + compositeto;
                }
                mailaddr = mailaddr.substring(1);
            }
        } else {
            throw new Exception("Error emailaddr type!");
        }
        return mailaddr;
    }

    /**
     * 获得邮件主题
     */
    public String getSubject() throws MessagingException {
        String subject = "";
        try {
            subject = MimeUtility.decodeText(mimeMessage.getSubject());
            if (subject == null)
                subject = "";
        } catch (Exception exce) {}
        return subject;
    }

    /**
     * 获得邮件发送日期的字符串格式
     */
    public String getSentDate() throws Exception {
        Date sentdate = mimeMessage.getSentDate();
        SimpleDateFormat format = new SimpleDateFormat(dateformat);
        return format.format(sentdate);
    }
    /**
     * 获得邮件发送日期
     */
    public Date getSendDateSF()throws Exception{
        return mimeMessage.getSentDate();
    }

    /**
     * 获得邮件正文内容
     */
    public String getBodyText() {
        return bodytext.toString();
    }

    /**
     * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
     */
    public void getMailContent(Part part) throws Exception {
        String contenttype = part.getContentType();
        int nameindex = contenttype.indexOf("name");
        boolean conname = false;
        if (nameindex != -1)
            conname = true;
        System.out.println("CONTENTTYPE: " + contenttype);
        if (part.isMimeType("text/plain") && !conname) {
            bodytext.append((String) part.getContent());
        } else if (part.isMimeType("text/html") && !conname) {
            bodytext.append((String) part.getContent());
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
                getMailContent(multipart.getBodyPart(i));
            }
        } else if (part.isMimeType("message/rfc822")) {
            getMailContent((Part) part.getContent());
        } else {}
    }

    /**
     * 判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"
     */
    public boolean getReplySign() throws MessagingException {
        boolean replysign = false;
        String needreply[] = mimeMessage
                .getHeader("Disposition-Notification-To");
        if (needreply != null) {
            replysign = true;
        }
        return replysign;
    }

    /**
     * 获得此邮件的Message-ID
     */
    public String getMessageId() throws MessagingException {
        return mimeMessage.getMessageID();
    }

    /**
     * 【判断此邮件是否已读，如果未读返回返回false,反之返回true】
     */
    public boolean isNew() throws MessagingException {
        boolean isnew = false;
        Flags flags = ((Message) mimeMessage).getFlags();
        Flags.Flag[] flag = flags.getSystemFlags();
        System.out.println("flags's length: " + flag.length);
        for (int i = 0; i < flag.length; i++) {
            if (flag[i] == Flags.Flag.SEEN) {
                isnew = true;
                System.out.println("seen Message.......");
                break;
            }
        }
        return isnew;
    }

    /**
     * 判断此邮件是否包含附件
     */
    public boolean isContainAttach(Part part) throws Exception {
        boolean attachflag = false;
        String contentType = part.getContentType();
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mpart = mp.getBodyPart(i);
                String disposition = mpart.getDisposition();
                if ((disposition != null)
                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition
                        .equals(Part.INLINE))))
                    attachflag = true;
                else if (mpart.isMimeType("multipart/*")) {
                    attachflag = isContainAttach((Part) mpart);
                } else {
                    String contype = mpart.getContentType();
                    if (contype.toLowerCase().indexOf("application") != -1)
                        attachflag = true;
                    if (contype.toLowerCase().indexOf("name") != -1)
                        attachflag = true;
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            attachflag = isContainAttach((Part) part.getContent());
        }
        return attachflag;
    }

    /**
     * 【保存附件】
     */
    public void saveAttachMent(Part part) throws Exception {
        String fileName = "";
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mpart = mp.getBodyPart(i);
                String disposition = mpart.getDisposition();
                if ((disposition != null)
                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition
                        .equals(Part.INLINE)))) {
                    fileName = mpart.getFileName();
                    if (fileName.toLowerCase().indexOf("gb2312") != -1) {
                        fileName = MimeUtility.decodeText(fileName);
                    }
                    saveFile(fileName, mpart.getInputStream());
                } else if (mpart.isMimeType("multipart/*")) {
                    saveAttachMent(mpart);
                } else {
                    fileName = mpart.getFileName();
                    if ((fileName != null)
                            && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
                        fileName = MimeUtility.decodeText(fileName);
                        saveFile(fileName, mpart.getInputStream());
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachMent((Part) part.getContent());
        }
    }

    /**
     * 【设置附件存放路径】
     */

    public void setAttachPath(String attachpath) {
        this.saveAttachPath = attachpath;
    }

    /**
     * 【设置日期显示格式】
     */
    public void setDateFormat(String format) throws Exception {
        this.dateformat = format;
    }

    /**
     * 【获得附件存放路径】
     */
    public String getAttachPath() {
        return saveAttachPath;
    }

    /**
     * 【真正的保存附件到指定目录里】
     */
    private void saveFile(String fileName, InputStream in) throws Exception {
        String osName = System.getProperty("os.name");
        String storedir = getAttachPath();
        String separator = "";
        if (osName == null)
            osName = "";
        if (osName.toLowerCase().indexOf("win") != -1) {
            separator = "\\";
            if (storedir == null || storedir.equals(""))
                storedir = "d:\\tmp";
        } else {
            separator = "/";
            storedir = "/tmp";
        }
        File storefile = new File(storedir + separator + fileName);
        System.out.println("storefile's path: " + storefile.toString());
        // for(int i=0;storefile.exists();i++){
        // storefile = new File(storedir+separator+fileName+i);
        // }
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(storefile));
            bis = new BufferedInputStream(in);
            int c;
            while ((c = bis.read()) != -1) {
                bos.write(c);
                bos.flush();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception("文件保存失败!");
        } finally {
            bos.close();
            bis.close();
        }
    }

    /**
     * PraseMimeMessage类测试
     */
    public static void main(String args[]) throws Exception {
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
        System.out.println("Messages's length: " + message.length);
        ReciveOneMail pmm = null;
        for (int i = 0; i < message.length; i++) {
            System.out.println("======================");
            pmm = new ReciveOneMail((MimeMessage) message[i]);
            System.out.println("邮件 " + i + " 的主题: " + pmm.getSubject());
            System.out.println("邮件 " + i + " 的发送时间: "+ pmm.getSentDate());
            System.out.println("邮件 " + i + " 是否需要回执: "+ pmm.getReplySign());
            System.out.println("Message " + i + " hasRead: " + pmm.isNew());
            System.out.println("Message " + i + "  containAttachment: "+ pmm.isContainAttach((Part) message[i]));
            System.out.println("邮件 " + i + " 来自于: " + pmm.getFrom());
            System.out.println("邮件 " + i + " 发送到: "+ pmm.getMailAddress("to"));
            System.out.println("邮件 " + i + " 抄送给: "+ pmm.getMailAddress("cc"));
            System.out.println("Message " + i + " bcc: "+ pmm.getMailAddress("bcc"));
            System.out.println("邮件 " + i + " Message-ID: "+ pmm.getMessageId());
            // 获得邮件内容===============
            System.out.println("=================获取邮件内容==================");
            pmm.getMailContent((Part) message[i]);
            System.out.println("邮件 " + i + "  的正文是: \r\n"
                    + pmm.getBodyText());
            System.out.println("==================附件存储到磁盘===============");
            pmm.setAttachPath("d:\\boottemp");
            pmm.saveAttachMent((Part) message[i]);

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
                    if (!description.equals("Qty:")){
                        System.out.println(description);
                    }
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
