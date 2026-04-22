import org.w3c.dom.Document;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/recipes")
public class RecipeServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        response.setContentType("text/html;charset=UTF-8");
//
//        try {
//
//            String xmlPath = getServletContext().getRealPath("/WEB-INF/recipes_data.xml");
//            String xslPath = getServletContext().getRealPath("/WEB-INF/recipes.xsl");
//
//
//            Source xmlSource = new StreamSource(new File(xmlPath));
//            Source xslSource = new StreamSource(new File(xslPath));
//
//
//            TransformerFactory factory = TransformerFactory.newInstance();
//            Transformer transformer = factory.newTransformer(xslSource);
//
//
//            transformer.transform(xmlSource, new StreamResult(response.getWriter()));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.getWriter().println("Error applying XSLT: " + e.getMessage());
//        }
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String currentUser = (String) request.getSession().getAttribute("currentUser");

        try {
            String xmlPath = getServletContext().getRealPath("/WEB-INF/recipes_data.xml");
            String xslPath = getServletContext().getRealPath("/WEB-INF/recipes.xsl");
            File xmlFile = new File(xmlPath);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            String userXPath = "/Data/User[1]";
            if (currentUser != null && !currentUser.isEmpty()) {
                userXPath = "/Data/User[concat(FirstName, ' ', LastName)='" + currentUser + "']";
            }

            String activeUserSkill = (String) xpath.evaluate(userXPath + "/SkillLevel/text()", doc, XPathConstants.STRING);
            if (activeUserSkill == null || activeUserSkill.isEmpty()) {
                activeUserSkill = "Intermediate";
            }

            String activeUserName = currentUser != null ? currentUser : "John Doe";

            out.println("<html><head><title>All Recipes</title>");
            out.println("<style>body { font-family: Arial, sans-serif; background-color: #FAEEE7; margin: 0; padding: 0; }</style>");
            out.println("</head><body>");
            request.getRequestDispatcher("/navbar.jsp").include(request, response);

            Source xmlSource = new StreamSource(xmlFile);
            Source xslSource = new StreamSource(new File(xslPath));
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(xslSource);

            transformer.setParameter("activeUserSkill", activeUserSkill);
            transformer.setParameter("activeUserName", activeUserName);

            transformer.transform(xmlSource, new StreamResult(out));

            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error loading recipes: " + e.getMessage() + "</h3>");
        }
    }
}
