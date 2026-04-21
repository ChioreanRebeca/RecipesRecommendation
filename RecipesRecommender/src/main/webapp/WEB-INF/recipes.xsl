<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Recipe Recommendations</title>
                <style>
                    body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }
                    .container { max-width: 800px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
                    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
                    th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
                    th { background-color: #4CAF50; color: white; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2 style="text-align: center; color: #333;">Available Recipes</h2>

                    <xsl:variable name="userSkill" select="/Data/User/SkillLevel"/>
                    <xsl:variable name="firstName" select="/Data/User/FirstName"/>
                    <xsl:variable name="lastName" select="/Data/User/LastName"/>
                    <xsl:variable name="fullName" select="concat($firstName, ' ', $lastName)"/>

                    <p><strong><xsl:value-of select="$fullName"/> Skill Level:</strong> <xsl:value-of select="$userSkill"/></p>


                    <table>
                        <tr>
                            <th>Recipe Title</th>
                            <th>Cuisine Type</th>
                            <th>Difficulty</th>
                        </tr>

                        <xsl:for-each select="Data/Recipes/Recipe">

                            <xsl:variable name="rowColor">
                                <xsl:choose>
                                    <xsl:when test="Difficulty = $userSkill">#fffacd</xsl:when> <xsl:otherwise>#e8f5e9</xsl:otherwise> </xsl:choose>
                            </xsl:variable>

                            <tr style="background-color: {$rowColor};">
                                <td><xsl:value-of select="Title"/></td>
                                <td><xsl:value-of select="CuisineType"/></td>
                                <td><xsl:value-of select="Difficulty"/></td>
                            </tr>
                        </xsl:for-each>

                    </table>

                    <p>* recipes that match the user's cooking skill have a yellow
                        background, the others have a green background</p>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>