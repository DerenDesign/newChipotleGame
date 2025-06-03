public class Random {

    private String[] correctIngredients;
    public static final String[] ALL_INGREDIENTS = {
        "No Rice", "Black Beans", "Pinto Beans", "Veggies", "Beef Barbacoa",
        "Chicken", "Sofritas", "Carnitas", "Mild", "Spicy1", "Spicy2",
        "Corn", "Sour Cream", "Cheese", "Guac", "Lettuce"
    };

    public Random() {
        correctIngredients = new String[3];
        boolean[] used = new boolean[ALL_INGREDIENTS.length];
        int selected = 0;

        while (selected < 3) {
            int index = (int)(Math.random() * ALL_INGREDIENTS.length);
            if (!used[index]) {
                correctIngredients[selected] = ALL_INGREDIENTS[index];
                used[index] = true;
                selected++;
            }
        }

        System.out.println("Random ingredients: " + 
            correctIngredients[0] + ", " + 
            correctIngredients[1] + ", " + 
            correctIngredients[2]);
    }

    public String[] getCorrectIngredients() {
        return correctIngredients;
    }
}
