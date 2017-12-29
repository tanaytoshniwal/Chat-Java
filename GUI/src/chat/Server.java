/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

/**
 *
 * @author AlphaBAT69
 */
public class Server extends javax.swing.JFrame {

    /**
     * Creates new form Server
     */
    
    static ServerSocket serverSocket=null;
    static Socket client=null;
    static Server obj;
    static Receive1 receiver;
    static Send1 sender;
    public Server() {
        super("first");
        initComponents();
    }
    boolean b=true;
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
                            	area.append(str);
                                area.append("\n");
                            }
                            br.close();
			}
			catch(IOException e){}
		}
	}
	class Send1 extends Thread{
		synchronized public void run(){
			try{
                            OutputStream os=client.getOutputStream();
                            DataOutputStream dos=new DataOutputStream(os);
                            /*BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
                            while(!(str=br.readLine()).equalsIgnoreCase("quit")){
                            	dos.write(str.getBytes());
                            	dos.write(13);
                            	dos.flush();
                            }*/
                            String str=msg.getText();
                            if(!str.equals("")){
                                dos.write(str.getBytes());
                                dos.write(13);
                                dos.flush();
                            }
                            
        msg.setText("");
                            dos.close();
			}
			catch(Exception e){}
		}
	}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        area = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        msg = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        area.setColumns(20);
        area.setRows(5);
        jScrollPane1.setViewportView(area);

        jLabel1.setText("Message:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(msg))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(msg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        sender=obj.new Send1();
        sender.start();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws Exception{
		obj=new Server();
		obj.setVisible(true);
                try{
			serverSocket=new ServerSocket(99);
			System.out.println("Socket Ready! Waiting for Client to accept...");
                        
		}
		catch(IOException e){
			e.printStackTrace();
                        JOptionPane.showMessageDialog(null,e.getMessage());
			System.out.println("Port is Busy!");
			System.exit(1);
		}
		client=serverSocket.accept();
		receiver=obj.new Receive1();
                
		receiver.start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea area;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField msg;
    // End of variables declaration//GEN-END:variables
}
