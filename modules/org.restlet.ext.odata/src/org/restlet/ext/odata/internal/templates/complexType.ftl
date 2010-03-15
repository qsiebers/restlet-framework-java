/**
 * Copyright 2005-2010 Noelios Technologies.
 * 
 * The contents of this file are subject to the terms of one of the following
 * open source licenses: LGPL 3.0 or LGPL 2.1 or CDDL 1.0 or EPL 1.0 (the
 * "Licenses"). You can select the license that you prefer but you may not use
 * this file except in compliance with one of these Licenses.
 * 
 * You can obtain a copy of the LGPL 3.0 license at
 * http://www.opensource.org/licenses/lgpl-3.0.html
 * 
 * You can obtain a copy of the LGPL 2.1 license at
 * http://www.opensource.org/licenses/lgpl-2.1.php
 * 
 * You can obtain a copy of the CDDL 1.0 license at
 * http://www.opensource.org/licenses/cddl1.php
 * 
 * You can obtain a copy of the EPL 1.0 license at
 * http://www.opensource.org/licenses/eclipse-1.0.php
 * 
 * See the Licenses for the specific language governing permissions and
 * limitations under the Licenses.
 * 
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly at
 * http://www.noelios.com/products/restlet-engine
 * 
 * Restlet is a registered trademark of Noelios Technologies.
 */

package ${packageName};

<#compress>
<#list type.importedJavaClasses?sort as clazz>
import ${clazz};
</#list>

<#list type.importedTypes?sort as t>
import ${t.fullClassName};
</#list>
</#compress>


<#compress>
/**
 * Generated by the generator tool for the OData extension for the Restlet framework.<br>
 * 
<#if metadata.metadataRef??> * @see <a href="${metadata.metadataRef}">Metadata of the target OData service</a></#if>
 * 
 */
</#compress>

public <#if type.abstractType>abstract </#if>class ${className} {

<#list type.properties?sort_by("name") as property>
    private ${property.type.javaType} ${property.propertyName}<#if property.defaultValue??> = property.defaultValue</#if>;
</#list>

    /**
     * Constructor without parameter.
     * 
     */
    public ${className}() {
        super();
    }
    
<#list type.properties?sort_by("name") as property>
   /**
    * Returns the value of the "${property.propertyName}" attribute.
    *
    * @return The value of the "${property.propertyName}" attribute.
    */
   <#if property.getterAccess??>${property.getterAccess}<#else>public</#if> ${property.type.javaType} get${property.normalizedName?cap_first}() {
      return ${property.propertyName};
   }
   
</#list>
<#list type.properties?sort_by("name") as property>
   /**
    * Sets the value of the "${property.propertyName}" attribute.
    *
    * @param ${property.propertyName}
    *     The value of the "${property.normalizedName}" attribute.
    */
   <#if property.setterAccess??>${property.setterAccess}<#else>public</#if> void set${property.normalizedName?cap_first}(${property.type.javaType} ${property.propertyName}) {
      this.${property.propertyName} = ${property.propertyName};
   }
   
</#list>
}