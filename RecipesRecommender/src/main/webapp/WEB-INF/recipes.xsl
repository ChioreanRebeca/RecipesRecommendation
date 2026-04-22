<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" indent="yes" omit-xml-declaration="yes"/>

    <xsl:param name="activeUserSkill"/>
    <xsl:param name="activeUserName"/>

    <xsl:template match="/">
        <div class="container" style="max-width: 800px; margin: 20px auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
            <h2 style="text-align: center; color: #FF8BA7;">Available Recipes</h2>

            <p style="text-align:center; font-size:16px;"><strong><xsl:value-of select="$activeUserName"/>'s Skill Level:</strong> <xsl:value-of select="$activeUserSkill"/></p>

            <table style="width: 100%; border-collapse: collapse; margin-top: 20px;">
                <tr>
                    <th style="background-color: #FF8BA7; color: white; padding: 12px; border: 1px solid #ddd;">Recipe Title</th>
                    <th style="background-color: #FF8BA7; color: white; padding: 12px; border: 1px solid #ddd;">Cuisine Type</th>
                    <th style="background-color: #FF8BA7; color: white; padding: 12px; border: 1px solid #ddd;">Difficulty</th>
                    <th style="background-color: #FF8BA7; color: white; padding: 12px; border: 1px solid #ddd;">Details</th>
                </tr>

                <xsl:for-each select="Data/Recipes/Recipe">
                    <xsl:variable name="rowColor">
                        <xsl:choose>
                            <xsl:when test="Difficulty = $activeUserSkill">#FAFFCB</xsl:when>
                            <xsl:otherwise>#C3F0CA</xsl:otherwise>
                        </xsl:choose>
                    </xsl:variable>

                    <tr style="background-color: {$rowColor};">
                        <td style="padding: 12px; border: 1px solid #ddd;"><xsl:value-of select="Title"/></td>
                        <td style="padding: 12px; border: 1px solid #ddd;"><xsl:value-of select="CuisineType"/></td>
                        <td style="padding: 12px; border: 1px solid #ddd;"><xsl:value-of select="Difficulty"/></td>
                        <td style="padding: 12px; border: 1px solid #ddd; text-align:center;">
                            <a href="recipeDetails?title={Title}" style="color:#FF8BA7; font-weight:bold; text-decoration:none;">View Details</a>
                        </td>
                    </tr>
                </xsl:for-each>
            </table>

            <p style="margin-top:20px; font-size: 14px;">* Recipes matching the user's skill level have a yellow background.</p>
        </div>
    </xsl:template>
</xsl:stylesheet>