<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.w3c.dom.*" %>
<%@ page import="javax.xml.parsers.*" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Enumeration" %>

<%
    String paramUser = request.getParameter("currentUser");
    if (paramUser != null && !paramUser.trim().isEmpty()) {
        session.setAttribute("currentUser", paramUser);
    }

    String activeUser = (String) session.getAttribute("currentUser");
%>

<style>
    /* Global Navbar Styling */
    .global-nav {
        background-color: #2e7d32; /* Deep Green */
        color: white;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 15px 30px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        margin-bottom: 20px;
        font-family: Arial, sans-serif;
    }

    .nav-links {
        display: flex;
        gap: 20px;
    }

    .nav-links a {
        color: white;
        text-decoration: none;
        font-weight: bold;
        font-size: 16px;
        padding: 8px 12px;
        border-radius: 4px;
        transition: background-color 0.3s;
    }

    .nav-links a:hover {
        background-color: #4CAF50;
    }

    .user-select-container {
        display: flex;
        align-items: center;
        gap: 10px;
        font-weight: bold;
    }

    .user-select-container select {
        padding: 6px 10px;
        border-radius: 4px;
        border: none;
        font-size: 14px;
        cursor: pointer;
    }
</style>

<div class="global-nav">
    <div class="nav-links">
        <a href="recipes">Home / All Recipes</a>
        <a href="recommendRecipe">Recommendation Dashboard</a>
        <a href="adduser.jsp">Add New User</a>
    </div>

    <div class="user-select-container">
        <label for="globalUserSelect">Current User:</label>

        <form method="GET" action="" style="margin: 0;">
            <%

                Enumeration<String> paramNames = request.getParameterNames();
                while(paramNames.hasMoreElements()){
                    String pName = paramNames.nextElement();
                    if(!pName.equals("currentUser")){
                        out.print("<input type='hidden' name='" + pName + "' value='" + request.getParameter(pName) + "'>");
                    }
                }
            %>
            <select id="globalUserSelect" name="currentUser" onchange="this.form.submit();">
                <%
                    try {
                        String filePath = application.getRealPath("/WEB-INF/recipes_data.xml");
                        File xmlFile = new File(filePath);

                        if (xmlFile.exists()) {
                            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                            Document doc = dBuilder.parse(xmlFile);
                            doc.getDocumentElement().normalize();

                            NodeList userNodes = doc.getElementsByTagName("User");

                            for (int i = 0; i < userNodes.getLength(); i++) {
                                Node nNode = userNodes.item(i);
                                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element eElement = (Element) nNode;

                                    String firstName = eElement.getElementsByTagName("FirstName").item(0).getTextContent();
                                    String lastName = eElement.getElementsByTagName("LastName").item(0).getTextContent();
                                    String fullName = firstName + " " + lastName;


                                    if (activeUser == null && i == 0) {
                                        activeUser = fullName;
                                        session.setAttribute("currentUser", activeUser);
                                    }


                                    String selectedStr = fullName.equals(activeUser) ? "selected" : "";
                                    out.print("<option value='" + fullName + "' " + selectedStr + ">" + fullName + "</option>");
                                }
                            }
                        }
                    } catch (Exception e) {
                        out.print("<option>Error loading users</option>");
                    }
                %>
            </select>
        </form>
    </div>
</div>