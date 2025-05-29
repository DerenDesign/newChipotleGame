import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomeScreen {
	
	private JFrame frame;
	private JPanel panel;
	private JLabel background;
	private ImageIcon chipotleBackground;
	
	
	
	HomeScreen(){
		panel = new JPanel();
		frame = new JFrame("Chipotle Vault");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 750);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
       
        
		
		chipotleBackground = new ImageIcon("H:\\git\\newChipotleGame\\images\\HomePage.jpg");
		background = new JLabel(chipotleBackground);
		
		
		panel.add(background);
		
		
		
		
		
		frame.add(panel);
		frame.setVisible(true);
		
	}
	
	public void show() {
		
		
		frame.revalidate();
		frame.repaint();
		
		
		
	}
	
	
	
	
}
