import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class client{
	private JFrame frame;
	private JPanel top, mid, bottom, portPanel, IPPanel, connectPanel,
		sidePanel, inBoxPanel, outBoxPanel;
	private JLabel portLabel, IPLabel;
	private JTextField portTxtField, IPTxtField;
	private JTextArea outputBox, inputBox;
	private JButton connectButton, sendButton, clearButton;
	private JRadioButton submitButton, getButton, updateButton, removeButton;
	private ButtonGroup selections;
	
	
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
		mid.setBackground(Color.red);
		bottom.setBackground(Color.green);
		pane.add(top, BorderLayout.NORTH);
		pane.add(mid, BorderLayout.CENTER);
		pane.add(bottom, BorderLayout.SOUTH);
		
		portPanel = new JPanel();
		IPPanel = new JPanel();
		connectPanel = new JPanel();
		top.add(portPanel);
		top.add(IPPanel);
		top.add(connectPanel);
		
		portLabel = new JLabel("Port: ");
		portTxtField = new JTextField("");
		portTxtField.setPreferredSize(new Dimension(60, 24));
		portTxtField.setMaximumSize(new Dimension(60, 24));
		portPanel.add(portLabel);
		portPanel.add(portTxtField);
		
		IPLabel = new JLabel("IP: ");
		IPTxtField = new JTextField("");
		IPTxtField.setPreferredSize(new Dimension(74, 24));
		IPTxtField.setMaximumSize(new Dimension(74, 24));
		IPPanel.add(IPLabel);
		IPPanel.add(IPTxtField);
		
		connectButton = new JButton("Connect/Disconnect");
		//connectButton.addActionListener(new ) //add a llistener
		connectPanel.add(connectButton);
		
		submitButton = new JRadioButton("Submit");
		getButton = new JRadioButton("Get");
		updateButton = new JRadioButton("Update");
		removeButton = new JRadioButton("Remove");
		selections = new ButtonGroup();
		selections.add(submitButton);
		selections.add(getButton);
		selections.add(updateButton);
		selections.add(removeButton);
		submitButton.setSelected(true);
		mid.add(submitButton);
		mid.add(getButton);
		mid.add(updateButton);
		mid.add(removeButton);
		
		sidePanel = new JPanel();
		sidePanel.setLayout(new GridLayout(2, 1));
		sendButton = new JButton("Send");
		//sendButton.addActionListener(new ) //Add listener
		clearButton = new JButton("Clear");
		//clearButton.addActionListener(new ) //Add listener
		sidePanel.add(sendButton);
		sidePanel.add(clearButton);
		mid.add(sidePanel);
		
		inputBox = new JTextArea();
		inputBox.setPreferredSize(new Dimension(600, 100));
		inputBox.setMaximumSize(new Dimension(600, 100));
		inputBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		inBoxPanel = new JPanel();
		inBoxPanel.add(inputBox);
		mid.add(inBoxPanel);
		
		outputBox = new JTextArea();
		outputBox.setPreferredSize(new Dimension(600, 100));
		outputBox.setMaximumSize(new Dimension(600, 100));
		outputBox.setEditable(false);
		outBoxPanel = new JPanel();
		outBoxPanel.add(outputBox);
		
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
		bottom.add(outBoxPanel);
		
		
		
		frame.setSize(800, 500);
		frame.setVisible(true);
	}
}
