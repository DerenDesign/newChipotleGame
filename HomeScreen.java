import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class HomeScreen {

    public JFrame frame;
    public JPanel panel;
    private JLabel background;
    private ImageIcon chipotleBackground;
    private boolean isPlaying;
    private Map<String, JLabel> checkmarks;
    private int hearts;
    private JLabel[] heartLabels;
    private String[] correctIngredients;

    HomeScreen() {
        isPlaying = false;
        checkmarks = new HashMap<>();
        hearts = 3;
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
                    Random generator = new Random();
                    correctIngredients = generator.getCorrectIngredients();
                    panel.removeAll();
                    checkmarks.clear();
                    hearts = 3;
                    heartLabels = new JLabel[3];

                    ImageIcon gameImage = new ImageIcon("images/Game Screen.png");
                    background.setIcon(gameImage);
                    background.setBounds(0, 0, 500, 780);

                    ImageIcon heartIcon = new ImageIcon("images/WhiteHeart.png");
                    int[] xPositions = {45, 195, 345};

                    for (int i = 0; i < hearts; i++) {
                        JLabel heart = new JLabel(heartIcon);
                        heart.setBounds(xPositions[i], 350, heartIcon.getIconWidth(), heartIcon.getIconHeight());
                        heartLabels[i] = heart;
                        panel.add(heart);
                    }

                    panel.add(background);
                    panel.revalidate();
                    panel.repaint();
                    frame.revalidate();
                    frame.repaint();
                }

                if (isPlaying) {
                    if (x > 400 && x < 480 && y > 700 && y < 750) {
                        boolean hasIncorrect = false;

                        if (checkmarks.size() == 3) {
                            boolean allCorrect = true;
                            for (String ingredient : checkmarks.keySet()) {
                                boolean isCorrect = false;
                                for (String correctIngredient : correctIngredients) {
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
                                panel.removeAll();
                                background.setIcon(new ImageIcon("images/WinScreen.png"));
                                background.setBounds(0, 0, 500, 780);
                                panel.add(background);
                                isPlaying = false;
                                panel.revalidate();
                                panel.repaint();
                                frame.revalidate();
                                frame.repaint();
                                JOptionPane.showMessageDialog(
                                    frame,
                                    "Congratulations! You guessed the correct ingredients: " +
                                    String.join(", ", correctIngredients)
                                );
                                return;
                            }
                        }

                        ImageIcon xIcon = new ImageIcon("images/redXnew.png");
                        Map<String, JLabel> newCheckmarks = new HashMap<>();

                        for (Map.Entry<String, JLabel> entry : checkmarks.entrySet()) {
                            String ingredient = entry.getKey();
                            JLabel checkmarkLabel = entry.getValue();

                            boolean isCorrect = false;
                            for (String correctIngredient : correctIngredients) {
                                if (ingredient.equals(correctIngredient)) {
                                    isCorrect = true;
                                    break;
                                }
                            }

                            if (!isCorrect) {
                                int xPos = checkmarkLabel.getX();
                                int yPos = checkmarkLabel.getY();
                                panel.remove(checkmarkLabel);

                                JLabel xLabel = new JLabel(xIcon);
                                xLabel.setBounds(xPos, yPos, xIcon.getIconWidth(), xIcon.getIconHeight());
                                panel.add(xLabel);
                                panel.setComponentZOrder(xLabel, 0);
                                newCheckmarks.put(ingredient, xLabel);
                                hasIncorrect = true;
                            } else {
                                newCheckmarks.put(ingredient, checkmarkLabel);
                            }
                        }

                        checkmarks = newCheckmarks;

                        if (hasIncorrect && hearts > 0) {
                            hearts--;
                            panel.remove(heartLabels[hearts]);
                            heartLabels[hearts] = null;
                        }

                        if (hearts == 0) {
                            panel.removeAll();
                            background.setIcon(new ImageIcon("images/GameOver.png"));
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
                                String.join(", ", correctIngredients)
                            );
                        } else {
                            panel.revalidate();
                            panel.repaint();
                            frame.revalidate();
                            frame.repaint();
                        }
                    }

                    // Ingredient zones — corrected and verified names
                    if (x > 434 && x < 484 && y > 534 && y < 561) addCheckmark(x, y, "No Rice");
                    if (x > 371 && x < 424 && y > 534 && y < 557) addCheckmark(x, y, "Black Beans");
                    if (x > 300 && x < 356 && y > 530 && y < 559) addCheckmark(x, y, "Pinto Beans");
                    if (x > 371 && x < 424 && y > 564 && y < 594) addCheckmark(x, y, "Veggies");
                    if (x > 301 && x < 356 && y > 563 && y < 592) addCheckmark(x, y, "Beef Barbacoa");
                    if (x > 235 && x < 287 && y > 532 && y < 559) addCheckmark(x, y, "Chicken");
                    if (x > 235 && x < 286 && y > 565 && y < 592) addCheckmark(x, y, "Sofritas");
                    if (x > 234 && x < 290 && y > 596 && y < 624) addCheckmark(x, y, "Carnitas");
                    if (x > 175 && x < 203 && y > 536 && y < 590) addCheckmark(x, y, "Mild");
                    if (x > 142 && x < 169 && y > 565 && y < 587) addCheckmark(x, y, "Spicy1");
                    if (x > 143 && x < 171 && y > 536 && y < 560) addCheckmark(x, y, "Spicy2");
                    if (x > 111 && x < 139 && y > 560 && y < 588) addCheckmark(x, y, "Corn");
                    if (x > 106 && x < 140 && y > 538 && y < 561) addCheckmark(x, y, "Sour Cream");
                    if (x > 75 && x < 107 && y > 538 && y < 588) addCheckmark(x, y, "Cheese");
                    if (x > 47 && x < 74 && y > 535 && y < 588) addCheckmark(x, y, "Guac");
                    if (x > 16 && x < 44 && y > 536 && y < 589) addCheckmark(x, y, "Lettuce");
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void addCheckmark(int x, int y, String ingredient) {
        if (checkmarks.containsKey(ingredient)) {
            panel.remove(checkmarks.get(ingredient));
            checkmarks.remove(ingredient);
            panel.revalidate();
            panel.repaint();
            frame.revalidate();
            frame.repaint();
            return;
        }

        ImageIcon checkmarkIcon = new ImageIcon("images/Chec.png");
        JLabel checkmark = new JLabel(checkmarkIcon);
        int width = checkmarkIcon.getIconWidth();
        int height = checkmarkIcon.getIconHeight();
        int adjX = Math.max(0, Math.min(x - width / 2, 500 - width));
        int adjY = Math.max(0, Math.min(y - height / 2, 780 - height));
        checkmark.setBounds(adjX, adjY, width, height);
        panel.add(checkmark);
        panel.setComponentZOrder(checkmark, 0);
        checkmarks.put(ingredient, checkmark);
        panel.revalidate();
        panel.repaint();
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        new HomeScreen();
    }
}
