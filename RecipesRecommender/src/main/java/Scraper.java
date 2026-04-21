import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.util.*;

public class Scraper {
    public static void main(String[] args) {
        String url = "https://www.bbcgoodfood.com/recipes/collection/budget-autumn";
        //Each recipe has a title, 2 cuisine types (e.g., "Italian, Asian"), and 3 difficulty
        //levels (e.g., Beginner, Intermediate, Advanced)
        // here I am a bit confused but what I understand is that
        // the recipe cuisine can be one of two options: Asian or Italian
        // and the difficulty can be one of
        // three options: Beginner, Intermediate and Advanced
        String[] cuisines = {"Italian", "Asian"};
        String[] difficulties = {"Beginner", "Intermediate", "Advanced"};

        // the cuisine types and difficulty levels will be added by you randomly
        Random random = new Random();

        try {
            Document doc = Jsoup.connect(url).get();

            // I found this while inspecting the website
            // <h2 class="heading-4" style="color: inherit;">Creamy pumpkin pasta</h2>
            // "h2" element of class "heading-4"
            Elements titles = doc.select("h2.heading-4");

            StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Data>\n");

            // Create 1 user: Each user has a name, surname, 1
            //cooking skill level, and 1 preferred cuisine type.
            //!! I still don t know if I added the user correctly or if I should create a new
            //!! xml file just for the users
            xml.append("  <User>\n")
                    .append("    <FirstName>John</FirstName>\n")
                    .append("    <LastName>Doe</LastName>\n")
                    .append("    <SkillLevel>Intermediate</SkillLevel>\n")
                    .append("    <PreferredCuisine>Italian</PreferredCuisine>\n")
                    .append("  </User>\n")
                    .append("  <Recipes>\n");


            int count = 0;
            for (Element title : titles) {
                if (count >= 20) break;

                String recipeTitle = title.text();

                // cleaning noisy text
                // removing the: "[...]this is a premium ..."   and "App only [...]" also removing the  "&" sign from recipes
                // <Title>App onlyButternut squash white bean tagliatelle. This is a premium piece of content available to subscribed users.</Title>
                // <Title>App onlyCreamy beans. This is a premium piece of content available to subscribed users.</Title>

                if (recipeTitle.startsWith("App only")) {
                    recipeTitle = recipeTitle.replace("App only", "");
                }
                if (recipeTitle.contains("This is a premium")) {
                    recipeTitle = recipeTitle.split(". This is a premium")[0];
                }
                // replace "&" with the word "and" since & is a reserved character for xml
                if(recipeTitle.contains("&")) {
                    recipeTitle = recipeTitle.replace("&", "and");
                }

                String diff = difficulties[random.nextInt(difficulties.length)];
                String cuisine = cuisines[random.nextInt(cuisines.length)];

                xml.append("    <Recipe>\n")
                        .append("      <Title>").append(recipeTitle).append("</Title>\n")
                        .append("      <CuisineType>").append(cuisine).append("</CuisineType>\n")
                        .append("      <Difficulty>").append(diff).append("</Difficulty>\n")
                        .append("    </Recipe>\n");
                count++;
            }

            xml.append("  </Recipes>\n</Data>");

            //save to xml
            FileWriter writer = new FileWriter("src/main/webapp/WEB-INF/recipes_data.xml");
            writer.write(xml.toString());
            writer.close();

            System.out.println("XML file generated successfully with " + count + " recipes!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}