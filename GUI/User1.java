import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class User1 extends JFrame implements ActionListener{
	JButton connect,send;
	JTextArea area;
	JTextField msg;
	ServerSocket server=null;
	Socket client=null;
	public User1() throws IOException{
		setTitle("User1");
		setSize(300,450);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		connect=new JButton("connect");
		send=new JButton("send");
		area=new JTextArea();
		msg=new JTextField();
		area.setBounds(0, 50, 300, 250);
		connect.setBounds(100, 10, 100, 30);
		send.setBounds(100,360,100,30);
		msg.setBounds(0, 310, 300, 30);
		add(connect);
		add(area);
		add(send);
		add(msg);
		connect.addActionListener(this);
	}
	class Receive1 extends Thread{
		synchronized public void run(){
			try{
				BufferedReader br=null;
				try{
					InputStream is=client.getInputStream();
					br=new BufferedReader(new InputStreamReader(is));
				}
				catch(UnknownHostException e){
					e.printStackTrace();
					System.out.println("Unable to connect!");
				}
				String str="";
				while((str=br.readLine())!=null){
					area.append("User2:"+str+"\n");
				}
				br.close();
			}
			catch(IOException e){System.exit(0);}
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton b=(JButton)arg0.getSource();
		if(b==connect){
			try{
				server=new ServerSocket(99);
				System.out.println("socket ready");
				client=server.accept();
			}catch(IOException e){
				System.out.println("port busy");
				System.exit(1);
			}
			User1.this.new Receive1().start();
		}
		if(b==send){
			try{
				DataOutputStream dos=new DataOutputStream(client.getOutputStream());
				String data=msg.getText().toString();
				area.append("User1:"+data+"\n");
				dos.write(data.getBytes());
				dos.flush();
				dos.close();
			}catch(IOException e){
				System.out.println("something");
			}
		}
	}
	public static void main(String[] args) throws IOException{
		new User1().setVisible(true);
	}
}
