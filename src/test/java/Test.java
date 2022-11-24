import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;

public class Test {

	public Test() {
		JFrame frame = new JFrame("Test");
		frame.setSize(300,300);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JPanel panel1= new JPanel();
		panel1.setBackground(Color.black);

		frame.getContentPane().add(panel1);

		JButton button1  = new JButton("Button");

		frame.setVisible(true);
	}
	public static void main(String[] args) {
		String start = "namasayahersa";
		String hashedStart = BCrypt.hashpw(start, BCrypt.gensalt());

		System.out.println(hashedStart);

		System.out.println(BCrypt.checkpw(start, hashedStart));
	}
}
