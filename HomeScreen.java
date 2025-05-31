import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.HashMap;
import java.util.Map;

public class HomeScreen {

    public JFrame frame;
    public JPanel panel;
    private JLabel background;
    private ImageIcon chipotleBackground;
    private boolean isPlaying;
    private Map<String, JLabel> checkmarks; 
    private Random random; 
    private int hearts; 
    private JLabel[] heartLabels; 

    HomeScreen() {
        isPlaying = false;
        checkmarks = new HashMap<>();
        hearts = 3; // 3 Hearts
        heartLabels = new JLabel[3]; 
        panel = new JPanel();
        panel.setLayout(null);
        frame = new JFrame("Chipotle Vault");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 780);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        chipotleBackground = new ImageIcon("images/HomePage.jpg");
        background = new JLabel(chipotleBackground);
        background.setBounds(0, 0, 500, 780);

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
                    isPlaying = true;
                    random = new Random(); 
                    panel.removeAll();
                    checkmarks.clear(); 
                    ImageIcon gameImage = new ImageIcon("images/Game Screen.png");
                    background.setIcon(gameImage);
                    background.setBounds(0, 0, 500, 780);
                    panel.add(background);

                    // Add hearts
                    ImageIcon heartIcon = new ImageIcon("images/WhiteHeart.png");
                    for (int i = 0; i < hearts; i++) {
                        JLabel heart = new JLabel(heartIcon);
                        heart.setBounds(50 + (i * 60), 50, heartIcon.getIconWidth(), heartIcon.getIconHeight());
                        heartLabels[i] = heart; 
                        panel.add(heart);
                    }

                    panel.revalidate();
                    panel.repaint();
                    frame.revalidate();
                    frame.repaint();
                }

                if (isPlaying) {
                    
                    if (x > 400 && x < 480 && y > 700 && y < 750) {
                        String[] correct = random.getCorrectIngredients();
                        boolean hasIncorrect = false;
                        // Check for win condition: exactly 3 checkmarks, all correct
                        if (checkmarks.size() ==correct.length) {
                            boolean allCorrect = true;
                            for (String ingredient : checkmarks.keySet()) {
                                boolean isCorrect = false;
                                for (String correctIngredient : correct) {
                                    if (ingredient.equals(correctIngredient)) {
                                        isCorrect = true;
                                        break;
                                    }
                                }
                                if (!isCorrect) {
                                    allCorrect = false;
                                    break;
                                }
                            }
                            if (allCorrect) {
                                // Win: show WinScreen.png
                                panel.removeAll();
                                ImageIcon winImage = new ImageIcon("images/WinScreen.png");
                                background.setIcon(winImage);
                                background.setBounds(0, 0, 500, 780);
                                panel.add(background);
                                isPlaying = false; // Stop further clicks
                                panel.revalidate();
                                panel.repaint();
                                frame.revalidate();
                                frame.repaint();
                                JOptionPane.showMessageDialog(
                                    frame,
                                    "Congratulations! You guessed the correct ingredients: " + 
                                    String.join(", ", correct)
                                );
                                System.out.println("Win condition met");
                                return;
                            }
                        }
                        // Process checkmarks and hearts
                        for (String ingredient : new HashMap<>(checkmarks).keySet()) {
                            boolean isCorrect = false;
                            for (String correctIngredient : correct) {
                                if (ingredient.equals(correctIngredient)) {
                                    isCorrect = true;
                                    break;
                                }
                            }
                            if (!isCorrect) {
                                panel.remove(checkmarks.get(ingredient));
                                checkmarks.remove(ingredient);
                                hasIncorrect = true;
                            }
                        }
                        
                        if (hasIncorrect && hearts > 0) {
                            hearts--;
                           
                            panel.remove(heartLabels[hearts]);
                            heartLabels[hearts] = null;
                        }
                        
                        if (hearts == 0) {
                            panel.removeAll();
                            ImageIcon gameOverImage = new ImageIcon("images/GameOver.png");
                            background.setIcon(gameOverImage);
                            background.setBounds(0, 0, 500, 780);
                            panel.add(background);
                            isPlaying = false;
                            panel.revalidate();
                            panel.repaint();
                            frame.revalidate();
                            frame.repaint();
                            JOptionPane.showMessageDialog(
                                frame,
                                "Game Over! The correct ingredients were: " + 
                                String.join(", ", correct)
                            );
                            System.out.println("Game over: no hearts remaining");
                        } else {
                            panel.revalidate();
                            panel.repaint();
                            frame.revalidate();
                            frame.repaint();
                            System.out.println("Checked ingredients. Hearts remaining: " + hearts);
                        }
                    }
                    // Ingredient selection areas
                    if (x > 442 && x < 496 && y > 578 && y < 606) {
                        System.out.println("No Rice");
                        addCheckmark(x, y, "No Rice");
                    }
                    if (x > 371 && x < 430 && y > 545 && y < 573) {
                        System.out.println("Black Beans");
                        addCheckmark(x, y, "Black Beans");
                    }
                    if (x > 308 && x < 362 && y > 547 && y < 573) {
                        System.out.println("Pinto Beans");
                        addCheckmark(x, y, "Pinto Beans");
                    }
                    if (x > 374 && x < 430 && y > 578 && y < 610) {
                        System.out.println("Veggies");
                        addCheckmark(x, y, "Veggies");
                    }
                    if (x > 308 && x < 362 && y > 579 && y < 609) {
                        System.out.println("Beef Barbocoa");
                        addCheckmark(x, y, "Beef Barbocoa");
                    }
                    if (x > 239 && x < 291 && y > 546 && y < 572) {
                        System.out.println("Chicken");
                        addCheckmark(x, y, "Chicken");
                    }
                    if (x > 239 && x < 293 && y > 582 && y < 604) {
                        System.out.println("Sofritas");
                        addCheckmark(x, y, "Sofritas");
                    }
                    if (x > 238 && x < 291 && y > 610 && y < 639) {
                        System.out.println("Carnitas");
                        addCheckmark(x, y, "Carnitas");
                    }
                    if (x > 180 && x < 210 && y > 551 && y < 605) {
                        System.out.println("Mild");
                        addCheckmark(x, y, "Mild");
                    }
                    if (x > 150 && x < 177 && y > 578 && y < 605) {
                        System.out.println("Spicy1");
                        addCheckmark(x, y, "Spicy1");
                    }
                    if (x > 147 && x < 177 && y > 550 && y < 574) {
                        System.out.println("Spicy2");
                        addCheckmark(x, y, "Spicy2");
                    }
                    if (x > 118 && x < 145 && y > 580 && y < 604) {
                        System.out.println("Corn");
                        addCheckmark(x, y, "Corn");
                    }
                    if (x > 116 && x < 145 && y > 551 && y < 576) {
                        System.out.println("Sour Cream");
                        addCheckmark(x, y, "Sour Cream");
                    }
                    if (x > 88 && x < 115 && y > 553 && y < 602) {
                        System.out.println("Cheese");
                        addCheckmark(x, y, "Cheese");
                    }
                    if (x > 54 && x < 82 && y > 550 && y < 603) {
                        System.out.println("Guac");
                        addCheckmark(x, y, "Guac");
                    }
                    if (x > 22 && x < 49 && y > 550 && y < 604) {
                        System.out.println("Lettuce");
                        addCheckmark(x, y, "Lettuce");
                    }
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void addCheckmark(int x, int y, String ingredient) {
      
        if (checkmarks.containsKey(ingredient)) {
            // Remove existing checkmark
            panel.remove(checkmarks.get(ingredient));
            checkmarks.remove(ingredient);
            panel.revalidate();
            panel.repaint();
            frame.revalidate();
            frame.repaint();
            System.out.println("Checkmark removed for " + ingredient);
            return;
        }

        // Add new checkmark
        ImageIcon checkmarkIcon = new ImageIcon("images/Chec.png");
        // if (checkmarkIcon.getIconWidth() == -1) {
        //     System.out.println("Error: Checker.png not found or failed to load");
        //     return;
        // }
        JLabel checkmark = new JLabel(checkmarkIcon);
        int width = checkmarkIcon.getIconWidth();
        int height = checkmarkIcon.getIconHeight();
        int adjX = Math.max(0, Math.min(x - width / 2, 500 - width));
        int adjY = Math.max(0, Math.min(y - height / 2, 780 - height));
        checkmark.setBounds(adjX, adjY, width, height);
        panel.add(checkmark);
        panel.setComponentZOrder(checkmark, 0); // Ensure it's on top
        checkmarks.put(ingredient, checkmark); // Store checkmark
        panel.revalidate();
        panel.repaint();
        frame.revalidate();
        frame.repaint();
        // System.out.println("Checkmark added for " + ingredient + " at (" + adjX + ", " + adjY + ") with size (" + width + ", " + height + ")");
    }

    public void show() {
        frame.revalidate();
        frame.repaint();
    }
}