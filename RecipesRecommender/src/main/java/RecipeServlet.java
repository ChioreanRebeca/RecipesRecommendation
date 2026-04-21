import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/recipes")
public class RecipeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try {

            String xmlPath = getServletContext().getRealPath("/WEB-INF/recipes_data.xml");
            String xslPath = getServletContext().getRealPath("/WEB-INF/recipes.xsl");


            Source xmlSource = new StreamSource(new File(xmlPath));
            Source xslSource = new StreamSource(new File(xslPath));


            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(xslSource);


            transformer.transform(xmlSource, new StreamResult(response.getWriter()));

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error applying XSLT: " + e.getMessage());
        }
    }
}
