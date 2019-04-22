//package view;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Image;
//
//import javax.swing.ImageIcon;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//
//public class Intro extends JPanel {
//
//	private static final long serialVersionUID = 1L;
//	public static int width = 600;
//	public static int height = 600;
//
//	public Intro() {
//		setSize(width, height);
//		setLayout(null);
//
//	}
//
//	@Override
//	protected void paintComponent(Graphics arg0) {
//		super.paintComponent(arg0);
//		setBackground(Color.BLACK);
//		Image img = new ImageIcon(this.getClass().getResource("/uno_logo.png")).getImage();
//		JLabel picLabel = new JLabel(new ImageIcon(img));
//		picLabel.setBounds(150, 50, 300, 266);
//		add(picLabel);
//		
//
//	}
//
//}

//package view;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Image;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//
//public class Intro extends JPanel {
//
//	private static final long serialVersionUID = 1L;
//	private JFrame frame= new JFrame ();
//	public static int width = 600;
//	public static int height = 600;
//
//	public Intro() {
//		frame.setTitle("UNO");
//		frame.setContentPane(this);
//		frame.setSize(width, height);
//		frame.setLocationRelativeTo(null);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
//		frame.setResizable(false);
//	}
//
//	@Override
//	protected void paintComponent(Graphics arg0) {
//		super.paintComponent(arg0);
//		setBackground(Color.BLACK);
//		setLayout(null);
//		Image img = new ImageIcon(this.getClass().getResource("/uno_logo.png")).getImage();
//		JLabel picLabel = new JLabel(new ImageIcon(img));
//		picLabel.setBounds(150, 50, 300, 266);
//		add(picLabel);
//		JButton button = new JButton("Play UNO");
//		button.setBounds(250, 350, 100, 35);
//		button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				closeFrame();
//				JFrame frame = new JFrame();
//				frame.setTitle("UNO");
//				frame.setSize(getSize());
//				frame.getContentPane().setBackground(Color.BLUE);
//				frame.setLocationRelativeTo(null);
//				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				frame.setVisible(true);
//				frame.setResizable(false);
//			}
//
//		});
//		add(button);
//
//	}
//	
//	public void closeFrame () {
//		frame.dispose();
//	}
//
//}
