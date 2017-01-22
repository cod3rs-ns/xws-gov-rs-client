package rs.acs.uns.sw.govrs.client.fx.editor.help;

import rs.acs.uns.sw.govrs.client.fx.domain.Element;

public class PopupEditorInit {
    private String parentIdBase;
    private boolean createNew = true;
    private Element element;
    private boolean saved;

    public PopupEditorInit(String parentIdBase, boolean createNew, Element element) {
        this.parentIdBase = parentIdBase;
        this.createNew = createNew;
        this.element = element;
        this.saved = false;
    }

    public String getParentIdBase() {
        return parentIdBase;
    }

    public void setParentIdBase(String parentIdBase) {
        this.parentIdBase = parentIdBase;
    }

    public boolean isCreateNew() {
        return createNew;
    }

    public void setCreateNew(boolean createNew) {
        this.createNew = createNew;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
