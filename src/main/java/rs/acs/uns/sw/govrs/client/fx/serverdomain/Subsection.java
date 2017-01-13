//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.12 at 01:04:06 AM CET 
//


package rs.acs.uns.sw.govrs.client.fx.serverdomain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters.StringPropertyAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for pododjeljak element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="pododjeljak">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}clan" maxOccurs="unbounded"/>
 *         &lt;/sequence>
 *         &lt;attGroup ref="{http://www.parlament.gov.rs/schema/elementi}standard_attrs"/>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "clan"
})
@XmlRootElement(name = "pododjeljak", namespace = "http://www.parlament.gov.rs/schema/elementi")
public class Subsection extends Element {

    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/elementi", required = true)
    protected List<Article> clan;
    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String id;


    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(StringPropertyAdapter.class)
    protected StringProperty name = new SimpleStringProperty("Pododeljak");

    /**
     * Gets the value of the clan property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clan property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClan().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Article }
     * 
     * 
     */
    public List<Article> getClan() {
        if (clan == null) {
            clan = new ArrayList<Article>();
        }
        return this.clan;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name.getName();
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public void initElement() {
        // add all articles
        for (Element e:getClan()) {
            getChildren().add(e);
        }

        // init observable list for all children
        for (Element e: getChildren()) {
            e.setParent(this);
            e.initElement();
        }
    }


    @Override
    public void createAndAddChild(Element element) {
        if (element instanceof Article) {
            Article a = (Article) element;
            a.setParent(this);
            getClan().add(a);
            getChildren().add(a);
        }
    }

    @Override
    public void removeChild(Element element) {
        if (element instanceof Article) {
            Article a = (Article) element;
            getClan().remove(a);
            getChildren().remove(a);
        }
    }

    @Override
    public String createElementOpening() {
        return null;
    }

    @Override
    public String createElementAttrs() {
        return null;
    }

    @Override
    public String createElementContent() {
        return null;
    }

    @Override
    public String createElementClosing() {
        return null;
    }

}
