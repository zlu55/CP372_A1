import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class client{
	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.setVisible(true);
		Container contentPane;
		contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		frame.setSize(700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("CP372 A1 - Zachary Luloff/Mitchell Mactaggart");
	}
}
