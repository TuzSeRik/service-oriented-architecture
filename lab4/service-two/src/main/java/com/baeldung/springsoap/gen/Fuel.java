//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.03.11 at 12:20:52 PM MSK 
//


package com.baeldung.springsoap.gen;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fuel.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="fuel">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALCOHOL"/>
 *     &lt;enumeration value="NUCLEAR"/>
 *     &lt;enumeration value="MANPOWER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "fuel")
@XmlEnum
public enum Fuel {

    ALCOHOL,
    NUCLEAR,
    MANPOWER;

    public String value() {
        return name();
    }

    public static Fuel fromValue(String v) {
        return valueOf(v);
    }

}
