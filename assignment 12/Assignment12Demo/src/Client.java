import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private JTextField productIDTextField;
    private JButton sendButton;
    private JPanel mainPanel;
    private JTextArea productInfoTextArea;

    Socket socket;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    Worker worker;

    public Client() {

        try {
            socket = new Socket(InetAddress.getByName("192.168.1.17"), 12002);
            dataOutputStream  = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dataOutputStream.writeInt(Integer.parseInt(productIDTextField.getText()));

                } catch (UnknownHostException unknownHostException) {
                    unknownHostException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        worker = new Worker();
        Thread workerThread = new Thread(worker);
        workerThread.start();



    }

    private class Worker implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    String s = dataInputStream.readUTF();
                    productInfoTextArea.append(s+"\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Client");
        frame.setContentPane(new Client().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.pack();
        frame.setVisible(true);
    }
}
