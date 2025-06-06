package src;
public class Random {

    private String[] correctIngredients;
    // All possible ingredients for the game
    private static final String[] ALL_INGREDIENTS = {
        "Black Beans", "Pinto Beans", "Veggies", "Beef Barbocoa",
        "Chicken", "Sofritas", "Carnitas", "Mild", "Tomatillo (Green)", "Tomatillo (Red)",
        "Corn", "Sour Cream", "Cheese", "Guac", "Lettuce"
    };

    public Random() {
        // Initialize the correct ingredients array with a size of 5
        correctIngredients = new String[5];
        boolean[] used = new boolean[ALL_INGREDIENTS.length];
        int selected = 0;
        // Counters for different ingredient types
        int beanCount = 0;
        int meatCount = 0;
        int salsaCount = 0;

        while (selected < 5) {
            int index = (int)(Math.random() * ALL_INGREDIENTS.length);
            // Check if the ingredient at the random index has already been used
            if (!used[index]) {
                String ingredient = ALL_INGREDIENTS[index];
                boolean valid = true;

                if (isBean(ingredient) && beanCount >= 1){
                    valid = false;
                }
                if (isMeat(ingredient) && meatCount >= 2){
                    valid = false;
                } 
                if (isSalsa(ingredient) && salsaCount >= 1){
                    valid = false;
                }
                // If the ingredient is valid, add it to the correct ingredients array
                if (valid) {
                    correctIngredients[selected] = ingredient;
                    used[index] = true;
                    selected++;

                    if (isBean(ingredient)){
                        beanCount++;
                    } 
                    if (isMeat(ingredient)){
                        meatCount++;
                    } 
                    if (isSalsa(ingredient)){
                        salsaCount++;
                    } salsaCount++;
                }
            }
        }

        
    }
    //Check if thee is a bean ingreidient
    private boolean isBean(String ingredient) {
        return ingredient.equals("Black Beans") || ingredient.equals("Pinto Beans");
    }
    //Check if there is a meat
    private boolean isMeat(String ingredient) {
        return ingredient.equals("Beef Barbocoa") || ingredient.equals("Chicken") ||
               ingredient.equals("Sofritas") || ingredient.equals("Carnitas");
    }
    //Check if theree is a salsa ingredient
    private boolean isSalsa(String ingredient) {
        return ingredient.equals("Mild") || ingredient.equals("Spicy1") || ingredient.equals("Spicy2");
    }
    //Get the array of correct ingredients
    public String[] getCorrectIngredients() {
        return correctIngredients;
    }
}
