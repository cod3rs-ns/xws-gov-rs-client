//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.12 at 01:04:06 AM CET 
//


package rs.acs.uns.sw.govrs.client.fx.serverdomain;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.ChapterEnumPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.editor.property_sheet.StringPropertyItem;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters.ChapterEnumPropertyAdapter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters.StringPropertyAdapter;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.enums.ChapterRoles;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for dio element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="dio">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element ref="{http://www.parlament.gov.rs/schema/elementi}glava" maxOccurs="unbounded" minOccurs="2"/>
 *         &lt;/sequence>
 *         &lt;attGroup ref="{http://www.parlament.gov.rs/schema/elementi}standard_attrs"/>
 *         &lt;attribute name="role">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="uvodni_dio"/>
 *               &lt;enumeration value="glavni_dio"/>
 *               &lt;enumeration value="zavrsni_dio"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/attribute>
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
    "glava"
})
@XmlRootElement(name = "dio", namespace = "http://www.parlament.gov.rs/schema/elementi")
public class Chapter extends Element {

    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/elementi", required = true)
    protected List<Part> glava;

    @XmlAttribute(name = "role")
    @XmlJavaTypeAdapter(ChapterEnumPropertyAdapter.class)
    protected ObjectProperty<ChapterRoles> role = new SimpleObjectProperty<>();

    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "anyURI")
    @XmlJavaTypeAdapter(StringPropertyAdapter.class)
    protected StringProperty id = new SimpleStringProperty();

    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(StringPropertyAdapter.class)
    protected StringProperty name = new SimpleStringProperty("Deo");

    /**
     * Gets the value of the glava property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the glava property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGlava().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Part }
     * 
     * 
     */
    public List<Part> getGlava() {
        if (glava == null) {
            glava = new ArrayList<Part>();
        }
        return this.glava;
    }

    /**
     * Gets the value of the role property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRole() {
        if (role.get() == ChapterRoles.UvodniDeo) {
            return "uvodni_dio";
        }

        if (role.get() == ChapterRoles.GlavniDeo) {
            return "glavni_dio";
        }

        if (role.get() == ChapterRoles.ZavrsniDeo) {
            return "zavrsni_dio";
        }
        return "";
    }

    /**
     * Sets the value of the role property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRole(String value) {
        if (value.equals("uvodni_dio")) {
            this.role.set(ChapterRoles.UvodniDeo);
        }
        if (value.equals("glavni_dio")) {
            this.role.set(ChapterRoles.GlavniDeo);
        }
        if (value.equals("zavrsni_dio")) {
            this.role.set(ChapterRoles.ZavrsniDeo);
        }
    }

    public ObjectProperty<ChapterRoles> roleProperty() {
        return role;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getId() {
        return id.get();
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is
     *              {@link String }
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
     * @return possible object is
     * {@link String }
     */
    public String getElementName() {
        return name.get();
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
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
        // add all parts
        for (Element e : getGlava()) {
            getChildren().add(e);
        }

        // init observable list for all children
        for (Element e: getChildren()) {
            e.setElementParent(this);
            e.initElement();
        }

        // create property list for context
        createPropertyAttrs();
    }

    @Override
    public void createAndAddChild(Element element) {
        if (element instanceof Part) {
            Part p = (Part) element;
            p.setElementParent(this);
            p.createPropertyAttrs();
            getGlava().add(p);
            getChildren().add(p);
        }
    }

    @Override
    public void removeChild(Element element) {
        if (element instanceof Part) {
            Part p = (Part) element;
            getGlava().remove(p);
            getChildren().remove(p);
        }
    }

    @Override
    public void createPropertyAttrs() {
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
        ChapterEnumPropertyItem chapterRolesPropertyItem = new ChapterEnumPropertyItem(
                roleProperty(),
                "Dodatno",
                "Vrsta",
                "Vrsta dela",
                true);
        getPropertyItems().add(idPropertyItem);
        getPropertyItems().add(namePropertyItem);
        getPropertyItems().add(chapterRolesPropertyItem);
    }

    @Override
    public void preMarshaller() {
        for (Element child: getChildren()) {
            child.preMarshaller();
        }
    }

}
