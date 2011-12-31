<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
      <html>
        <head>
          <style>
              table {
                border-collapse: collapse;
              }
              td {
                background-color: cyan;
                border: 1px solid grey;
                color: white;
                padding: 0px;
                height: 11px;
                width: 11px;
              }
              .alive {
                background-color: black;
              }
          </style>
        </head>
        <body>
          <xsl:apply-templates/>
        </body>
      </html>
    </xsl:template>

    <xsl:template match="tr">
      <tr>
        <xsl:apply-templates select="td"/>
      </tr>
    </xsl:template>

    <xsl:template match="td">
      <xsl:variable name="pos" select="position()"/>
      <xsl:variable name="row" select="parent::tr"/>
      <xsl:variable name="prev-row" select="parent::tr/preceding-sibling::tr[1]"/>
      <xsl:variable name="next-row" select="parent::tr/following-sibling::tr[1]"/>
      <xsl:variable name="is-alive" select="@class = 'alive'"/>
      <td>
        <xsl:variable name="neighbour-count"
                      select="count((
                        $prev-row/td[(position() - $pos) > -2 and
                                (position() - $pos) &lt; 2] |
                        $row/td[(position() - $pos) > -2 and
                                position() != $pos and
                                (position() - $pos) &lt; 2] |
                        $next-row/td[(position() - $pos) > -2 and
                                (position() - $pos) &lt; 2]
                      )[@class='alive'])"/>
        <xsl:attribute name="class">
          <xsl:choose>
            <xsl:when test="$is-alive and $neighbour-count = 2 or
                            $neighbour-count = 3">alive</xsl:when>
          </xsl:choose>
        </xsl:attribute>
      </td>
    </xsl:template>

    <xsl:template match="node()|@*">
      <xsl:copy>
        <xsl:apply-templates select="node()|@*"/>
      </xsl:copy>
    </xsl:template>

</xsl:stylesheet>
