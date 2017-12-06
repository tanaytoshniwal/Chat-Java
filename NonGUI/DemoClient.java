import java.io.*;
import java.net.*;

public class DemoClient {
	static Socket clientSocket=null;
	class Send2 extends Thread{
		public void run(){
			try{
				OutputStream os=clientSocket.getOutputStream();
				DataOutputStream dos=new DataOutputStream(os);
		
				String str="";
				System.out.println("Connection Established. Write \"quit\" to exit");
				BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
				while(!(str=br.readLine()).equalsIgnoreCase("quit")){
					dos.write(str.getBytes());
					dos.write(13);
					dos.flush();
				}
				dos.close();
			}
			catch(IOException e){System.out.println("exception occured");}
		}
	}
	class Receive2 extends Thread{
		public void run(){
			try{
				BufferedReader br=null;
				try{
					//System.out.println(InetAddress.getLocalHost());
					InputStream is=clientSocket.getInputStream();
					br=new BufferedReader(new InputStreamReader(is));
				}
				catch(UnknownHostException e){
					e.printStackTrace();
					System.out.println("Unable to connect!");
				}
				String str="";
				while((str=br.readLine())!=null){
					System.out.println(str);
				}
				br.close();
			}
			catch(IOException e){System.out.println("exception caught");}
		}
	}
	public static void main(String[] args) throws IOException{
		clientSocket=new Socket(InetAddress.getLocalHost(),99);
		DemoClient obj=new DemoClient();
		
		Receive2 receiver=obj.new Receive2();
		Send2 sender=obj.new Send2();
		receiver.start();
		sender.start();
	}
}
