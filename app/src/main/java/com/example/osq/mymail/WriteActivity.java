package com.example.osq.mymail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class WriteActivity extends AppCompatActivity {

    static OutputStream os=null;
    static BufferedReader bff=null;

//    static private final String NAME_BASE64="ZmFuaGVlckAxNjMuY29t\r\n";//用户名 base64 编码 ,注意是@前面的部分的base64编码
//    static private final String PASS_BASE64="b3NxOTY1MzE=\r\n";//密码base64编码
//    static private final String FROM="MAIL FROM: <fanheer@163.com>\r\n";//用户名,注意格式
//    static private final String TO="RCPT TO: <fanheer@163.com>\r\n";//收件人，注意格式

    Button Btnsend;
    EditText subedit,contentedit,idedit,pswedit,wfromedit,wtoedit;


    class emailthread extends Thread{
        private String NAME_BASE64;
        private String PASS_BASE64;
        private String FROM;
        private String TO;

        private String fromtx;
        private String totx;

        private String sub;
        private String contents;

        emailthread(String name,String psw,String from,String to,String from_tx,String to_tx,String sub,String contents){
            this.NAME_BASE64=name;
            this.PASS_BASE64=psw;
            this.FROM=from;
            this.TO=to;

            this.fromtx=from_tx;
            this.totx=to_tx;

            this.sub=sub;
            this.contents=contents;
        }
//        public String getSubject(String sub)
//        {
//            return this.sub;
//        }
//        public String getContents(String contents)
//        {
//            return this.contents;
//        }
        public void run()
        {
            try
            {
                Socket socket = new Socket();
                String line = null;

                socket.connect(new InetSocketAddress("smtp.163.com", 25), 3000);//连接服务器，这里用的是新浪邮箱
                bff = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                os = socket.getOutputStream();

                line=bff.readLine();

                os.write("HELO smtp.163.com\r\n".getBytes("UTF-8"));//发送问候消息
                line=bff.readLine();

                os.write("AUTH LOGIN\r\n".getBytes("UTF-8"));//发送登陆命令
                line=bff.readLine();

                os.write(NAME_BASE64.getBytes("UTF-8"));//发送用户名的base64编码
                line=bff.readLine();

                os.write(PASS_BASE64.getBytes("UTF-8"));//发送密码的base64编码
                line=bff.readLine();

                os.write(FROM.getBytes("UTF-8"));//发送用户名，应定要和前面发送的编码一致
                line=bff.readLine();

                os.write(TO.getBytes("UTF-8"));//发送收件人地址
                line=bff.readLine();

                os.write("DATA\r\n".getBytes("UTF-8"));//发送数据命令
                line=bff.readLine();

                os.write(("From:"+this.fromtx+"\r\n"//发件人，要和前面的一致
                        + "To:"+this.totx+"\r\n" //收件人，要和前面的一致
                        + "Subject:"+this.sub+"\r\n\r\n").getBytes("UTF-8"));//邮件主题

                os.write(("\r\t"+this.contents).getBytes("UTF-8"));//邮件正文内容

                os.write("\r\n.\r\n".getBytes("UTF-8"));//结束标志
                line=bff.readLine();

                os.write("QUIT\r\n".getBytes("UTF-8"));//退出登录
                line=bff.readLine();

                os.close(); // 关闭Socket输出流
                bff.close(); // 关闭Socket输入流
                socket.close(); // 关闭Socket

            } catch (Exception e){
                System.out.println("Error. " + e);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        wfromedit=(EditText)findViewById(R.id.writefromedt);
        wtoedit=(EditText)findViewById(R.id.writetoedt);

        subedit=(EditText)findViewById(R.id.writesubjectedt);
        contentedit=(EditText)findViewById(R.id.contentedt);

        Btnsend=(Button) findViewById(R.id.ensuretosend);
        Btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fromtx totx
                String w_from=wfromedit.getText().toString();
                String w_to=wtoedit.getText().toString();

                //FROM TO
                String FROM="MAIL FROM: <"+w_from+">\r\n";
                String TO="RCPT TO: <"+w_to+">\r\n";

                //id psw
                String user_id=getIntent().getStringExtra("user_id");
                String user_psw=getIntent().getStringExtra("user_psw");
                byte[] buffer_id=user_id.getBytes();
                byte[] encode_id= Base64.encode(buffer_id,Base64.DEFAULT);
                String id=new String(encode_id);
                byte[] buffer_psw=user_psw.getBytes();
                byte[] encode_psw= Base64.encode(buffer_psw,Base64.DEFAULT);
                String psw=new String(encode_psw);

                //sub content
                final String sub=subedit.getText().toString();
                final String content=contentedit.getText().toString();


                emailthread eth=new emailthread(id,psw,FROM,TO,w_from,w_to,sub,content);
                eth.start();
                Toast.makeText(WriteActivity.this,"发送成功！",Toast.LENGTH_LONG).show();
            }
        });


    }
}
