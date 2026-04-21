<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    /* Global Navbar Styling */
    .global-nav {
        background-color: #2e7d32;
        color: white;
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 15px 30px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        margin-bottom: 20px;
        font-family: Arial, sans-serif;
    }

    .nav-links { display: flex; gap: 20px; }
    .nav-links a {
        color: white;
        text-decoration: none;
        font-weight: bold;
        font-size: 16px;
        padding: 8px 12px;
        border-radius: 4px;
        transition: background-color 0.3s;
    }
    .nav-links a:hover { background-color: #4CAF50; }
</style>

<div class="global-nav">
    <div class="nav-links">
        <a href="recipes">Home / All Recipes</a>
        <a href="recommendRecipe">Recommendation Dashboard</a>
        <a href="adduser.jsp">Add New User</a>
    </div>
</div>