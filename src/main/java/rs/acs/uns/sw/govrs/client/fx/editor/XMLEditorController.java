package rs.acs.uns.sw.govrs.client.fx.editor;


import com.gluonhq.connect.GluonObservableObject;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PropertySheet;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.StyledTextArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.reactfx.SuspendableNo;
import rs.acs.uns.sw.govrs.client.fx.domain.Element;
import rs.acs.uns.sw.govrs.client.fx.domain.tree.TreeController;
import rs.acs.uns.sw.govrs.client.fx.domain.tree.TreeModel;
import rs.acs.uns.sw.govrs.client.fx.editor.preview.HtmlPreview;
import rs.acs.uns.sw.govrs.client.fx.editor.style.ParStyle;
import rs.acs.uns.sw.govrs.client.fx.editor.style.TextStyle;
import rs.acs.uns.sw.govrs.client.fx.manager.StateManager;
import rs.acs.uns.sw.govrs.client.fx.rest.RestClientProvider;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.Law;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.StringWrapper;
import rs.acs.uns.sw.govrs.client.fx.serverdomain.wrapper.ItemWrapper;
import rs.acs.uns.sw.govrs.client.fx.util.Creator;
import rs.acs.uns.sw.govrs.client.fx.util.CustomDialogCreator;
import rs.acs.uns.sw.govrs.client.fx.util.Loader;
import rs.acs.uns.sw.govrs.client.fx.validation.ErrorMessage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Used for creating Laws.
 */
public class XMLEditorController implements TreeController {
    /**
     * Attribute for CSS change of buttons
     */
    private static final String PRESSED = "pressed";

    /**
     * Styled Text Area attributes
     **/
    private final StyledTextArea<ParStyle, TextStyle> area;
    private final SuspendableNo updatingToolbar = new SuspendableNo();

    // ---------------- ui-containers ------------------
    @FXML
    private TitledPane treeContainer;
    @FXML
    private TitledPane previewContainer;
    @FXML
    private BorderPane areaContainer;
    @FXML
    private BorderPane attributesContainer;
    // -------------------------------------------------

    // ----------- text area commands ------------------
    @FXML
    private Button undoAction;
    @FXML
    private Button redoAction;
    @FXML
    private Button cutAction;
    @FXML
    private Button copyAction;
    @FXML
    private Button pasteAction;
    @FXML
    private Button boldAction;
    @FXML
    private Button italicAction;
    @FXML
    private Button underlineAction;
    @FXML
    private Button strikeAction;
    @FXML
    private Button linkAction;
    @FXML
    private ComboBox<Integer> fontSizePicker;

    // ------------ file control buttons ---------------
    @FXML
    private ImageView openButton;
    @FXML
    private ImageView saveButton;
    @FXML
    private ImageView saveAsButton;
    @FXML
    private ImageView newLawButton;
    @FXML
    private ImageView uploadButton;
    // -------------------------------------------------

    // ------------------ components -------------------
    private HtmlPreview preview;
    private TreeModel tree;
    private PropertySheet propertySheet;
    // -------------------------------------------------

    /**
     * Injected StateManger - parent component
     **/
    private StateManager stateManager;

    /**
     * Active Law element
     **/
    private Law law;

    /**
     * Represents file on disk, if opened, currently initialized to some value for testing
     **/
    private File activeFile = new File(System.getProperty("user.home") + File.separator + "test_save.xml");

    /**
     * Selected element on Tree
     **/
    private Element selectedElement;

    /**
     * Change Listener for Text Area
     **/
    private ChangeListener textAreaChangeListener;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public XMLEditorController() {
        // setup text area
        area = new StyledTextArea<>(
                ParStyle.EMPTY, (paragraph, style) -> paragraph.setStyle(style.toCss()),
                TextStyle.EMPTY.updateFontSize(10).updateFontFamily("Verdana").updateTextColor(Color.BLACK),
                (text, style) -> text.setStyle(style.toCss()));
        area.setWrapText(true);
        area.setStyleCodecs(ParStyle.CODEC, TextStyle.CODEC);
        area.setDisable(true);
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        // setup additional parameters of area and init actions
        setupAdditionalAreaParams();
        connectTextAreaActions();

        // add area to container
        VirtualizedScrollPane<StyledTextArea<ParStyle, TextStyle>> vsPane = new VirtualizedScrollPane<>(area);
        areaContainer.setCenter(vsPane);

        // create tooltips for file control buttons
        Tooltip.install(openButton, new Tooltip("Otvorite novi dokument"));
        Tooltip.install(saveButton, new Tooltip("Sačuvajte dokument"));
        Tooltip.install(saveAsButton, new Tooltip("Sačuvajte dokument kao..."));
        Tooltip.install(newLawButton, new Tooltip("Kreirajte novi propis"));
        Tooltip.install(uploadButton, new Tooltip("Postavite propis na skupštinski red"));
    }

    /**
     * Creates all actions of <strong>TextArea</strong> and initializes all remaining properties.
     * Shouldn't be changed further.
     */
    private void setupAdditionalAreaParams() {
        // Populate possible font sizes
        fontSizePicker.setItems(FXCollections.observableArrayList(8, 9, 10, 11, 12, 14, 16, 20, 24, 32, 40));
        fontSizePicker.getSelectionModel().select(4);

        // Disable Undo&Redo actions when there is nothing to undo/redo
        undoAction.disableProperty().bind(Bindings.not(area.undoAvailableProperty()));
        redoAction.disableProperty().bind(Bindings.not(area.redoAvailableProperty()));

        // Make boolean binding on empty selection, for disabling adequate buttons
        BooleanBinding selectionEmpty = new BooleanBinding() {
            {
                bind(area.selectionProperty());
            }

            @Override
            protected boolean computeValue() {
                return area.getSelection().getLength() == 0;
            }
        };

        // Disable cut/copy when there is no selection
        cutAction.disableProperty().bind(selectionEmpty);
        copyAction.disableProperty().bind(selectionEmpty);

        // Area style update logic
        area.beingUpdatedProperty().addListener((o, old, beingUpdated) -> {
            if (!beingUpdated) {
                boolean bold;
                boolean italic;
                boolean underline;
                boolean strike;
                Integer fontSize;

                IndexRange selection = area.getSelection();
                if (selection.getLength() != 0) {
                    StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
                    bold = styles.styleStream().anyMatch(s -> s.bold.orElse(false));
                    italic = styles.styleStream().anyMatch(s -> s.italic.orElse(false));
                    underline = styles.styleStream().anyMatch(s -> s.underline.orElse(false));
                    strike = styles.styleStream().anyMatch(s -> s.strikethrough.orElse(false));
                    int[] sizes = styles.styleStream().mapToInt(s -> s.fontSize.orElse(-1)).distinct().toArray();
                    fontSize = sizes.length == 1 ? sizes[0] : -1;
                } else {
                    int p = area.getCurrentParagraph();
                    int col = area.getCaretColumn();
                    TextStyle style = area.getStyleAtPosition(p, col);
                    bold = style.bold.orElse(false);
                    italic = style.italic.orElse(false);
                    underline = style.underline.orElse(false);
                    strike = style.strikethrough.orElse(false);
                    fontSize = style.fontSize.orElse(-1);
                }

                updatingToolbar.suspendWhile(() -> {
                    if (bold) {
                        if (!boldAction.getStyleClass().contains(PRESSED)) {
                            boldAction.getStyleClass().add(PRESSED);
                        }
                    } else {
                        boldAction.getStyleClass().remove(PRESSED);
                    }

                    if (italic) {
                        if (!italicAction.getStyleClass().contains(PRESSED)) {
                            italicAction.getStyleClass().add(PRESSED);
                        }
                    } else {
                        italicAction.getStyleClass().remove(PRESSED);
                    }

                    if (underline) {
                        if (!underlineAction.getStyleClass().contains(PRESSED)) {
                            underlineAction.getStyleClass().add(PRESSED);
                        }
                    } else {
                        underlineAction.getStyleClass().remove(PRESSED);
                    }

                    if (strike) {
                        if (!strikeAction.getStyleClass().contains(PRESSED)) {
                            strikeAction.getStyleClass().add(PRESSED);
                        }
                    } else {
                        strikeAction.getStyleClass().remove(PRESSED);
                    }

                    if (fontSize != -1) {
                        fontSizePicker.getSelectionModel().select(fontSize);
                    } else {
                        fontSizePicker.getSelectionModel().clearSelection();
                    }

                    // Update preview on finished style changing
                    if (preview != null) preview.update();
                });
            }
        });
    }

    // ------------------------------------------ TextArea actions ---------------------------------------------------//
    private void toggleBold() {
        updateStyleInSelection(spans -> TextStyle.bold(!spans.styleStream().allMatch(style -> style.bold.orElse(false))));
    }

    private void toggleItalic() {
        updateStyleInSelection(spans -> TextStyle.italic(!spans.styleStream().allMatch(style -> style.italic.orElse(false))));
    }

    private void toggleUnderline() {
        updateStyleInSelection(spans -> TextStyle.underline(!spans.styleStream().allMatch(style -> style.underline.orElse(false))));
    }

    private void toggleStrikethrough() {
        updateStyleInSelection(spans -> TextStyle.strikethrough(!spans.styleStream().allMatch(style -> style.strikethrough.orElse(false))));
    }

    private void updateFontSize(Integer size) {
        if (!updatingToolbar.get()) {
            updateWholeStyle(TextStyle.fontSize(size));
        }
    }

    private void updateStyleInSelection(Function<StyleSpans<TextStyle>, TextStyle> mixinGetter) {
        IndexRange selection = area.getSelection();
        if (selection.getLength() != 0) {
            StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
            TextStyle mixin = mixinGetter.apply(styles);
            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
            area.setStyleSpans(selection.getStart(), newStyles);
        }
    }

    private void updateWholeStyle(TextStyle ts) {
        area.selectAll();
        IndexRange selection = area.getSelection();
        if (selection.getLength() != 0) {
            StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(ts));
            area.setStyleSpans(selection.getStart(), newStyles);
        }
        area.deselect();
    }

    private void updateStyleInSelection(TextStyle mixin) {
        IndexRange selection = area.getSelection();
        if (selection.getLength() != 0) {
            StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
            area.setStyleSpans(selection.getStart(), newStyles);
        }
    }
    // END--------------------------------------- TextArea actions ---------------------------------------------------//

    /**
     * Connects TextArea actions.
     */
    private void connectTextAreaActions() {
        undoAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.UNDO));
        undoAction.setOnAction(event -> {
            Runnable action = area::undo;
            action.run();
            area.requestFocus();
        });
        redoAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.REDO));
        redoAction.setOnAction(event -> {
            Runnable action = area::redo;
            action.run();
            area.requestFocus();
        });
        cutAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.CONTENT_CUT));
        cutAction.setOnAction(event -> {
            Runnable action = area::cut;
            action.run();
            area.requestFocus();
        });
        copyAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.CONTENT_COPY));
        copyAction.setOnAction(event -> {
            Runnable action = area::copy;
            action.run();
            area.requestFocus();
        });
        pasteAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.CONTENT_PASTE));
        pasteAction.setOnAction(event -> {
            Runnable action = area::paste;
            action.run();
            area.requestFocus();
        });

        boldAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.FORMAT_BOLD));
        boldAction.setOnAction(event -> {
            Runnable action = this::toggleBold;
            action.run();
            area.requestFocus();
        });
        italicAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.FORMAT_ITALIC));
        italicAction.setOnAction(event -> {
            Runnable action = this::toggleItalic;
            action.run();
            area.requestFocus();
        });
        underlineAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.FORMAT_UNDERLINE));
        underlineAction.setOnAction(event -> {
            Runnable action = this::toggleUnderline;
            action.run();
            area.requestFocus();
        });
        strikeAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.FORMAT_STRIKETHROUGH));
        strikeAction.setOnAction(event -> {
            Runnable action = this::toggleStrikethrough;
            action.run();
            area.requestFocus();
        });
        linkAction.setGraphic(GlyphsDude.createIcon(MaterialDesignIcon.LINK));
        fontSizePicker.setOnAction(event -> updateFontSize(fontSizePicker.getValue()));
    }

    /**
     * Opens XML file from disk.
     */
    @FXML
    private void openAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Otvori XML propis");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML files", "*.xml")
        );
        Stage stage = (Stage) areaContainer.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                JAXBContext context = JAXBContext.newInstance(Law.class);
                Unmarshaller unMarshaller = context.createUnmarshaller();
                Law openNew = (Law) unMarshaller.unmarshal(file);
                switchViewToNewLaw(openNew);
                Notifications.create().owner(treeContainer.getScene().getWindow()).title("Otvaranje XML fajl").text("XML fajl je uspešno otvoren").showConfirm();
            } catch (Exception e) {
                CustomDialogCreator.createErrorAlert(
                        "GovRS",
                        "GREŠKA!",
                        "Fajl nije moguće otvoriti."
                ).showAndWait();
                Logger.getLogger(getClass().getName()).log(Level.WARNING, "Unable to load file.", e);
            }
        }
    }

    /**
     * Saves active file to disk.
     */
    @FXML
    private void saveAction() {
        if (law != null) {
            try {
                List<ErrorMessage> errors = new ArrayList<>();
                law.validate(errors);
                if (errors.size() > 0) {
                    for (ErrorMessage em : errors) {
                        Notifications.create().owner(areaContainer.getScene().getWindow())
                                .title("Greška <" + em.getElementType().toString() + ">")
                                .text(em.getMessage())
                                .showError();
                    }
                } else {
                    if (activeFile != null) {
                        JAXBContext context = JAXBContext.newInstance(Law.class);
                        Marshaller marshaller = context.createMarshaller();
                        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                        marshaller.marshal(law, activeFile);
                        Notifications.create().owner(areaContainer.getScene().getWindow()).title("Skladištenje XML datoteke").text("Fajl je uspešno sačuvan").showConfirm();
                    }
                }
            } catch (Exception e) {
                CustomDialogCreator.createErrorAlert(
                        "GovRS",
                        "Čucanje XML datoteke",
                        "Fajl nije uspešno sačuvan."
                ).showAndWait();
                Logger.getLogger(getClass().getName()).log(Level.WARNING, "Unable to save file.");
            }
        } else {
            Notifications.create().owner(treeContainer.getScene().getWindow()).title("Neispravna akcija").text("Morate prvo otvoriti/napraviti novi propis").showWarning();
        }
    }

    /**
     * Selects and saves active Law to file.
     */
    @FXML
    private void saveAsAction() {
        if (law != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sačuvajate propis kao...");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("XML files", "*.xml")
            );

            Stage stage = (Stage) areaContainer.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                try {
                    List<ErrorMessage> errors = new ArrayList<>();
                    law.validate(errors);
                    if (errors.size() > 0) {
                        for (ErrorMessage em : errors) {
                            Notifications.create().owner(areaContainer.getScene().getWindow())
                                    .title("Greška <" + em.getElementType().toString() + ">")
                                    .text(em.getMessage())
                                    .showError();
                        }
                    } else {
                        JAXBContext context = JAXBContext.newInstance(Law.class);
                        Marshaller marshaller = context.createMarshaller();
                        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                        marshaller.marshal(law, file);
                        Notifications.create().owner(areaContainer.getScene().getWindow()).title("Skladištenje XML datoteke").text("Fajl je uspešno sačuvan").showConfirm();
                    }
                } catch (Exception e) {
                    CustomDialogCreator.createErrorAlert(
                            "GovRS",
                            "Čuvanje XML datoteke",
                            "Fajl nije uspešno sačuvan."
                    ).showAndWait();
                    Logger.getLogger(getClass().getName()).log(Level.WARNING, "Unable to save file.");
                }
            }
        } else {
            Notifications.create().owner(treeContainer.getScene().getWindow()).title("Neispravna akcija").text("Morate prvo otvoriti/napraviti novi propis").showWarning();
        }
    }

    @FXML
    private void createNewLaw() {
        TextInputDialog dialog = CustomDialogCreator.createNewEntryDialog("Propis");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            Law newl = Creator.createNewLaw();
            newl.setName(name);
            switchViewToNewLaw(newl);
            Notifications.create().owner(treeContainer.getScene().getWindow()).title("Propis").text("Propis je uspešno kreiran!").showInformation();
        });
    }

    @FXML
    private void uploadLaw() {
        if (law != null) {
            if ("sazvana".equals(RestClientProvider.getInstance().parliamentState.get())) {
                List<ErrorMessage> errors = new ArrayList<>();
                law.validate(errors);
                if (errors.size() > 0) {
                    for (ErrorMessage em : errors) {
                        Notifications.create().owner(areaContainer.getScene().getWindow())
                                .title("Greška <" + em.getElementType().toString() + ">")
                                .text(em.getMessage())
                                .showError();
                    }
                } else {
                    law.preMarshaller();
                    GluonObservableObject<Law> lawProperty =
                            RestClientProvider.getInstance().postLaw(law);
                    Stage stage = Loader.createLoader(treeContainer.getScene());
                    stage.show();

                    lawProperty.initializedProperty().addListener(((observable, oldValue, newValue) -> {
                        stage.close();
                        if (lawProperty.get() == null) {
                            Notifications.create().owner(areaContainer.getScene().getWindow()).title("GREŠKA!").text("Propis već postoji!").showError();
                        } else {
                            Notifications.create().owner(areaContainer.getScene().getWindow()).title("Propis").text("Uspešno ste predložili Propis.").showInformation();
                        }
                    }));

                }
            } else {
                Notifications.create().owner(previewContainer.getScene().getWindow()).title("Neuspela operacija").text("Trenutno ne postoji sazvana sednica Skupštine").showWarning();
            }
        } else {
            Notifications.create().owner(treeContainer.getScene().getWindow()).title("Neispravna akcija").text("Morate prvo otvoriti/napraviti novi propis").showWarning();
        }
    }

    /**
     * Updates context when new Law is opened.
     *
     * @param newLaw newly opened Law
     */
    private void switchViewToNewLaw(Law newLaw) {
        area.setDisable(false);
        propertySheet = new PropertySheet();
        attributesContainer.setCenter(propertySheet);
        selectedElement = null;
        law = newLaw;
        law.initElement();
        preview = new HtmlPreview(law, "propis", Law.class);
        previewContainer.setContent(preview.getNode());
        preview.update();
        tree = new TreeModel(
                law,
                Element::getChildren,
                Element::elementNameProperty,
                this
        );

        TreeView<Element> treeView = tree.getTreeView();
        treeContainer.setContent(treeView);
    }

    public Element getSelectedElement() {
        return selectedElement;
    }

    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * This method should be called when on Tree Element change, to trigger context change.
     *
     * @param selectedElement newly selected Element
     */
    public void setSelectedElement(Element selectedElement) {
        this.selectedElement = selectedElement;
        // remove old listener if exist
        if (textAreaChangeListener != null) {
            area.textProperty().removeListener(textAreaChangeListener);
        }

        // displays content of text elements
        if (selectedElement instanceof StringWrapper || selectedElement instanceof ItemWrapper) {
            area.setDisable(false);

            area.clear();
            area.replaceText(0, -1, selectedElement.getElementContent());

            textAreaChangeListener = createChangeAreaListener(selectedElement);
            area.textProperty().addListener(textAreaChangeListener);
        } else {
            area.clear();
            area.setDisable(true);
        }
        this.propertySheet.getItems().clear();
        this.propertySheet.getItems().addAll(selectedElement.getPropertyItems());
    }

    /**
     * Creates new Listener for newly selected Element
     *
     * @param selElement newly selected Element
     * @return ChangeListener instance
     */
    private ChangeListener createChangeAreaListener(Element selElement) {
        return (ChangeListener<String>) (observable, oldValue, newValue) -> {
            selElement.setElementContent(newValue);
            preview.update();
        };
    }

    public HtmlPreview getPreview() {
        return preview;
    }

}
