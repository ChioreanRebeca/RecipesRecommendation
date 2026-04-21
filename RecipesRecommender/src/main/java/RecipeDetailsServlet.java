package com.app;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/recipeDetails")
public class RecipeDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();


        String targetTitle = request.getParameter("title");

        out.println("<html><head><title>Recipe Details</title>");
        out.println("<style>body { font-family: Arial; padding: 20px; } .card { border: 1px solid #ddd; padding: 20px; max-width: 400px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); } h2 { color: #4CAF50; margin-top: 0; }</style>");
        out.println("</head><body>");

        if (targetTitle == null || targetTitle.isEmpty()) {
            out.println("<h3>Error: No recipe title provided in the URL.</h3>");
            out.println("<br><a href='" + request.getContextPath() + "/recipes'>Back to All Recipes</a></body></html>");
            return;
        }

        try {

            String filePath = getServletContext().getRealPath("/WEB-INF/recipes_data.xml");
            File xmlFile = new File(filePath);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();


            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();


            String recipeExpr = "/Data/Recipes/Recipe[Title='" + targetTitle + "']";


            Node recipeNode = (Node) xpath.evaluate(recipeExpr, doc, XPathConstants.NODE);


            if (recipeNode != null && recipeNode.getNodeType() == Node.ELEMENT_NODE) {
                Element recipe = (Element) recipeNode;
                String title = recipe.getElementsByTagName("Title").item(0).getTextContent();
                String cuisine = recipe.getElementsByTagName("CuisineType").item(0).getTextContent();
                String difficulty = recipe.getElementsByTagName("Difficulty").item(0).getTextContent();

                out.println("<div class='card'>");
                out.println("<h2>" + title + "</h2>");
                out.println("<p><strong>Cuisine Type:</strong> " + cuisine + "</p>");
                out.println("<p><strong>Difficulty Level:</strong> " + difficulty + "</p>");
                out.println("</div>");
            } else {
                out.println("<h3>Recipe not found!</h3>");
            }

            out.println("<br><a href='" + request.getContextPath() + "/recipes'>Back to All Recipes</a>");
            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error executing XPath: " + e.getMessage() + "</h3>");
        }
    }
}