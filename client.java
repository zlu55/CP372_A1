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
	private JOptionPane removeWarning;
	private JCheckBox allBox;
	
	
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
		allBox = new JCheckBox("ALL");
		selectPanel = new JPanel();
		selectPanel.add(submitButton);
		selectPanel.add(getButton);
		selectPanel.add(updateButton);
		selectPanel.add(removeButton);
		selectPanel.add(allBox);
		
		sidePanel = new JPanel();
		sidePanel.setLayout(new GridLayout(2, 1));
		sendButton = new JButton("Send");
		sendButton.addActionListener(this::sendInfo);
		clearButton = new JButton("Clear");
		clearButton.addActionListener(this::clear);
		sidePanel.add(sendButton);
		sidePanel.add(clearButton);
		selectPanel.setPreferredSize(new Dimension(390, 75));
		selectPanel.setMinimumSize(new Dimension(390, 75));
		selectPanel.setMaximumSize(new Dimension(390, 75));
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
		ISBNTxt = new JTextField("978-1-61268-019-4");/////////////////////////FIX/REMOVE TEST ISBN
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
	
	
	private void sendInfo(ActionEvent event){
		if(connectionRequest.isConnected()){
			String title = titleTxt.getText();
			String author = authorTxt.getText();
			String pub = pubTxt.getText();
			int year = 0;
			String ISBN;
			String all = "NOT";
			if(allBox.isSelected()){
				all = "ALL";
			}
			if(yearTxt.getText().length() > 0){
				try{
					year = Integer.parseInt(yearTxt.getText());
				}catch(Exception e){
					outputBox.setText("Year format incorrect");
				}
			}
			ISBN = ISBNTxt.getText().replace("-", "");
			if(checkISBNValid(ISBN) == false){
				outputBox.setText("Incorrect ISBN");
			}
			
			if((submitButton.isSelected() == true || updateButton.isSelected() == true) && ISBN.equals("")){
				outputBox.setText("Enter an ISBN");
			}else if(ISBN.equals("") && title.equals("") && author.equals("") && pub.equals("") && year == 0 && all.equals("NOT")){
				outputBox.setText("Enter something");
			}else{
				checkRequest(ISBN, title, author, pub, year, all);
			}
		}else{
			outputBox.setText("No connection");
		}
	}
	
	private void checkRequest(String ISBN, String title, String author, String pub, int year, String all){
		Object[] data = {ISBN, title, author, pub, year, all};
		if (submitButton.isSelected() == true){
			connectionRequest.clientRequest("submit", data);
		}else if (getButton.isSelected() == true){
			connectionRequest.clientRequest("get", data);
		}else if (updateButton.isSelected() == true){
			connectionRequest.clientRequest("update", data);
		}else if (removeButton.isSelected() == true){
			removeWarning = new JOptionPane();
			int result = JOptionPane.showConfirmDialog(
							null,
							"Are you sure you want to remove this book?", "",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE,
							removeWarning);
			if(result == JOptionPane.YES_OPTION){
					connectionRequest.clientRequest("remove", data);
			}else if(result == JOptionPane.NO_OPTION){
				remove(removeWarning);
				outputBox.setText("Not removed");
			}
			
			//connectionRequest.clientRequest("remove", data);
		}
		 outputBox.setText(connectionRequest.returnedData());
	}
	
	private boolean checkISBNValid(String isbn){
		if(isbn.length() != 13){
			return false;
		}else{
			int finalNum = 0;
			char[] isbnArr = isbn.toCharArray();
			for(int i=0; i<12; i++){
				if(i % 2 == 0){
					finalNum += Integer.parseInt(isbnArr[i]+"");
				}else{
					finalNum += (Integer.parseInt(isbnArr[i]+"") * 3);
				}
			}
			if(finalNum / 10 == 10){
				return true;
			}else{
				return false;
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
					if(connectionRequest.isConnected()){
						System.out.println("Connected");
						outputBox.setText("");
					}else{
						outputBox.setText("Connection unsuccessful");
					}
				}catch(Exception e){
					outputBox.setText("Connection unsuccessful");
					connectButton.setSelected(false);
				}
			}else{
				try{
					connectionRequest.disconnect();
					System.out.println("Disconnected");
				}catch(Exception e){
					outputBox.setText("Connection unsuccessful");
				}
			}
		}
	}
	
}
