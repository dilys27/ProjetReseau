package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Game;
import server.Client;
import server.HandleClient;

import java.awt.Font;

public class GuiPartie extends JPanel {

	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame();
	public static int width = 600;
	public static int height = 600;
	private Client c;

	public GuiPartie(Client c) {
		setupFrame();
		setupPanel();
		this.c = c;
	}

	private void setupPanel() {
		setBackground(Color.BLACK);
		addComponents();
	}

	private void setupFrame() {
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/uno_logo1.png")));
		frame.setTitle("UNO - Home");
		frame.setContentPane(this);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	public void addComponents() {
		Image img = new ImageIcon(this.getClass().getResource("/uno_logo.png")).getImage();
		JLabel picLabel = new JLabel(new ImageIcon(img));
		picLabel.setBounds(142, 5, 300, 266);
		setLayout(null);
		JLabel select = new JLabel("SELECT NUMBER OF PLAYERS");
		select.setBounds(168, 316, 248, 20);
		select.setForeground(Color.LIGHT_GRAY);
		select.setFont(new Font("Verdana", Font.BOLD, 15));
		add(select);
		add(picLabel);
		JButton button2 = new JButton("2 Players");
		button2.setBounds(88, 405, 100, 35);
		button2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				new Thread(new Game(2)).start();
			}

		});
		JButton button3 = new JButton("3 Players");
		button3.setBounds(253, 405, 100, 35);
		button3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//GameEvents.THREE_GAME
				frame.dispose();
				new Thread(new Game(3)).start();
			}

		});
		JButton button4 = new JButton("4 Players");
		button4.setBounds(418, 405, 100, 35);
		button4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//GameEvents.FOUR_GAME
				frame.dispose();
				new Thread(new Game(4)).start();
			}

		});
		add(button2);
		add(button3);
		add(button4);

	}

}
