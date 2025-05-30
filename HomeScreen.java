import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class HomeScreen {

    public JFrame frame;
    private JPanel panel;
    private JLabel background;
    private ImageIcon chipotleBackground;
    private boolean isPlaying;

    HomeScreen() {
        isPlaying = false;
        panel = new JPanel();
        frame = new JFrame("Chipotle Vault");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 780);
        frame.setLocationRelativeTo(null);

        chipotleBackground = new ImageIcon("images/HomePage.jpg");
        background = new JLabel(chipotleBackground);

        panel.add(background);

       
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                System.out.println("X: " + x + " Y: " + y);
                
                if (x > 146 && x < 340 && y > 491 && y < 591 && !isPlaying) {
                    JOptionPane.showMessageDialog(
                        frame,
                        "To win, you must guess the correct combination of ingredients for a Chipotle meal!\n" +
                        "You only get 3 tries so make the most of it. Winners receive a BOGO chipotle code!"
                    );
                }
                
                if (x > 127 && x < 371 && y > 380 && y < 449 && !isPlaying) {
                    // Start button logic here
                	isPlaying = true;
                	panel.remove(background);
                	
                	ImageIcon gameImage = new ImageIcon("images/GameScreen.png");
                	
                	background = new JLabel(gameImage);
                	
                	panel.add(background);
                	
                	
                	frame.revalidate();
                	frame.repaint();
                	
                	
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    public void show() {
        frame.revalidate();
        frame.repaint();
    }
}
