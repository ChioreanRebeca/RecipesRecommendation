
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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

@WebServlet("/recommendRecipe")
public class RecomendationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String filterType = request.getParameter("filterType");
        String specificCuisine = request.getParameter("specificCuisine");
        String currentUser = (String) request.getSession().getAttribute("currentUser");


        out.println("<html><head><title>Recipe Recommendations</title>");

        //css part
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; padding: 20px; background-color: #f4f7f6; color: #333; }");
        out.println(".card { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 5px 15px rgba(0,0,0,0.05); max-width: 900px; margin: auto; }");
        out.println(".dashboard { display: flex; justify-content: space-between; gap: 20px; margin-bottom: 30px; }");
        out.println(".action-box { flex: 1; background: #e8f5e9; padding: 20px; border-radius: 8px; text-align: center; border: 1px solid #c8e6c9; }");
        out.println(".btn { display: inline-block; background: #4CAF50; color: white; padding: 10px 15px; text-decoration: none; border-radius: 5px; font-weight: bold; border: none; cursor: pointer; margin-top: 10px; }");
        out.println(".btn:hover { background: #45a049; }");
        out.println("table { border-collapse: collapse; width: 100%; margin-top: 20px; }");
        out.println("th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }");
        out.println("th { background-color: #4CAF50; color: white; }");
        out.println("</style></head><body>");

        request.getRequestDispatcher("/navbar.jsp").include(request, response);
        out.println("<div class='card'>");
        out.println("<h2 style='text-align:center; color: #2e7d32; margin-bottom: 30px;'>Recommendation Dashboard</h2>");


        out.println("<div class='dashboard'>");

        out.println("<div class='action-box'>");
        out.println("<h3>Quick User Match</h3>");
        out.println("<p style='font-size:14px; color:#555;'>Recommend based  on <b> user cooking skill</b>.</p>");
        out.println("<a href='recommendRecipe?filterType=req6' class='btn'>Match Skill</a>");
        out.println("</div>");


        out.println("<div class='action-box'>");
        out.println("<h3>Perfect User Match</h3>");
        out.println("<p style='font-size:14px; color:#555;'>Recommend based on <b> user skill and user favorite cuisine</b>.</p>");
        out.println("<a href='recommendRecipe?filterType=req7' class='btn'>Match Both</a>");
        out.println("</div>");


        out.println("<div class='action-box'>");
        out.println("<h3>Cuisine Search</h3>");
        out.println("<p style='font-size:14px; color:#555;'>Ignore user profile and search for a specific cuisine.</p>");
        out.println("<form action='recommendRecipe' method='GET' style='margin-top:10px;'>");
        out.println("<input type='hidden' name='filterType' value='req10'>");
        out.println("<select name='specificCuisine' style='padding:8px; border-radius:4px; border:1px solid #ccc;'>");
        out.println("<option value='Italian'>Italian</option>");
        out.println("<option value='Asian'>Asian</option>");
        out.println("</select>");
        out.println("<br><button type='submit' class='btn'>Search</button>");
        out.println("</form>");
        out.println("</div>");

        out.println("</div>"); //end of dashboard
        out.println("<hr style='border: 0; border-top: 1px solid #eee; margin: 30px 0;'>");


        if (filterType != null) {
            try {
                String filePath = getServletContext().getRealPath("/WEB-INF/recipes_data.xml");
                File xmlFile = new File(filePath);

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();

                XPathFactory xPathfactory = XPathFactory.newInstance();
                XPath xpath = xPathfactory.newXPath();

                String recipeExpr = "";
                String resultMessage = "";

                String userXPath = "/Data/User[1]"; //fallback

                if (currentUser != null && !currentUser.isEmpty()) {
                    userXPath = "/Data/User[concat(FirstName, ' ', LastName)='" + currentUser + "']";
                }

                if ("req6".equals(filterType)) {
                    String userSkill = (String) xpath.evaluate(userXPath + "/SkillLevel/text()", doc, XPathConstants.STRING);
                    recipeExpr = "/Data/Recipes/Recipe[Difficulty='" + userSkill + "']";
                    resultMessage = "Showing recipes matching your Skill Level: <strong>" + userSkill + "</strong>";

                } else if ("req7".equals(filterType)) {
                    String userSkill = (String) xpath.evaluate(userXPath + "/SkillLevel/text()", doc, XPathConstants.STRING);
                    String userCuisine = (String) xpath.evaluate(userXPath + "/PreferredCuisine/text()", doc, XPathConstants.STRING);
                    recipeExpr = "/Data/Recipes/Recipe[Difficulty='" + userSkill + "' and CuisineType='" + userCuisine + "']";
                    resultMessage = "Showing perfect matches for Skill (<strong>" + userSkill + "</strong>) AND Cuisine (<strong>" + userCuisine + "</strong>)";

                } else if ("req10".equals(filterType)) {
                    recipeExpr = "/Data/Recipes/Recipe[CuisineType='" + specificCuisine + "']";
                    resultMessage = "Showing all recipes for Cuisine: <strong>" + specificCuisine + "</strong>";
                }

                NodeList results = (NodeList) xpath.evaluate(recipeExpr, doc, XPathConstants.NODESET);

                out.println("<h3 style='color: #2e7d32;'>" + resultMessage + "</h3>");
                out.println("<table><tr><th>Recipe Title</th><th>Cuisine</th><th>Difficulty</th></tr>");

                if (results.getLength() > 0) {
                    for (int i = 0; i < results.getLength(); i++) {
                        Element recipe = (Element) results.item(i);
                        String title = recipe.getElementsByTagName("Title").item(0).getTextContent();
                        String cuisine = recipe.getElementsByTagName("CuisineType").item(0).getTextContent();
                        String difficulty = recipe.getElementsByTagName("Difficulty").item(0).getTextContent();

                        out.println("<tr><td>" + title + "</td><td>" + cuisine + "</td><td>" + difficulty + "</td></tr>");
                    }
                } else {
                    out.println("<tr><td colspan='3' style='text-align:center; color:#666;'>No recipes found for this filter.</td></tr>");
                }
                out.println("</table>");

            } catch (Exception e) {
                e.printStackTrace();
                out.println("<h3 style='color:red;'>Error executing XPath: " + e.getMessage() + "</h3>");
            }
        } else {
            out.println("<h3 style='text-align:center; color:#888;'>Select an option from the dashboard above to view recipes.</h3>");
        }

        out.println("<div style='text-align:center; margin-top:30px;'><a href='" + request.getContextPath() + "/recipes' style='color:#4CAF50; text-decoration:none; font-weight:bold;'>&larr; Back to Main Page</a></div>");
        out.println("</div></body></html>");
    }
}