package com.app;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;

@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String skillLevel = request.getParameter("skillLevel");
        String preferredCuisine = request.getParameter("preferredCuisine");

        try {

            String filePath = getServletContext().getRealPath("/WEB-INF/recipes_data.xml");
            File xmlFile = new File(filePath);


            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();


            Element newUser = doc.createElement("User");

            Element fNameNode = doc.createElement("FirstName");
            fNameNode.setTextContent(firstName);
            newUser.appendChild(fNameNode);

            Element lNameNode = doc.createElement("LastName");
            lNameNode.setTextContent(lastName);
            newUser.appendChild(lNameNode);

            Element skillNode = doc.createElement("SkillLevel");
            skillNode.setTextContent(skillLevel);
            newUser.appendChild(skillNode);

            Element cuisineNode = doc.createElement("PreferredCuisine");
            cuisineNode.setTextContent(preferredCuisine);
            newUser.appendChild(cuisineNode);

            Element root = doc.getDocumentElement();
            root.insertBefore(newUser, doc.getElementsByTagName("Recipes").item(0));


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred while saving the user: " + e.getMessage());
        }
    }
}