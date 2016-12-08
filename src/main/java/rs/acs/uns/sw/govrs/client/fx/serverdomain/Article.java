
package rs.acs.uns.sw.govrs.client.fx.serverdomain;

import javafx.beans.property.StringProperty;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.adapters.StringPropertyAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


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
    "stav"
})
@XmlRootElement(name = "clan", namespace = "http://www.parlament.gov.rs/schema/elementi")
public class Article extends Element{

    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/elementi", required = true)
    protected List<Paragraph> stav;
    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String id;

    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(StringPropertyAdapter.class)
    protected StringProperty name;

    /**
     * Gets the value of the stav property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stav property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStav().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Paragraph }
     * 
     * 
     */
    public List<Paragraph> getStav() {
        if (stav == null) {
            stav = new ArrayList<Paragraph>();
        }
        return this.stav;
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
    public void setName(String value) {
        this.name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public void initChildrenObservableList() {
        // add all paragraphs
        for (Element e:getStav()) {
            getChildren().add(e);
        }

        // init observable list for all children
        for (Element e: getChildren()) {
            e.initChildrenObservableList();
        }
    }

    @Override
    public void createAndAddChild(String name) {

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