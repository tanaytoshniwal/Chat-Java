import java.io.*;
import java.net.*;

public class DemoServer {
	static ServerSocket serverSocket=null;
	static Socket client=null;
	class Receive1 extends Thread{
		public void run(){
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
					System.out.println(str);
				}
				br.close();
			}
			catch(IOException e){System.out.println("exception caught");}
		}
	}
	class Send1 extends Thread{
		public void run(){
			try{
				OutputStream os=client.getOutputStream();
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
	public static void main(String[] args) throws IOException{
		try{
			serverSocket=new ServerSocket(99);
			System.out.println("Socket Ready! Waiting for Client to accept...");
		}
		catch(IOException e){
			e.printStackTrace();
			System.out.println("Port is Busy!");
			System.exit(1);
		}
		client=serverSocket.accept();
		
		DemoServer obj=new DemoServer();
		
		Receive1 receiver=obj.new Receive1();
		Send1 sender=obj.new Send1();
		receiver.start();
		sender.start();
		
	}
}
