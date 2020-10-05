import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class client{
	private JFrame frame;
	private JPanel top, mid, bottom, portPanel, IPPanel, connectionPanel;
	private JLabel portLabel;
	private JTextField portTxtField;
	
	
	public static void main(String[] args){
		client newClient = new client();
		newClient.setUpGUI();
	}
	
	public void setUpGUI(){
		frame = new JFrame("CP372 A1 - Zachary Luloff/Mitchell Mactaggart");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pane = new JPanel();
		frame.setContentPane(pane);
		pane.setLayout(new BorderLayout());
		
		top = new JPanel();
		mid = new JPanel();
		bottom = new JPanel();
		top.setBackground(Color.blue);
		pane.add(top, BorderLayout.NORTH);
		pane.add(mid, BorderLayout.CENTER);
		pane.add(bottom, BorderLayout.SOUTH);
		
		
		portPanel = new JPanel();
		portPanel.setSize(233,50);
		IPPanel = new JPanel();
		connectionPanel = new JPanel();
		top.add(portPanel);
		top.add(IPPanel);
		top.add(connectionPanel);
		
		portLabel = new JLabel("Port: ");
		portTxtField = new JTextField();
		portTxtField.setSize(60, 10);
		portPanel.add(portLabel);
		portPanel.add(portTxtField);
		
		frame.setSize(800, 500);
		frame.setVisible(true);
	}
}
