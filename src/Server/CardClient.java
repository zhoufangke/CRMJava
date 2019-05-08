package Server;


import java.awt.*;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import java.io.DataInputStream;

import java.io.DataOutputStream;

import java.io.IOException;

import java.net.Socket;

import java.net.UnknownHostException;

 

/**


 */

public class CardClient extends Frame {

 

    private TextField tfText;

    private TextArea taContent;

    private Socket s;

    private DataOutputStream dos;

    private DataInputStream dis;

 

    public static void main(String[] args) {

        new CardClient().launchFrame();

 

    }

 

    public void launchFrame(){

        tfText = new TextField();

        taContent = new TextArea();

        this.setSize(300,300);

        this.setLocation(300,300);

        this.tfText.addActionListener(new TFListener());

        this.add(tfText, BorderLayout.SOUTH);

        this.add(taContent,BorderLayout.NORTH);

        this.addWindowListener(new WindowAdapter(){

            @Override

            public void windowClosing(WindowEvent e) {

                System.exit(0);

            }});

        this.pack();

        this.connect();

        this.setVisible(true);

    }

 

    public void connect() {

        try {

            s = new Socket("192.168.43.156",1320);

            dos = new DataOutputStream(s.getOutputStream());

            dis = new DataInputStream(s.getInputStream());

            new Thread(new SendThread()).start();

        } catch (UnknownHostException e) {

            System.out.println("UnknownHostException");

            e.printStackTrace();

        } catch (IOException e) {

            System.out.println("IOException");

            e.printStackTrace();

        }finally{

            //关闭

        }

 

    }

 

    class TFListener implements ActionListener {

        private String str;

 

        public void actionPerformed(ActionEvent e) {

            str = tfText.getText().trim();

            tfText.setText("");

            try {

                dos.writeUTF(str);

            } catch (IOException e1) {

                System.out.println("IOException");

                e1.printStackTrace();

            }

        }

 

    }

 

    /**

     * 客户端接收消息的线程

     *

     */

    class SendThread implements Runnable{

        private String str;

        private boolean iConnect = false;

 

        public void run(){

            iConnect = true;

            recMsg();

 

        }

 

        public void recMsg() {

            try {

                while(iConnect){

                    str = dis.readUTF();

                    taContent.setText(str);

                }

            } catch (IOException e) {

                e.printStackTrace();

            }

 

        }

 

    }

 

}
