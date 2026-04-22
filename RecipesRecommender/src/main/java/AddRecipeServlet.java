import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;

@WebServlet("/addRecipe")
public class AddRecipeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String title = request.getParameter("recipeTitle");
        String cuisine = request.getParameter("cuisineType");
        String difficulty = request.getParameter("difficulty");


        if (title == null || title.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Error: Recipe Title cannot be empty!");
            request.getRequestDispatcher("/addrecipe.jsp").forward(request, response);
            return;
        }

        try {

            String filePath = getServletContext().getRealPath("/WEB-INF/recipes_data.xml");
            File xmlFile = new File(filePath);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();


            Element newRecipe = doc.createElement("Recipe");

            Element titleNode = doc.createElement("Title");
            titleNode.setTextContent(title.trim());
            newRecipe.appendChild(titleNode);

            Element cuisineNode = doc.createElement("CuisineType");
            cuisineNode.setTextContent(cuisine);
            newRecipe.appendChild(cuisineNode);

            Element difficultyNode = doc.createElement("Difficulty");
            difficultyNode.setTextContent(difficulty);
            newRecipe.appendChild(difficultyNode);


            Node recipesContainer = doc.getElementsByTagName("Recipes").item(0);
            recipesContainer.appendChild(newRecipe);


            String xsdPath = getServletContext().getRealPath("/WEB-INF/schema.xsd");
            File xsdFile = new File(xsdPath);

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(xsdFile);
            Validator validator = schema.newValidator();

            try {

                validator.validate(new DOMSource(doc));

            } catch (SAXException e) {

                request.setAttribute("errorMessage", "XSD Validation Failed: The new data does not match the schema! Details: " + e.getMessage());
                request.getRequestDispatcher("/addrecipe.jsp").forward(request, response);
                return;
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);


            response.sendRedirect(request.getContextPath() + "/recipes");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "A system error occurred: " + e.getMessage());
            request.getRequestDispatcher("/addrecipe.jsp").forward(request, response);
        }
    }
}