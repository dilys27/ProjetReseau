package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import server.Client;

public class GuiIntro extends JPanel {

	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame();
	public static int width = 600;
	public static int height = 600;

	public GuiIntro() {
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/uno_logo1.png")));
		frame.setTitle("UNO - Welcome");
		frame.setContentPane(this);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		setBackground(Color.BLACK);
		setLayout(null);
		Image img = new ImageIcon(this.getClass().getResource("/uno_logo.png")).getImage();
		JLabel picLabel = new JLabel(new ImageIcon(img));
		picLabel.setBounds(150, 50, 300, 266);
		add(picLabel);
		JButton button = new JButton("Play UNO");
		button.setBounds(250, 350, 100, 35);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Client c = new Client();
				frame.dispose();
				new GuiPartie(c);
			}

		});
		add(button);

	}
	
	public static void main(String... args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        for (int i = 0; i < 10; i++) {
            panel.add(new JButton("Hello-" + i));
        }
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(50, 30, 300, 50);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(500, 400));
        contentPane.add(scrollPane);
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

}
