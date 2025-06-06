package src;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.util.HashMap;
import java.util.Map;

public class GamePlay {
    //instance variables
    public JFrame frame;
    public JPanel panel;
    private JLabel background;
    private ImageIcon chipotleBackground;
    private boolean isPlaying;
    private Map<String, JLabel> checkmarks;
    private Random random;
    private int hearts;
    private JLabel[] heartLabels;

    private Map<String, String> ingredientCategories;
    private Map<String, Integer> categoryLimits;
    private Map<String, Integer> categoryCounts;
    //default constructor
    GamePlay() {
        //setting up game variables and frame 
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

        // Setting up ingredient categories and limits
        ingredientCategories = new HashMap<>();
        categoryLimits = new HashMap<>();
        categoryCounts = new HashMap<>();
        ingredientCategories.put("No Rice", "Rice");
        ingredientCategories.put("Black Beans", "Beans");
        ingredientCategories.put("Pinto Beans", "Beans");
        ingredientCategories.put("Mild", "Salsa");
        ingredientCategories.put("Spicy1", "Salsa");
        ingredientCategories.put("Spicy2", "Salsa");
        ingredientCategories.put("Beef Barbocoa", "Meat");
        ingredientCategories.put("Chicken", "Meat");
        ingredientCategories.put("Sofritas", "Meat");
        ingredientCategories.put("Carnitas", "Meat");
        categoryLimits.put("Rice", 1);
        categoryLimits.put("Beans", 1);
        categoryLimits.put("Salsa", 1);
        categoryLimits.put("Meat", 2);
        categoryCounts.put("Rice", 0);
        categoryCounts.put("Beans", 0);
        categoryCounts.put("Salsa", 0);
        categoryCounts.put("Meat", 0);
        //Mouse Listener for user input
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //Getting usser input
                int x = e.getX();
                int y = e.getY();
                //System.out.println("X: " + x + " Y: " + y);
                //Help Button
                if (x > 146 && x < 340 && y > 491 && y < 591 && !isPlaying) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "To win, you must guess the correct combination of ingredients for a Chipotle meal!\n" +
                                    "You only get 3 tries so make the most of it. Winners receive a BOGO chipotle code!");
                }
                //Play Button
                if (x > 127 && x < 371 && y > 380 && y < 449 && !isPlaying) {
                    isPlaying = true;
                    random = new Random();
                    panel.removeAll();
                    checkmarks.clear();
                    // Resetting hearts and category counts
                    for (String k : categoryCounts.keySet()) {
                        categoryCounts.put(k, 0);
                    }
                    //Adding Game Screen to JFrame
                    ImageIcon gameImage = new ImageIcon("images/Game Screen.png");
                    background.setIcon(gameImage);
                    background.setBounds(0, 0, 500, 780);
                    panel.add(background);
                    //Adding White Hearts
                    ImageIcon heartIcon = new ImageIcon("images/WhiteHeart.png");
                    for (int i = 0; i < hearts; i++) {
                        JLabel heart = new JLabel(heartIcon);
                        heart.setBounds(50 + (i * 125), 350, heartIcon.getIconWidth(), heartIcon.getIconHeight());
                        heartLabels[i] = heart;
                        panel.add(heart);
                    }
                    //Ensure background is on the bottom
                    panel.setComponentZOrder(background, panel.getComponentCount() - 1);
                    //Ensuring frame/panel is updated
                    panel.revalidate();
                    panel.repaint();
                    frame.revalidate();
                    frame.repaint();
                }

                if (isPlaying) {
                    if (x > 400 && x < 480 && y > 700 && y < 750) {
                        String[] correct = random.getCorrectIngredients();
                        boolean hasIncorrect = false;
                        // Check if the user has selected 5 ingredients
                        if (checkmarks.size() == 5) {
                            boolean allCorrect = true;
                            //Check if ingredients match
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
                                //If user has won, generate a code
                                String code = "";
                                int length = 5;
                                // Generate a random 5-letter code
                                for (int i = 0; i < length; i++) {
                                    int rand = (int) (Math.random() * 26);
                                    char letter = (char) ('A' + rand);
                                    code += letter;
                                }
                                //Text and formatting for random generated code
                                JLabel codeLabel = new JLabel(code, SwingConstants.CENTER);
                                codeLabel.setFont(new Font("Arial", Font.BOLD, 40));
                                codeLabel.setForeground(Color.WHITE);
                                codeLabel.setBounds(0, 500, 500, 100);

                                panel.removeAll();
                                ImageIcon winImage = new ImageIcon("images/WinScreen.png");
                                background.setIcon(winImage);
                                background.setBounds(0, 0, 500, 780);
                                panel.add(codeLabel);
                                panel.add(background);
                                isPlaying = false;
                                panel.revalidate();
                                panel.repaint();
                                frame.revalidate();
                                frame.repaint();
                                //Win message
                                JOptionPane.showMessageDialog(
                                        frame,
                                        "Congratulations! The corrent ingredients were: " +
                                                String.join(", ", correct));
                                return;
                            }
                        }
                        // If the user has not selected 5 ingredients, check for incorrect selections
                        for (String ingredient : new HashMap<>(checkmarks).keySet()) {
                            boolean isCorrect = false;
                            for (String rightIngredient : correct) {
                                if (ingredient.equals(rightIngredient)) {
                                    isCorrect = true;
                                    break;
                                }
                            }
                            // If the ingredient is incorrect, remove the checkmark and update hearts
                            if (!isCorrect) {
                                panel.remove(checkmarks.get(ingredient));
                                checkmarks.remove(ingredient);
                                String category = ingredientCategories.getOrDefault(ingredient, "Other");
                                if (categoryCounts.containsKey(category)) {
                                    categoryCounts.put(category, categoryCounts.get(category) - 1);
                                }
                                hasIncorrect = true;
                            }
                        }
                        // Update hearts based on incorrect selections
                        if (hasIncorrect && hearts > 0) {
                            hearts--;
                            panel.remove(heartLabels[hearts]);
                            heartLabels[hearts] = null;
                        }
                        //If there are no hearts, game is over
                        if (hearts == 0) {
                            //Show GameOver screen
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
                                            String.join(", ", correct));
                        } else {
                            panel.revalidate();
                            panel.repaint();
                            frame.revalidate();
                            frame.repaint();
                        }
                    }

                    // Ingredient selections
                    if (x > 434 && x < 484 && y > 534 && y < 561) addCheckmark(x, y, "No Rice");
                    if (x > 371 && x < 424 && y > 534 && y < 557) addCheckmark(x, y, "Black Beans");
                    if (x > 300 && x < 356 && y > 530 && y < 559) addCheckmark(x, y, "Pinto Beans");
                    if (x > 371 && x < 424 && y > 564 && y < 594) addCheckmark(x, y, "Veggies");
                    if (x > 301 && x < 356 && y > 563 && y < 592) addCheckmark(x, y, "Beef Barbocoa");
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
    // Method to add or remove checkmarks based on user input
    private void addCheckmark(int x, int y, String ingredient) {
        String category = ingredientCategories.getOrDefault(ingredient, "Other");
        //Check if ingreident is already selected
        if (checkmarks.containsKey(ingredient)) {
            //If it is, remove the checkmark
            panel.remove(checkmarks.get(ingredient));
            checkmarks.remove(ingredient);
            if (categoryCounts.containsKey(category)) {
                categoryCounts.put(category, categoryCounts.get(category) - 1);
            }
            panel.revalidate();
            panel.repaint();
            frame.revalidate();
            frame.repaint();
            System.out.println("Checkmark removed for " + ingredient);
            return;
        }
        //If it not seleceted, check if it exceeds the  limit
        if (categoryCounts.containsKey(category)) {
            int currentCount = categoryCounts.get(category);
            int maxCount = categoryLimits.getOrDefault(category, Integer.MAX_VALUE);
            //If it does, show a message and return
            if (currentCount >= maxCount) {
                JOptionPane.showMessageDialog(frame, "You can only choose " + maxCount + " from the " + category + " category.");
                return;
            }
        }
        //Check mark icom
        ImageIcon checkmarkIcon = new ImageIcon("images/Chec.png");
        JLabel checkmark = new JLabel(checkmarkIcon);
        int width = checkmarkIcon.getIconWidth();
        int height = checkmarkIcon.getIconHeight();
        //Adjust position of check mark based of user input
        int adjX = Math.max(0, Math.min(x - width / 2, 500 - width));
        int adjY = Math.max(0, Math.min(y - height / 2, 780 - height));
        checkmark.setBounds(adjX, adjY, width, height);
        panel.add(checkmark);
        panel.setComponentZOrder(checkmark, 0);
        checkmarks.put(ingredient, checkmark);

        if (categoryCounts.containsKey(category)) {
            categoryCounts.put(category, categoryCounts.get(category) + 1);
        }

        panel.revalidate();
        panel.repaint();
        frame.revalidate();
        frame.repaint();
    }

    public void show() {
        frame.revalidate();
        frame.repaint();
    }
}
