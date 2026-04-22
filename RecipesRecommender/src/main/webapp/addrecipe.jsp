<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add New Recipe</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #FAEEE7; margin: 0; padding: 0; }
        .form-container { max-width: 400px; margin: 40px auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        input[type="text"], select { width: 100%; padding: 10px; margin: 8px 0 15px 0; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        input[type="submit"] { width: 100%; background-color: #FF8BA7; color: white; padding: 10px 15px; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        input[type="submit"]:hover { background-color: #FFC6C7; color: #333; }
        .error { color: red; font-size: 14px; text-align: center; margin-bottom: 10px; }
    </style>
</head>
<body>

<jsp:include page="navbar-simple.jsp" />

<div class="form-container">
    <h2 style="text-align: center; color: #FF8BA7;">Add New Recipe</h2>

    <%-- Display validation error message if the Servlet sends one back --%>
    <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="error"><%= request.getAttribute("errorMessage") %></div>
    <% } %>

    <form action="addRecipe" method="POST">
        <label>Recipe Title:</label>
        <input type="text" name="recipeTitle" required>

        <label>Cuisine Type:</label>
        <select name="cuisineType">
            <option value="Italian">Italian</option>
            <option value="Asian">Asian</option>
        </select>

        <label>Difficulty Level:</label>
        <select name="difficulty">
            <option value="Beginner">Beginner</option>
            <option value="Intermediate">Intermediate</option>
            <option value="Advanced">Advanced</option>
        </select>

        <input type="submit" value="Save Recipe to XML">
    </form>
</div>

</body>
</html>