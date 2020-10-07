import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class client{
	private JFrame frame;
	private JPanel top, mid, bottom, portPanel, IPPanel, connectPanel,
		sidePanel, inBoxPanel, outBoxPanel, selectPanel;
	private JLabel portLabel, IPLabel, ISBNLbl, titleLbl, authorLbl, pubLbl,
		yearLbl;
	private JTextField portTxtField, IPTxtField, ISBNTxt, titleTxt, authorTxt,
		pubTxt, yearTxt;
	private JTextArea outputBox;
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
		
		
		mid.setLayout(new BoxLayout(mid, BoxLayout.Y_AXIS));
		
		
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
		selectPanel = new JPanel();
		selectPanel.add(submitButton);
		selectPanel.add(getButton);
		selectPanel.add(updateButton);
		selectPanel.add(removeButton);
		
		sidePanel = new JPanel();
		sidePanel.setLayout(new GridLayout(2, 1));
		sendButton = new JButton("Send");
		//sendButton.addActionListener(new ) //Add listener
		clearButton = new JButton("Clear");
		//clearButton.addActionListener(new ) //Add listener
		sidePanel.add(sendButton);
		sidePanel.add(clearButton);
		selectPanel.setPreferredSize(new Dimension(600, 100));
		selectPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		selectPanel.add(sidePanel);
		mid.add(selectPanel);
		mid.add(Box.createRigidArea(new Dimension(5, 0)));
		
		//inBoxPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		inBoxPanel = new JPanel();
		inBoxPanel.setLayout(new GridLayout(4, 2));
		ISBNLbl = new JLabel("ISBN: ");
		titleLbl = new JLabel("Title: ");
		pubLbl = new JLabel("Publisher: ");
		authorLbl = new JLabel("Author: ");
		yearLbl = new JLabel("Year: ");
		ISBNTxt = new JTextField();
		titleTxt = new JTextField();
		authorTxt = new JTextField();
		pubTxt = new JTextField();
		yearTxt = new JTextField();
		
		inBoxPanel.add(ISBNLbl);
		inBoxPanel.add(ISBNTxt);
		inBoxPanel.add(titleLbl);
		inBoxPanel.add(titleTxt);
		inBoxPanel.add(authorLbl);
		inBoxPanel.add(authorTxt);
		inBoxPanel.add(pubLbl);
		inBoxPanel.add(pubTxt);
		inBoxPanel.add(authorLbl);
		inBoxPanel.add(authorTxt);
		inBoxPanel.setPreferredSize(new Dimension(600, 100));
		mid.add(inBoxPanel);
		
		outputBox = new JTextArea();
		outputBox.setPreferredSize(new Dimension(600, 100));
		outputBox.setEditable(false);
		outBoxPanel = new JPanel();
		outBoxPanel.add(outputBox);
		
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
		bottom.add(outBoxPanel);
		
		
		
		frame.setSize(800, 500);
		frame.setVisible(true);
	}
}
