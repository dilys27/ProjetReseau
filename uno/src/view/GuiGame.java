package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.TextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import model.Card;
import model.Game;
import model.Player;
import server.Client;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.GridLayout;
import javax.swing.JScrollPane;

public class GuiGame extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame();
	public static int width = 600;
	public static int height = 600;
	private Game game;
	private Client c;
	private Player player;
	private int NB_PLAYERS;
	private ArrayList<JButton> cards; // NUMBER OF CARDS BEGINNING
	private JLabel lblPlayer_1; // LABEL PLY 1
	private JLabel lblAction_1; // ACTION_LABEL 1
	private JLabel lblPlayer_4;
	private JLabel lblAction_4;
	private JLabel lblPlayer_3;
	private JLabel lblAction_3;
	private JLabel lblPlayer_2;
	private JLabel lblAction_2;
	private JLabel lblNewLabel;
	private JPanel panel_1;

	public GuiGame(Game game, Player player, Client c) {
		this.NB_PLAYERS = game.NB_PLAYERS;
		this.player = player;
		this.game = game;
		setupPanel();
		setupFrame();
		this.c = c;
	}

	private void setupPanel() { // PANEL
		setBackground(new Color(0, 0, 0));
		setLayout(null);
		addComponents();

	}

	private void setupFrame() { // FRAME
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/uno_logo1.png")));
		frame.setTitle("UNO");
		frame.setContentPane(this);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	private void addComponents() {

		TextField textField = new TextField(); // CHAT AREA
		textField.setBounds(421, 344, 153, 22);
		add(textField);

		JButton btnNewButton = new JButton("SEND"); // SEND BUTTON
		btnNewButton.setBounds(421, 372, 153, 23);
		add(btnNewButton);

		JLabel lblChat = new JLabel("CHAT");
		lblChat.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblChat.setHorizontalAlignment(SwingConstants.CENTER);
		lblChat.setBounds(474, 11, 46, 14);
		add(lblChat);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 0));
		panel.setBounds(10, 32, 403, 363);
		add(panel);
		panel.setLayout(null);

		if (getNumber_of_players() == 2) { // NUMBER OF PLAYERS IN THE GAME
			twoPlayers(panel);
		} else if (getNumber_of_players() == 3) {
			threePlayers(panel);
		} else {
			fourPlayers(panel);
		}

		lblNewLabel = new JLabel("" + game.getCard_sup().getString()); // DECK
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(166, 191, 116, 20);
		panel.add(lblNewLabel);

		JTextArea textArea = new JTextArea(); // TEXT AREA
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setBounds(421, 32, 153, 295);
		add(textArea);

		JButton btnNewButton_1 = new JButton("PASS"); // PASS BUTTON
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// GameEvents.PASS
				player.pass();
				revalidate();
				repaint();
			}
		});
		btnNewButton_1.setBounds(463, 406, 111, 23);
		add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("PICK A CARD"); // PICK A CARD BUTTON
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// GameEvents.PICK_CARD
				if (player.isTurn() == true) {
					player.addCard(1);
					cardsOnHand(player.getGuiGame().panel_1);
				}
				revalidate();
				repaint();
			}
		});
		btnNewButton_2.setBounds(463, 440, 111, 23);
		add(btnNewButton_2);

		JButton btnContreUno = new JButton("CONTRE-UNO"); // CONTRE-UNO BUTTON
		btnContreUno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// GameEvents.CONTRE_UNO
				for (Player p : game.getPlayers()) {
					// System.out.println(p.getHand().size());
					if (p.getHand().size() == 1 && p.isUno() == false) {
						p.addCard(2);
						JOptionPane.showMessageDialog(null,
								player.getName() + ": Contre - UNO\r\n" + p.getName() + ": +2 cards", "Contre - UNO",
								JOptionPane.INFORMATION_MESSAGE);

					}
					revalidate();
					repaint();
				}
			}
		});
		btnContreUno.setBounds(463, 474, 111, 23);
		add(btnContreUno);

		JButton btnNewButton_3 = new JButton("UNO");// UNO BUTTON
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// GameEvents.UNO
				player.setUno();
			}
		});
		btnNewButton_3.setBounds(463, 505, 111, 23);
		add(btnNewButton_3);

		panel_1 = new JPanel(); // CARD PANEL
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		cardsOnHand(panel_1);
		JScrollPane scrollPane = new JScrollPane(panel_1);
		scrollPane.setBounds(10, 406, 442, 122);
		add(scrollPane);

		JMenuBar menuBar = new JMenuBar(); // MENU
		frame.setJMenuBar(menuBar);

		JMenu mnAccount = new JMenu("Account");
		menuBar.add(mnAccount);

		JMenuItem mntmNick = new JMenuItem("Nick"); // CHANGE PSEUDO
		mntmNick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = JOptionPane.showInputDialog(frame, "Please enter your new name:", null);
				while (name.isEmpty())
					name = JOptionPane.showInputDialog(frame, "Please enter your new name:", null);
				player.setName(name);
				lblPlayer_1.setText(name);
			}

		});
		mnAccount.add(mntmNick);

		JMenu mnOptions = new JMenu("Options"); // MENU OPTIONS
		menuBar.add(mnOptions);

		JMenuItem mntmHome = new JMenuItem("Home"); // MENU HOME
		mntmHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				new GuiPartie(c);
			}
		});
		mnOptions.add(mntmHome);

		JMenuItem mntmDisconnect = new JMenuItem("Disconnect"); // MENU DISCONNECT
		mntmDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// GameEvents.DISCONNECT_GAME
				frame.dispose();
				new GuiIntro();
			}
		});
		mnOptions.add(mntmDisconnect);

		JMenuItem mntmExit = new JMenuItem("Exit"); // EXIT
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// GameEvents.QUIT
				System.exit(0);
			}
		});
		mnOptions.add(mntmExit);
	}

	private void initNamelbl() { // LABEL NAMES
		String s = player.getName();
		if (s == "Player 1") {
			if (player.getGame().NB_PLAYERS == 4)
				lblPlayer_4 = new JLabel("Player 4");
			if (player.getGame().NB_PLAYERS == 3)
				lblPlayer_3 = new JLabel("Player 3");
			lblPlayer_2 = new JLabel("Player 2");
		} else if (s == "Player 2") {
			lblPlayer_4 = new JLabel("Player 1");
			if (player.getGame().NB_PLAYERS == 4)
				lblPlayer_3 = new JLabel("Player 4");
			if (player.getGame().NB_PLAYERS == 3)
				lblPlayer_2 = new JLabel("Player 3");
		} else if (s == "Player 3") {
			lblPlayer_4 = new JLabel("Player 2");
			lblPlayer_3 = new JLabel("Player 1");
			if (player.getGame().NB_PLAYERS == 4)
				lblPlayer_2 = new JLabel("Player 4");
		} else if (s == "Player 4") {
			lblPlayer_4 = new JLabel("Player 3");
			lblPlayer_3 = new JLabel("Player 2");
			lblPlayer_2 = new JLabel("Player 1");
		}
	}

	private void fourPlayers(JPanel panel) { // FOUR PLAYERS

		lblPlayer_1 = new JLabel(player.getName());
		plyrSet(lblPlayer_1);
		lblPlayer_1.setBounds(166, 304, 74, 26);
		panel.add(lblPlayer_1);

		lblAction_1 = new JLabel("Action...");
		plyrLab(lblAction_1);
		lblAction_1.setBounds(166, 338, 74, 14);
		panel.add(lblAction_1);

		lblPlayer_4 = new JLabel("Player 4");
		plyrSet(lblPlayer_4);
		lblPlayer_4.setBounds(307, 188, 74, 26);
		panel.add(lblPlayer_4);

		lblAction_4 = new JLabel("Action...");
		plyrLab(lblAction_4);
		lblAction_4.setBounds(307, 225, 74, 14);
		panel.add(lblAction_4);

		lblPlayer_3 = new JLabel("Player 3");
		plyrSet(lblPlayer_3);
		lblPlayer_3.setBounds(166, 34, 74, 26);
		panel.add(lblPlayer_3);

		lblAction_3 = new JLabel("Action...");
		plyrLab(lblAction_3);
		lblAction_3.setBounds(166, 71, 74, 14);
		panel.add(lblAction_3);

		lblPlayer_2 = new JLabel("Player 2");
		plyrSet(lblPlayer_2);
		lblPlayer_2.setBounds(37, 188, 74, 26);
		panel.add(lblPlayer_2);

		lblAction_2 = new JLabel("Action...");
		plyrLab(lblAction_2);
		lblAction_2.setBounds(37, 225, 74, 14);
		panel.add(lblAction_2);
	}

	private void threePlayers(JPanel panel) {// THREE PLAYERS
		lblPlayer_1 = new JLabel(player.getName());
		plyrSet(lblPlayer_1);
		lblPlayer_1.setBounds(166, 304, 74, 26);
		panel.add(lblPlayer_1);

		lblAction_1 = new JLabel("Action...");
		plyrLab(lblAction_1);
		lblAction_1.setBounds(166, 338, 74, 14);
		panel.add(lblAction_1);

		lblPlayer_2 = new JLabel("Player 2");
		plyrSet(lblPlayer_2);
		lblPlayer_2.setBounds(37, 188, 74, 26);
		panel.add(lblPlayer_2);

		lblAction_2 = new JLabel("Action...");
		plyrLab(lblAction_2);
		lblAction_2.setBounds(37, 225, 74, 14);
		panel.add(lblAction_2);

		lblPlayer_3 = new JLabel("Player 3");
		plyrSet(lblPlayer_3);
		lblPlayer_3.setBounds(166, 34, 74, 26);
		panel.add(lblPlayer_3);

		lblAction_3 = new JLabel("Action...");
		plyrLab(lblAction_3);
		lblAction_3.setBounds(166, 71, 74, 14);
		panel.add(lblAction_3);
	}

	private void twoPlayers(JPanel panel) { // TWO PLAYERS
		lblPlayer_1 = new JLabel(player.getName());
		plyrSet(lblPlayer_1);
		lblPlayer_1.setBounds(166, 304, 74, 26);
		panel.add(lblPlayer_1);

		lblAction_1 = new JLabel("Action...");
		plyrLab(lblAction_1);
		lblAction_1.setBounds(166, 338, 74, 14);
		panel.add(lblAction_1);

		if (player.getName() == "Player 1") {
			lblPlayer_2 = new JLabel("Player 2");
		} else if (player.getName() == "Player 2") {
			lblPlayer_2 = new JLabel("Player 1");
		}

		lblPlayer_2 = new JLabel("Player 2");
		plyrSet(lblPlayer_2);
		lblPlayer_2.setBounds(37, 188, 74, 26);
		panel.add(lblPlayer_2);

		lblAction_2 = new JLabel("Action...");
		plyrLab(lblAction_2);
		lblAction_2.setBounds(37, 225, 74, 14);
		panel.add(lblAction_2);
	}

	public void cardsOnHand(JPanel panel_1) { // CARDS ON HAND
		panel_1.removeAll();
		for (int i = 0; i < player.getHand().size(); i++) {
			JButton b = new JButton(player.getHand().get(i).getString());
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (player.isTurn() == true) {
						int x = 0;
						for (Card c : player.getHand()) {
							if (b.getText().equals(c.getString())) {
								x = player.getHand().indexOf(c);

							}
						}
						System.out.println(b.getText() + x);
						if (x > -1 && x < player.getHand().size() + 1) {
//						System.out.println("ccccc");
							Card c = player.getHand().get(x);
							if (player.playCard(c)) {
								panel_1.remove(b);
								System.out.println("ccccc");

							}

						}
//					player.setTurn(true);
						revalidate();
						repaint();
					}
				}
			});
			panel_1.add(b);

		}

	}

	public void getLblAction(int x, String s) {
		if (x == 0)
			lblAction_1.setText(s);
		else if (x == 1)
			lblAction_2.setText(s);
		else if (x == 2)
			lblAction_3.setText(s);
		else
			lblAction_4.setText(s);
	}

	private void plyrLab(JLabel lblAction_1) { // DISPLAY SETTINGS
		lblAction_1.setBackground(SystemColor.activeCaptionBorder);
		lblAction_1.setOpaque(true);
		lblAction_1.setHorizontalAlignment(SwingConstants.CENTER);
	}

	private void plyrSet(JLabel lblPlayer_1) { // DISPLAY SETTINGS
		lblPlayer_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer_1.setFont(new Font("Verdana", Font.BOLD, 13));
	}

	public int getNumber_of_players() {
		return NB_PLAYERS;
	}

	public void setNumber_of_players(int number_of_players) {
		this.NB_PLAYERS = number_of_players;
	}

	public JPanel getPanel_1() {
		return panel_1;
	}

	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}

	public void setLblNewLabel(String s) {
		lblNewLabel.setText(s);
	}

}
