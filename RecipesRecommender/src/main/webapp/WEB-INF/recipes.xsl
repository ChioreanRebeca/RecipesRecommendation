<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" indent="yes" omit-xml-declaration="yes"/>

    <xsl:param name="activeUserSkill"/>
    <xsl:param name="activeUserName"/>

    <xsl:template match="/">
        <div class="container" style="max-width: 800px; margin: 20px auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
            <h2 style="text-align: center; color: #333;">Available Recipes</h2>

            <p style="text-align:center; font-size:16px;"><strong><xsl:value-of select="$activeUserName"/>'s Skill Level:</strong> <xsl:value-of select="$activeUserSkill"/></p>

            <table style="width: 100%; border-collapse: collapse; margin-top: 20px;">
                <tr>
                    <th style="background-color: #4CAF50; color: white; padding: 12px; border: 1px solid #ddd;">Recipe Title</th>
                    <th style="background-color: #4CAF50; color: white; padding: 12px; border: 1px solid #ddd;">Cuisine Type</th>
                    <th style="background-color: #4CAF50; color: white; padding: 12px; border: 1px solid #ddd;">Difficulty</th>
                    <th style="background-color: #4CAF50; color: white; padding: 12px; border: 1px solid #ddd;">Details</th>
                </tr>

                <xsl:for-each select="Data/Recipes/Recipe">
                    <xsl:variable name="rowColor">
                        <xsl:choose>
                            <xsl:when test="Difficulty = $activeUserSkill">#fffacd</xsl:when>
                            <xsl:otherwise>#e8f5e9</xsl:otherwise>
                        </xsl:choose>
                    </xsl:variable>

                    <tr style="background-color: {$rowColor};">
                        <td style="padding: 12px; border: 1px solid #ddd;"><xsl:value-of select="Title"/></td>
                        <td style="padding: 12px; border: 1px solid #ddd;"><xsl:value-of select="CuisineType"/></td>
                        <td style="padding: 12px; border: 1px solid #ddd;"><xsl:value-of select="Difficulty"/></td>
                        <td style="padding: 12px; border: 1px solid #ddd; text-align:center;">
                            <a href="recipeDetails?title={Title}" style="color:#2e7d32; font-weight:bold; text-decoration:none;">View Details</a>
                        </td>
                    </tr>
                </xsl:for-each>
            </table>

            <p style="margin-top:20px; font-size: 14px;">* recipes matching the user's skill level have a yellow background.</p>
        </div>
    </xsl:template>
</xsl:stylesheet>

<!--<?xml version="1.0" encoding="UTF-8"?>-->
<!--<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">-->
<!--    <xsl:output method="html" indent="yes"/>-->

<!--    <xsl:template match="/">-->
<!--        <html>-->
<!--            <head>-->
<!--                <title>Recipe Recommendations</title>-->
<!--                <style>-->
<!--                    body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }-->
<!--                    .container { max-width: 800px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }-->
<!--                    table { width: 100%; border-collapse: collapse; margin-top: 20px; }-->
<!--                    th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }-->
<!--                    th { background-color: #4CAF50; color: white; }-->
<!--                </style>-->
<!--            </head>-->
<!--            <body>-->
<!--                <div class="container">-->
<!--                    <h2 style="text-align: center; color: #333;">Available Recipes</h2>-->

<!--                    <xsl:variable name="userSkill" select="/Data/User/SkillLevel"/>-->
<!--                    <xsl:variable name="firstName" select="/Data/User/FirstName"/>-->
<!--                    <xsl:variable name="lastName" select="/Data/User/LastName"/>-->
<!--                    <xsl:variable name="fullName" select="concat($firstName, ' ', $lastName)"/>-->

<!--                    <p><strong><xsl:value-of select="$fullName"/> Skill Level:</strong> <xsl:value-of select="$userSkill"/></p>-->


<!--                    <table>-->
<!--                        <tr>-->
<!--                            <th>Recipe Title</th>-->
<!--                            <th>Cuisine Type</th>-->
<!--                            <th>Difficulty</th>-->
<!--                        </tr>-->

<!--                        <xsl:for-each select="Data/Recipes/Recipe">-->

<!--                            <xsl:variable name="rowColor">-->
<!--                                <xsl:choose>-->
<!--                                    <xsl:when test="Difficulty = $userSkill">#fffacd</xsl:when> <xsl:otherwise>#e8f5e9</xsl:otherwise> </xsl:choose>-->
<!--                            </xsl:variable>-->

<!--                            <tr style="background-color: {$rowColor};">-->
<!--                                <td><xsl:value-of select="Title"/></td>-->
<!--                                <td><xsl:value-of select="CuisineType"/></td>-->
<!--                                <td><xsl:value-of select="Difficulty"/></td>-->
<!--                            </tr>-->
<!--                        </xsl:for-each>-->

<!--                    </table>-->

<!--                    <p>* recipes that match the user's cooking skill have a yellow-->
<!--                        background, the others have a green background</p>-->
<!--                </div>-->
<!--            </body>-->
<!--        </html>-->
<!--    </xsl:template>-->
<!--</xsl:stylesheet>-->


