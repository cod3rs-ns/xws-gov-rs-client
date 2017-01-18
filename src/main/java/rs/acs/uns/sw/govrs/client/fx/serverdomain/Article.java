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
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.StringPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters.StringPropertyAdapter;
import rs.acs.uns.sw.govrs.client.fx.util.StringCleaner;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * <p>Java class for clan element declaration.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;element name="clan">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}stav" maxOccurs="unbounded"/>
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
    "content"
})
@XmlRootElement(name = "clan", namespace = "http://www.parlament.gov.rs/schema/elementi")
public class Article extends Element{

    @XmlElementRef(name = "stav", namespace = "http://www.parlament.gov.rs/schema/elementi", type = Paragraph.class)
    @XmlMixed
    protected List<Object> content;

    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "anyURI")
    @XmlJavaTypeAdapter(StringPropertyAdapter.class)
    protected StringProperty id = new SimpleStringProperty();

    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(StringPropertyAdapter.class)
    protected StringProperty name = new SimpleStringProperty("Član");

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * {@link Paragraph }
     * 
     * 
     */
    public List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
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
        return id.get();
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
        this.id.set(value);
    }

    public StringProperty idProperty() {
        return id;
    }
    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getElementName() {
        return name.get();
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setElementName(String value) {
        this.name.set(value);
    }

    public StringProperty elementNameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        this.name.set(value);
    }

    @Override
    public void initElement() {
        // add all paragraphs
        for (Object o: getContent()) {
            if (o instanceof Paragraph) {
                Paragraph e = (Paragraph) o;
                getChildren().add(e);
            } else {
                if(!StringCleaner.checkIsEmpty(o.toString())){
                    StringWrapper se = new StringWrapper(o);
                    getChildren().add(se);
                }
            }}

        // init observable list for all children
        for (Element e: getChildren()) {
            e.setElementParent(this);
            e.initElement();
        }
        createPropertyAttrs();
    }

    @Override
    public void createAndAddChild(Element element) {
        if (element instanceof Paragraph) {
            element.setElementParent(this);
            element.createPropertyAttrs();
            getContent().add(element);
            getChildren().add(element);
        }

        if (element instanceof StringWrapper) {
            element.setElementParent(this);
            element.createPropertyAttrs();
            getContent().add(((StringWrapper) element).getWrappedObject());
            getChildren().add(element);
        }
    }

    @Override
    public void removeChild(Element element) {
        if (element instanceof Paragraph) {
            getContent().remove(element);
            getChildren().remove(element);
        } else if (element instanceof StringWrapper) {
            getChildren().remove(element);
            getContent().remove(((StringWrapper)element).getWrappedObject());
        } else {
            Logger.getLogger(getClass().getName()).log(Level.WARNING, "Invalid type of element to delete.");
        }
    }

    @Override
    public void createPropertyAttrs() {
        // create property list for context
        StringPropertyItem idPropertyItem = new StringPropertyItem(
                idProperty(),
                "Generalno",
                "ID ",
                "Jedinstveni identifikator",
                false);
        StringPropertyItem namePropertyItem = new StringPropertyItem(
                elementNameProperty(),
                "Generalno",
                "Naziv",
                "Naziv elementa",
                true);
        getPropertyItems().add(idPropertyItem);
        getPropertyItems().add(namePropertyItem);
    }

    @Override
    public void preMarshaller() {
        getContent().clear();
        for (Element child: getChildren()) {
            if(child instanceof StringWrapper) {
                // add TextOnly
                getContent().add(((StringWrapper) child).getWrappedObject());
            } else {
                getContent().add(child);
            }
            child.preMarshaller();
        }
    }
}
