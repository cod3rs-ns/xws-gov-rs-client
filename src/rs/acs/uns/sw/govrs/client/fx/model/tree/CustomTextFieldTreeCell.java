package rs.acs.uns.sw.govrs.client.fx.model.tree;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import rs.acs.uns.sw.govrs.client.fx.MainFXApp;
import rs.acs.uns.sw.govrs.client.fx.model.Element;
import rs.acs.uns.sw.govrs.client.fx.model.Glava;
import rs.acs.uns.sw.govrs.client.fx.model.Propis;
import rs.acs.uns.sw.govrs.client.fx.model.Tacka;

import java.util.function.Function;

public class CustomTextFieldTreeCell extends TreeCell<Element> {
    private TextField textField;
    private ContextMenu addMenu = new ContextMenu();
    private Function<Element, ObservableValue<String>> text;

    public CustomTextFieldTreeCell(Function<Element, ObservableValue<String>> text) {
        this.text = text;

        if (getItem() instanceof Propis) {
            MenuItem newGlava = new MenuItem("Nova vugla", new ImageView(new Image(getClass().getResourceAsStream("../../images/head.png"))));
            MenuItem newDeo = new MenuItem("Nova deo/dio/duo", new ImageView(new Image(getClass().getResourceAsStream("../../images/deo.png"))));
            addMenu.getItems().add(newGlava);
            addMenu.getItems().add(newDeo);
            newGlava.setOnAction(event -> getTreeItem().getValue().createAndAddChild("Evo nove vugle!"));
            newDeo.setOnAction(event -> getTreeItem().getValue().createAndAddChild("Evo novog dela!"));
        }
        if(getItem() instanceof Tacka) {
            MenuItem falseItem = new MenuItem("Nema šta na tačku dodati!");
            addMenu.getItems().add(falseItem);

            falseItem.setDisable(true);
        }
        if(getItem() instanceof Glava) {
            MenuItem addMenuItem = new MenuItem("Nova tačka", new ImageView(new Image(getClass().getResourceAsStream("../../images/dot.png"))));
            addMenu.getItems().add(addMenuItem);
            addMenuItem.setOnAction(event -> getTreeItem().getValue().createAndAddChild("Evo nove čke!"));
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTextField();
        }
        textProperty().unbind();
        setText("");
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getItem().getName());
        setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../../" + getItem().getImage()))));
    }

    @Override
    public void updateItem(Element item, boolean empty) {
        super.updateItem(item, empty);
        textProperty().unbind();
        graphicProperty().unbind();
        if (empty) {
            setText("");
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(item.getName());
                }
                setText("");
                setGraphic(textField);
            } else {
                textProperty().bind(text.apply(item));
                if (item.getImage() != null) {
                    setGraphic(new ImageView(new Image(MainFXApp.class.getResourceAsStream(item.getImage()))));
                }
                ContextMenu addMenu = new ContextMenu();
                if (getItem() instanceof Propis) {
                    MenuItem newGlava = new MenuItem("Nova vugla", new ImageView(new Image(getClass().getResourceAsStream("../../images/head.png"))));
                    MenuItem newDeo = new MenuItem("Nova deo/dio/duo", new ImageView(new Image(getClass().getResourceAsStream("../../images/deo.png"))));
                    addMenu.getItems().add(newGlava);
                    addMenu.getItems().add(newDeo);
                    newGlava.setOnAction(event -> getTreeItem().getValue().createAndAddChild("Evo nove vugle!"));
                    newDeo.setOnAction(event -> getTreeItem().getValue().createAndAddChild("Evo novog dela!"));
                }
                if(getItem() instanceof Tacka) {
                    MenuItem falseItem = new MenuItem("Nema šta na tačku dodati!");
                    addMenu.getItems().add(falseItem);

                    falseItem.setDisable(true);
                }
                if(getItem() instanceof Glava) {
                    MenuItem addMenuItem = new MenuItem("Nova tačka", new ImageView(new Image(getClass().getResourceAsStream("../../images/dot.png"))));
                    addMenu.getItems().add(addMenuItem);
                    addMenuItem.setOnAction(event -> getTreeItem().getValue().createAndAddChild("Evo nove čke!"));
                }

                setContextMenu(addMenu);

            }
        }
    }

    private void createTextField() {
        textField = new TextField(getItem().getName());
        textField.setOnKeyReleased(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                getItem().setName(textField.getText());
                commitEdit(getItem());
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });

    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
