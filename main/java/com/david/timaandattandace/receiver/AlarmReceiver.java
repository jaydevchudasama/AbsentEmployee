package com.david.timaandattandace.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;

import android.widget.Toast;

import com.david.timaandattandace.database.Database;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String SMTP_HOST_NAME = "smtp.gmail.com"; //can be your host server smtp@yourdomain.com
    private static final String SMTP_AUTH_USER = "vandit0296@gmail.com"; //your login username/email
    private static final String SMTP_AUTH_PWD = "8000889505"; //password/secret

    private static Message message;

    @Override
    public void onReceive(Context context, Intent intent) {
        int i = 0;
        boolean iscursor = false;
        Database db = new Database(context);
        Cursor cursor = db.excal_clock();
        Cursor cursor1 = db.excal_shift();
        File file = Environment.getExternalStorageDirectory();
        String cfile = "myExcel.xls";
        File directory = new File(file.getAbsolutePath());
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {
            File file1 = new File(directory, cfile);
            WorkbookSettings workbookSettings = new WorkbookSettings();
            workbookSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook writableWorkbook;
            writableWorkbook = Workbook.createWorkbook(file1, workbookSettings);
            WritableSheet sheet = writableWorkbook.createSheet("userlist", 0);
            sheet.addCell(new Label(0, 0, "Empid"));
            sheet.addCell(new Label(1, 0, "Type"));
            sheet.addCell(new Label(2, 0, "Date"));
            sheet.addCell(new Label(3, 0, "Time"));
            if (cursor.moveToNext()) {
                do {
                    iscursor = true;
                    i = cursor.getPosition() + 1;
                    sheet.addCell(new Label(0, i, cursor.getInt(1) + ""));
                    String type;
                    if (cursor.getInt(2) == 1) {
                        type = "Clock In";
                    } else {
                        type = "Clock Out";
                    }

                    sheet.addCell(new Label(1, i, type));
                    sheet.addCell(new Label(2, i, cursor.getString(3)));
                    sheet.addCell(new Label(3, i, cursor.getString(4)));
                } while (cursor.moveToNext());
            }
            if (iscursor) {
                i = i + 1;
                sheet.addCell(new Label(0, i, null));
                i = i + 1;
                sheet.addCell(new Label(0, i, null));
                i = i + 1;
                sheet.addCell(new Label(0, i, null));
                i = i + 1;
                sheet.addCell(new Label(0, i, null));
            }
            if (cursor1.moveToNext()) {
                i = 0;
                sheet.addCell(new Label(6, i, "Emp Id"));
                sheet.addCell(new Label(7, i, "Type"));
                sheet.addCell(new Label(8, i, "From Date"));
                sheet.addCell(new Label(9, i, "From Time"));
                sheet.addCell(new Label(10, i, "To Date"));
                sheet.addCell(new Label(11, i, "To Time"));
                do {
                    i = cursor1.getPosition() + 1;
                    sheet.addCell(new Label(6, i, cursor1.getInt(1) + ""));
                    String days;
                    if (cursor1.getInt(2) == 1) {
                        days = "Sickdays";
                    } else {
                        days = "Holidays";
                    }
                    sheet.addCell(new Label(7, i, days));
                    sheet.addCell(new Label(8, i, cursor1.getString(3)));
                    sheet.addCell(new Label(9, i, cursor1.getString(4)));
                    sheet.addCell(new Label(10, i, cursor1.getString(5)));
                    sheet.addCell(new Label(11, i, cursor1.getString(6)));
                } while (cursor1.moveToNext());
            }

            cursor.close();
            cursor1.close();
            writableWorkbook.write();
            writableWorkbook.close();
            Toast.makeText(context, "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }


        String from = "vandit0296@gmail.com"; //from
        String to = "test12apk@gmail.com";
        String subject = "Test";
        String msg = "Test apk";
        final String username = SMTP_AUTH_USER;
        final String password = SMTP_AUTH_PWD;

        // Assuming you are sending email through relay.jangosmtp.net
        String host = SMTP_HOST_NAME;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setContent(msg, "text/html");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

//            // Part two is attachment
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            File att = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myExcel.xls");

            if (att.exists()) {


                //    mimeBodyPart.attachFile(att);

                DataSource source = new FileDataSource(att);
                mimeBodyPart.setDataHandler(new DataHandler(source));
                mimeBodyPart.setFileName(att.getName());
                multipart.addBodyPart(mimeBodyPart);
                message.setContent(multipart);
            } else {

            }
            // Send the complete message parts
            message.setContent(multipart);

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {

                        // Send message
                        Transport.send(message);
                        System.out.println("Sent message successfully....");
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            });

            thread.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
