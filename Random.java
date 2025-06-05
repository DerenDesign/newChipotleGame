public class Random {

    private String[] correctIngredients;
    private static final String[] ALL_INGREDIENTS = {
         "Black Beans", "Pinto Beans", "Veggies", "Beef Barbocoa",
        "Chicken", "Sofritas", "Carnitas", "Mild", "Spicy1", "Spicy2",
        "Corn", "Sour Cream", "Cheese", "Guac", "Lettuce"
    };

    public Random() {
		//int numIngredients = (int)(Math.random() * ALL_INGREDIENTS.length) + 1;
        correctIngredients = new String[5];
        boolean[] used = new boolean[ALL_INGREDIENTS.length]; // Track used indices
        int selected = 0;

        while (selected < 5) {
            int index = (int)(Math.random() * ALL_INGREDIENTS.length);
            if (!used[index]) {
                correctIngredients[selected] = ALL_INGREDIENTS[index];
                used[index] = true;
                selected++;
            }
        }

        // Debug output to verify selection
        System.out.println("Random ingredients: " + 
            correctIngredients[0] + ", " + 
            correctIngredients[1] + ", " + 
            correctIngredients[2] + "," + 
            correctIngredients[3] + ", " + 
            correctIngredients[4] 
             );
    }

    public String[] getCorrectIngredients() {
        return correctIngredients;
    }
}