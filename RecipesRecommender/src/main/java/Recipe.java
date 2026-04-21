public class Recipe {
    private String title;
    private String cuisine;
    private String difficulty;

    public Recipe(String title, String cuisine, String difficulty) {
        this.title = title;
        this.cuisine = cuisine;
        this.difficulty = difficulty;
    }


    // getters and setters for all the parameters of the xml recipe items
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

}
