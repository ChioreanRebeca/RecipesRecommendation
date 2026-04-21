import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    public List<Recipe> loadRecipesFromXML(String filePath) {
        List<Recipe> recipeList = new ArrayList<>();
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Recipe");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String title = eElement.getElementsByTagName("Title").item(0).getTextContent();
                    String cuisine = eElement.getElementsByTagName("CuisineType").item(0).getTextContent();
                    String difficulty = eElement.getElementsByTagName("Difficulty").item(0).getTextContent();

                    recipeList.add(new Recipe(title, cuisine, difficulty));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipeList;
    }

}

