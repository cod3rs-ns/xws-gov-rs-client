
package rs.acs.uns.sw.govrs.client.fx.serverdomain;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for korisnik_aplikacije element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="korisnik_aplikacije">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;extension base="{http://www.parlament.gov.rs/schema/korisnici}TKorisnik">
 *         &lt;sequence>
 *           &lt;element name="korisnicko_ime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="lozinka" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/sequence>
 *       &lt;/extension>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "korisnickoIme",
    "lozinka",
    "email"
})
@XmlRootElement(name = "korisnik_aplikacije", namespace = "http://www.parlament.gov.rs/schema/korisnici")
public class AppUser
    extends User
{

    @XmlElement(name = "korisnicko_ime", namespace = "http://www.parlament.gov.rs/schema/korisnici", required = true)
    protected String korisnickoIme;
    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/korisnici", required = true)
    protected String lozinka;
    @XmlElement(namespace = "http://www.parlament.gov.rs/schema/korisnici", required = true)
    protected String email;

    /**
     * Gets the value of the korisnickoIme property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    /**
     * Sets the value of the korisnickoIme property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKorisnickoIme(String value) {
        this.korisnickoIme = value;
    }

    /**
     * Gets the value of the lozinka property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLozinka() {
        return lozinka;
    }

    /**
     * Sets the value of the lozinka property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLozinka(String value) {
        this.lozinka = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

}
