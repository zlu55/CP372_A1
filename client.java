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
	private JScrollPane scrollPane;
	private JToggleButton connectButton;
	private JButton sendButton, clearButton;
	private JRadioButton submitButton, getButton, updateButton, removeButton;
	private ButtonGroup selections;
	private ConnectionRequest connectionRequest;
	
	
	public static void main(String[] args){
		client newClient = new client();
		newClient.setUpGUI();
		
	}
	
	public void setUpGUI(){
		connectionRequest = new ConnectionRequest();
		frame = new JFrame("CP372 A1 - Zachary Luloff/Mitchell Mactaggart");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pane = new JPanel();
		frame.setContentPane(pane);
		pane.setLayout(new BorderLayout());
		
		top = new JPanel();
		mid = new JPanel();
		bottom = new JPanel();		
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
		portTxtField = new JTextField("3000");
		portTxtField.setPreferredSize(new Dimension(60, 24));
		portTxtField.setMaximumSize(new Dimension(60, 24));
		portPanel.add(portLabel);
		portPanel.add(portTxtField);
		
		IPLabel = new JLabel("IP: ");
		IPTxtField = new JTextField("127.0.0.1");
		IPTxtField.setPreferredSize(new Dimension(90, 24));
		IPTxtField.setMaximumSize(new Dimension(90, 24));
		IPPanel.add(IPLabel);
		IPPanel.add(IPTxtField);
		
		connectButton = new JToggleButton("Connect/Disconnect");
		connectButton.setSelected(false);
		connectButton.addActionListener(new connectListener());
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
		sendButton.addActionListener(this::sendInfo);
		clearButton = new JButton("Clear");
		clearButton.addActionListener(this::clear);
		sidePanel.add(sendButton);
		sidePanel.add(clearButton);
		selectPanel.setPreferredSize(new Dimension(350, 75));
		selectPanel.setMinimumSize(new Dimension(350, 75));
		selectPanel.setMaximumSize(new Dimension(350, 75));
		selectPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		selectPanel.add(sidePanel);
		mid.add(selectPanel);
		mid.add(Box.createRigidArea(new Dimension(5, 0)));
		
		inBoxPanel = new JPanel();
		inBoxPanel.setLayout(new GridLayout(5, 2));
		ISBNLbl = new JLabel("ISBN: ");
		titleLbl = new JLabel("Title: ");
		pubLbl = new JLabel("Publisher: ");
		authorLbl = new JLabel("Author: ");
		yearLbl = new JLabel("Year: ");
		ISBNLbl.setAlignmentY(Component.RIGHT_ALIGNMENT);
		ISBNTxt = new JTextField("");
		titleTxt = new JTextField("");
		authorTxt = new JTextField("");
		pubTxt = new JTextField("");
		yearTxt = new JTextField("");
		ISBNTxt.setPreferredSize(new Dimension(74, 24));
		titleTxt.setPreferredSize(new Dimension(74, 24));
		authorTxt.setPreferredSize(new Dimension(74, 24));
		pubTxt.setPreferredSize(new Dimension(74, 24));
		yearTxt.setPreferredSize(new Dimension(74, 24));
		inBoxPanel.setMinimumSize(new Dimension(350, 125));
		inBoxPanel.setMaximumSize(new Dimension(350, 125));
		inBoxPanel.setPreferredSize(new Dimension(350, 125));
		inBoxPanel.add(ISBNLbl);
		inBoxPanel.add(ISBNTxt);
		inBoxPanel.add(titleLbl);
		inBoxPanel.add(titleTxt);
		inBoxPanel.add(authorLbl);
		inBoxPanel.add(authorTxt);
		inBoxPanel.add(pubLbl);
		inBoxPanel.add(pubTxt);
		inBoxPanel.add(yearLbl);
		inBoxPanel.add(yearTxt);
		mid.add(inBoxPanel);
		
		outputBox = new JTextArea("");
		outputBox.setEditable(false);
		outBoxPanel = new JPanel();
		scrollPane = new JScrollPane(outputBox);
		scrollPane.setPreferredSize(new Dimension(620, 200));
		scrollPane.setMinimumSize(new Dimension(620, 200));
		scrollPane.setMaximumSize(new Dimension(620, 200));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		outBoxPanel.add(scrollPane);
		
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
		bottom.add(outBoxPanel);
		
		frame.setSize(700, 500);
		frame.setVisible(true);
	}
	
	
	private void sendInfo(ActionEvent e){
		String title = titleTxt.getText();
		String author = authorTxt.getText();
		String pub = pubTxt.getText();
		int year;
		String ISBN;
		if(yearTxt.getText().length() > 0){
			try{
				year = Integer.parseInt(yearTxt.getText());
			}catch(NumberFormat e){
				System.out.println(e);
			}
		}else{
			year = 0;
		}
		ISBN = ISBNTxt.getText().replace("-", "");
		if(checkISBNValid(ISBN) == false){
			outputBox.setText("Incorrect ISBN");
		}
	}
	
	private boolean checkISBNValid(String isbn){
		if(isbn.length() != 13){
			return false;
		}else{
			int finalNum = 0;
			char[] isbnArr = isbn.toCharArray();
			for(int i=0; i<12; i++){
				if(i % 2 == 0){
					finalNum += isbnArr[i];
				}else{
					finalNum += isbnArr[i] * 3;
				}
			}
		}
	}
	
	private void clear(ActionEvent e){
		ISBNTxt.setText("");
		authorTxt.setText("");
		titleTxt.setText("");
		pubTxt.setText("");
		yearTxt.setText("");
		outputBox.setText("");
	}
	
	private class connectListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(connectButton.isSelected() == true){
				try{
					connectionRequest.newConnect(IPTxtField.getText(), Integer.parseInt(portTxtField.getText()));
					System.out.println("Connected");
				}catch(Exception e){
					System.out.println(e);
				}
			}else{
				try{
					connectionRequest.disconnect();
					System.out.println("Disconnected");
				}catch(Exception e){
					System.out.println(e);
				}
			}
		}
	}
	
}
