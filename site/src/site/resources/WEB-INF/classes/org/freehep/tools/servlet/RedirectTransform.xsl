<?xml version="1.0" encoding="UTF-8" ?>

<!--
    Document   : RedirectTransform.xsl
    Created on : January 28, 2004, 4:51 PM
    Author     : tonyj
    Description:
        Transforms RedirectData to internal form.
-->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="redirect">
		<redirect>
			<xsl:apply-templates />
		</redirect>
	</xsl:template>
	<xsl:template match="project">
		<xsl:element name="{@name}">
			<xsl:apply-templates/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="area">
		<xsl:element name="{@name}">
			<xsl:value-of select="@href"/>
			<xsl:apply-templates/>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet> 
