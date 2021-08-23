package systems.tat.ramta.client.ui.chat;

import com.sun.javafx.scene.NodeHelper;
import javafx.beans.property.*;
import javafx.beans.value.WritableValue;
import javafx.css.*;
import javafx.css.converter.BooleanConverter;
import javafx.css.converter.EnumConverter;
import javafx.css.converter.PaintConverter;
import javafx.css.converter.SizeConverter;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import systems.tat.ramta.client.enums.ChatSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ChatBubble extends Control {

    /*
      ###########################################################################
      #                                                                         #
      # Constructors                                                            #
      #                                                                         #
      ###########################################################################
     */


    public ChatBubble() {
        this("Message...", ChatSender.YOU_SELF);
    }

    public ChatBubble(String text) {
        this(text, ChatSender.YOU_SELF);
    }

    public ChatBubble(String text, ChatSender chatSender) {
        setText(text);
        setChatSender(chatSender);
        initialize();
    }

    private void initialize() {
        getStyleClass().setAll("chat-bubble");
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new ChatBubbleSkin(this);
    }

    /*
      ###########################################################################
      #                                                                         #
      # Properties                                                              #
      #                                                                         #
      ###########################################################################
     */

    private StringProperty text;
    private ObjectProperty<ChatSender> sender;

    private BooleanProperty wrapText;
    private BooleanProperty highlightHyperLink;
    private BooleanProperty displayHyperLinkContent;
    private BooleanProperty underLine;
    private DoubleProperty lineSpacing;

    private ObjectProperty<Paint> textFill;
    private ObjectProperty<Paint> selectionBackgroundFill;
    private ObjectProperty<Paint> selectionTextFill;
    private ObjectProperty<Paint> hyperLinkFill;
    private ObjectProperty<Font> font;

    private ObjectProperty<Pos> alignment;
    private ObjectProperty<TextAlignment> textAlignment;

    public final StringProperty textProperty() {
        if(this.text == null) {
            this.text = new SimpleStringProperty(this, "text", "");
        }
        return this.text;
    }

    public final ObjectProperty<ChatSender> senderProperty() {
        if(this.sender == null) {
            this.sender = new SimpleObjectProperty<>(this, "sender", ChatSender.YOU_SELF);
        }
        return sender;
    }

    public final DoubleProperty lineSpacingProperty() {
        if(this.lineSpacing == null) {
            this.lineSpacing = new StyleableDoubleProperty(0) {
                @Override
                public Object getBean() {
                    return ChatBubble.this;
                }

                @Override
                public String getName() {
                    return "lineSpacing";
                }

                @Override
                public CssMetaData<ChatBubble, Number> getCssMetaData() {
                    return null; //Todo: Make LINE_SPACING css values
                }
            };
        }
        return this.lineSpacing;
    }

    public final BooleanProperty underLineProperty() {
        if(this.underLine == null) {
            this.underLine = new StyleableBooleanProperty(Boolean.FALSE) {
                @Override
                public Object getBean() {
                    return ChatBubble.this;
                }

                @Override
                public String getName() {
                    return "underLine";
                }

                @Override
                public CssMetaData<ChatBubble, Boolean> getCssMetaData() {
                    return null; //Todo: Make UNDERLINE css value
                }
            };
        }
        return this.underLine;
    }

    public final BooleanProperty displayHyperLinkContentProperty() {
        if(this.displayHyperLinkContent == null) {
            this.displayHyperLinkContent = new StyleableBooleanProperty(Boolean.TRUE) {
                @Override
                public Object getBean() {
                    return ChatBubble.this;
                }

                @Override
                public String getName() {
                    return "displayHyperLinkContent";
                }

                @Override
                public CssMetaData<ChatBubble, Boolean> getCssMetaData() {
                    return null; //Todo: Make DISPLAY_HYPERLINK_CONTENT css value
                }
            };
        }
        return this.displayHyperLinkContent;
    }

    public final BooleanProperty highlightHyperLinkProperty() {
        if(this.highlightHyperLink == null) {
            this.highlightHyperLink = new StyleableBooleanProperty(Boolean.TRUE) {
                @Override
                public Object getBean() {
                    return ChatBubble.this;
                }

                @Override
                public String getName() {
                    return "highlightHyperLink";
                }

                @Override
                public CssMetaData<ChatBubble, Boolean> getCssMetaData() {
                    return null; //Todo: Make HIGHLIGHT_HYPER_LINK css value
                }
            };
        }
        return this.highlightHyperLink;
    }

    public final BooleanProperty wrapTextProperty() {
        if(this.wrapText == null) {
            this.wrapText = new StyleableBooleanProperty(Boolean.TRUE) {
                @Override
                public Object getBean() {
                    return ChatBubble.this;
                }

                @Override
                public String getName() {
                    return "wrapText";
                }

                @Override
                public CssMetaData<ChatBubble, Boolean> getCssMetaData() {
                    return null; //Todo: Make WRAP_TEXT css value
                }
            };
        }
        return this.wrapText;
    }

    public final ObjectProperty<Font> fontProperty() {
        if(this.font == null) {
            this.font = new StyleableObjectProperty<Font>(Font.getDefault()) {

                private boolean fontByCss = false;

                @Override
                public void applyStyle(StyleOrigin origin, Font v) {
                    try {
                        fontByCss = true;
                        super.applyStyle(origin, v);
                    } finally {
                        fontByCss = false;
                    }
                }

                @Override
                public void set(Font v) {
                    final Font oldValue = get();
                    if(!Objects.equals(v, oldValue)) {
                        super.set(v);
                    }
                }

                @Override
                protected void invalidated() {
                    if(!fontByCss) {
                        NodeHelper.reapplyCSS(ChatBubble.this);
                    }
                }

                @Override
                public Object getBean() {
                    return ChatBubble.this;
                }

                @Override
                public String getName() {
                    return "font";
                }

                @Override
                public CssMetaData<ChatBubble, Font> getCssMetaData() {
                    return null; //Todo: Make FONT css value
                }
            };
        }
        return this.font;
    }

    public final ObjectProperty<Pos> alignmentProperty() {
        if(this.alignment == null) {
            this.alignment = new StyleableObjectProperty<>(Pos.TOP_LEFT) {

                @Override
                public Object getBean() {
                    return ChatBubble.this;
                }

                @Override
                public String getName() {
                    return "alignment";
                }

                @Override
                public CssMetaData<ChatBubble, Pos> getCssMetaData() {
                    return null; //Todo: Make ALIGNMENT css value
                }
            };
        }
        return this.alignment;
    }

    public final ObjectProperty<TextAlignment> textAlignmentProperty() {
        if(this.textAlignment == null) {
            this.textAlignment = new StyleableObjectProperty<>(TextAlignment.LEFT) {
                @Override
                public Object getBean() {
                    return ChatBubble.this;
                }

                @Override
                public String getName() {
                    return "textAlignment";
                }

                @Override
                public CssMetaData<ChatBubble, TextAlignment> getCssMetaData() {
                    return null; //Todo: Make TEXT_ALIGNMENT css value
                }
            };
        }
        return this.textAlignment;
    }

    public final ObjectProperty<Paint> textFillProperty() {
        if(this.textFill == null) {
            this.textFill = new StyleableObjectProperty<>(Color.BLACK) {
                @Override
                public Object getBean() {
                    return ChatBubble.this;
                }

                @Override
                public String getName() {
                    return "textFill";
                }

                @Override
                public CssMetaData<ChatBubble, Paint> getCssMetaData() {
                    return null; //Todo: Make TEXT_FILL css value
                }
            };
        }
        return this.textFill;
    }

    public final ObjectProperty<Paint> selectionBackgroundFillProperty() {
        if(this.selectionBackgroundFill == null) {
            this.selectionBackgroundFill = new StyleableObjectProperty<>(Color.CORNFLOWERBLUE) {
                @Override
                public Object getBean() {
                    return ChatBubble.this;
                }

                @Override
                public String getName() {
                    return "selectionBackgroundFill";
                }

                @Override
                public CssMetaData<ChatBubble, Paint> getCssMetaData() {
                    return null; //Todo: Make SELECTION_BACKGROUND_FILL css value
                }
            };
        }
        return this.selectionBackgroundFill;
    }

    public final ObjectProperty<Paint> selectionTextFillProperty() {
        if(this.selectionTextFill == null) {
            this.selectionTextFill = new StyleableObjectProperty<>(Color.WHITE) {
                @Override
                public Object getBean() {
                    return ChatBubble.this;
                }

                @Override
                public String getName() {
                    return "selectionTextFill";
                }

                @Override
                public CssMetaData<ChatBubble, Paint> getCssMetaData() {
                    return null; //Todo: Make SELECTION_TEXT_FILL css value
                }
            };
        }
        return this.selectionTextFill;
    }

    public final ObjectProperty<Paint> hyperLinkFillProperty() {
        if(this.hyperLinkFill == null) {
            this.hyperLinkFill = new StyleableObjectProperty<>(Color.CORNFLOWERBLUE) {
                @Override
                public Object getBean() {
                    return ChatBubble.this;
                }

                @Override
                public String getName() {
                    return "hyperLinkFill";
                }

                @Override
                public CssMetaData<ChatBubble, Paint> getCssMetaData() {
                    return null; //Todo: Make HYPERLINK_FILL css value
                }
            };
        }
        return this.hyperLinkFill;
    }

    /*
      ###########################################################################
      #                                                                         #
      # Methods                                                                 #
      #                                                                         #
      ###########################################################################
     */

    public final void setAlignment(Pos alignment) {
        this.alignmentProperty().setValue(alignment);
    }

    public final Pos getAlignment() {
        return this.alignment == null ? Pos.TOP_LEFT : this.alignment.get();
    }

    public final void setChatSender(ChatSender sender) {
        this.senderProperty().setValue(sender);
    }

    public final ChatSender getChatSender() {
        return this.sender == null ? ChatSender.YOU_SELF : this.sender.get();
    }

    public final void setDisplayHyperLinkContent(boolean display) {
        this.displayHyperLinkContentProperty().setValue(display);
    }

    public final boolean isDisplayHyperLinkContent() {
        return this.displayHyperLinkContent == null || this.displayHyperLinkContent.getValue();
    }

    public final void setFont(Font font) {
        this.fontProperty().setValue(font);
    }

    public final Font getFont() {
        return this.font == null ? Font.getDefault() : this.font.get();
    }

    public final void setHighlightHyperLink(boolean highlight) {
        this.highlightHyperLinkProperty().setValue(highlight);
    }

    public final boolean isHighlightHyperLink() {
        return this.highlightHyperLink == null || this.highlightHyperLink.getValue();
    }

    public final void setHyperLinkFill(Paint hyperLinkFill) {
        this.hyperLinkFillProperty().setValue(hyperLinkFill);
    }

    public final Paint getHyperLinkFill() {
        return this.hyperLinkFill == null ? Color.CORNFLOWERBLUE : this.hyperLinkFill.get();
    }

    public final void setLineSpacing(double lineSpacing) {
        this.lineSpacingProperty().setValue(lineSpacing);
    }

    public final double getLineSpacing() {
        return this.lineSpacing == null ? 0 : this.lineSpacing.getValue();
    }

    public final void setSelectionBackgroundFill(Paint selectionBackgroundFill) {
        this.selectionBackgroundFillProperty().setValue(selectionBackgroundFill);
    }

    public final Paint getSelectionBackgroundFill() {
        return this.selectionBackgroundFill == null ? Color.CORNFLOWERBLUE : this.selectionBackgroundFill.get();
    }

    public final void setSelectionTextFill(Paint selectionTextFill) {
        this.selectionTextFillProperty().setValue(selectionTextFill);
    }

    public final Paint getSelectionTextFill() {
        return this.selectionTextFill == null ? Color.WHITE : this.selectionTextFill.get();
    }

    public final void setText(String text) {
        this.textProperty().setValue(text);
    }

    public final String getText() {
        return this.text == null ? "" : this.text.getValue();
    }

    public final void setTextFill(Paint paint) {
        this.textFillProperty().setValue(paint);
    }

    public final Paint getTextFill() {
        return this.textFill == null ? Color.BLACK : this.textFill.get();
    }

    public final void setUnderLine(boolean underLine) {
        this.underLineProperty().setValue(underLine);
    }

    public final boolean isUnderLine() {
        return this.underLine == null || this.underLine.getValue();
    }

    public final void setTextAlignment(TextAlignment textAlignment) {
        this.textAlignmentProperty().setValue(textAlignment);
    }

    public final TextAlignment getTextAlignment() {
        return this.textAlignment == null ? TextAlignment.LEFT : this.textAlignment.get();
    }

    public final void wrapText(boolean wrap) {
        this.wrapTextProperty().setValue(wrap);
    }

    public final boolean isWrapText() {
        return this.wrapText == null || this.wrapText.getValue();
    }

    /*
      ###########################################################################
      #                                                                         #
      # Stylesheet Handling                                                     #
      #                                                                         #
      ###########################################################################
     */

    protected Pos getInitialAlignment() {
        return Pos.TOP_LEFT;
    }

    private static class StylesheetHandler {

        /*
        ------------------------------------
        - Font                             -
        ------------------------------------
        */
        private static final FontCssMetaData<ChatBubble> FONT =
                new FontCssMetaData<ChatBubble>("-fx-font", Font.getDefault()) {
                    @Override
                    public boolean isSettable(ChatBubble styleable) {
                        return styleable.font == null || styleable.font.isBound();
                    }

                    @Override
                    public StyleableProperty<Font> getStyleableProperty(ChatBubble styleable) {
                        return (StyleableProperty<Font>) (WritableValue<Font>) styleable.fontProperty();
                    }
                };

        /*
        ------------------------------------
        - Alignment                        -
        ------------------------------------
        */
        private static final CssMetaData<ChatBubble, Pos> ALIGNMENT =
                new CssMetaData<ChatBubble, Pos>("-fx-alignment",
                        new EnumConverter<Pos>(Pos.class), Pos.TOP_LEFT) {
                    @Override
                    public boolean isSettable(ChatBubble styleable) {
                        return styleable.alignment == null || styleable.alignment.isBound();
                    }

                    @Override
                    public StyleableProperty<Pos> getStyleableProperty(ChatBubble styleable) {
                        return (StyleableProperty<Pos>) (WritableValue<Pos>) styleable.alignmentProperty();
                    }

                    @Override
                    public Pos getInitialValue(ChatBubble styleable) {
                        return styleable.getInitialAlignment();
                    }
                };

        /*
        ------------------------------------
        - Text Alignment                   -
        ------------------------------------
        */
        private static final CssMetaData<ChatBubble, TextAlignment> TEXT_ALIGNMENT =
                new CssMetaData<ChatBubble, TextAlignment>("-fx-text-alignment",
                        new EnumConverter<TextAlignment>(TextAlignment.class), TextAlignment.LEFT) {
                    @Override
                    public boolean isSettable(ChatBubble styleable) {
                        return styleable.textAlignment == null || styleable.textAlignment.isBound();
                    }

                    @Override
                    public StyleableProperty<TextAlignment> getStyleableProperty(ChatBubble styleable) {
                        return (StyleableProperty<TextAlignment>) (WritableValue<TextAlignment>) styleable.textAlignmentProperty();
                    }
                };

        /*
        ------------------------------------
        - Text Fill                        -
        ------------------------------------
        */
        private static final CssMetaData<ChatBubble, Paint> TEXT_FILL =
                new CssMetaData<ChatBubble, Paint>("-fx-text-fill",
                        PaintConverter.getInstance(), Color.BLACK) {
                    @Override
                    public boolean isSettable(ChatBubble styleable) {
                        return styleable.textFill == null || styleable.textFill.isBound();
                    }

                    @Override
                    public StyleableProperty<Paint> getStyleableProperty(ChatBubble styleable) {
                        return (StyleableProperty<Paint>) (WritableValue<Paint>) styleable.textFillProperty();
                    }
                };

        /*
        ------------------------------------
        - Selection Background Fill        -
        ------------------------------------
        */
        private static final CssMetaData<ChatBubble, Paint> SELECTION_BACKGROUND_FILL =
                new CssMetaData<ChatBubble, Paint>("-rtx-selection-background",
                        PaintConverter.getInstance(), Color.CORNFLOWERBLUE) {
                    @Override
                    public boolean isSettable(ChatBubble styleable) {
                        return styleable.selectionBackgroundFill == null || styleable.selectionBackgroundFill.isBound();
                    }

                    @Override
                    public StyleableProperty<Paint> getStyleableProperty(ChatBubble styleable) {
                        return (StyleableProperty<Paint>) (WritableValue<Paint>) styleable.selectionBackgroundFillProperty();
                    }
                };

        /*
        ------------------------------------
        - Selection Text Fill              -
        ------------------------------------
        */
        private static final CssMetaData<ChatBubble, Paint> SELECTION_TEXT_FILL =
                new CssMetaData<ChatBubble, Paint>("-rtx-selection-text-fill",
                        PaintConverter.getInstance(), Color.WHITE) {
                    @Override
                    public boolean isSettable(ChatBubble styleable) {
                        return styleable.selectionTextFill == null || styleable.selectionTextFill.isBound();
                    }

                    @Override
                    public StyleableProperty<Paint> getStyleableProperty(ChatBubble styleable) {
                        return (StyleableProperty<Paint>) (WritableValue<Paint>) styleable.selectionTextFillProperty();
                    }
                };

        /*
        ------------------------------------
        - Hyperlink Text Fill              -
        ------------------------------------
        */
        private static final CssMetaData<ChatBubble, Paint> HYPERLINK_TEXT_FILL =
                new CssMetaData<ChatBubble, Paint>("-rtx-hyper-text-fill",
                        PaintConverter.getInstance(), Color.CORNFLOWERBLUE) {
                    @Override
                    public boolean isSettable(ChatBubble styleable) {
                        return styleable.hyperLinkFill == null || styleable.hyperLinkFill.isBound();
                    }

                    @Override
                    public StyleableProperty<Paint> getStyleableProperty(ChatBubble styleable) {
                        return (StyleableProperty<Paint>) (WritableValue<Paint>) styleable.hyperLinkFillProperty();
                    }
                };

        /*
        ------------------------------------
        - Wrap Text                        -
        ------------------------------------
        */
        private static final CssMetaData<ChatBubble, Boolean> WRAP_TEXT =
                new CssMetaData<ChatBubble, Boolean>("-fx-wrap-text",
                        BooleanConverter.getInstance(), Boolean.TRUE) {
                    @Override
                    public boolean isSettable(ChatBubble styleable) {
                        return styleable.wrapText == null || styleable.wrapText.isBound();
                    }

                    @Override
                    public StyleableProperty<Boolean> getStyleableProperty(ChatBubble styleable) {
                        return (StyleableProperty<Boolean>) (WritableValue<Boolean>) styleable.wrapTextProperty();
                    }
                };

        /*
        ------------------------------------
        - Highlight Hyper Link             -
        ------------------------------------
        */
        private static final CssMetaData<ChatBubble, Boolean> HIGHLIGHT_HYPERLINK =
                new CssMetaData<ChatBubble, Boolean>("-rtx-highlight-hyperlink",
                        BooleanConverter.getInstance(), Boolean.TRUE) {
                    @Override
                    public boolean isSettable(ChatBubble styleable) {
                        return styleable.highlightHyperLink == null || styleable.highlightHyperLink.isBound();
                    }

                    @Override
                    public StyleableProperty<Boolean> getStyleableProperty(ChatBubble styleable) {
                        return (StyleableProperty<Boolean>) (WritableValue<Boolean>) styleable.highlightHyperLinkProperty();
                    }
                };

        /*
        ------------------------------------
        - Display Hyper Link               -
        ------------------------------------
        */
        private static final CssMetaData<ChatBubble, Boolean> DISPLAY_HYPERLINK =
                new CssMetaData<ChatBubble, Boolean>("-rtx-display-hyperlink",
                        BooleanConverter.getInstance(), Boolean.TRUE) {
                    @Override
                    public boolean isSettable(ChatBubble styleable) {
                        return styleable.displayHyperLinkContent == null || styleable.displayHyperLinkContent.isBound();
                    }

                    @Override
                    public StyleableProperty<Boolean> getStyleableProperty(ChatBubble styleable) {
                        return (StyleableProperty<Boolean>) (WritableValue<Boolean>) styleable.displayHyperLinkContentProperty();
                    }
                };

        /*
        ------------------------------------
        - Underline                        -
        ------------------------------------
        */
        private static final CssMetaData<ChatBubble, Boolean> UNDERLINE =
                new CssMetaData<ChatBubble, Boolean>("-fx-underline",
                        BooleanConverter.getInstance(), Boolean.FALSE) {
                    @Override
                    public boolean isSettable(ChatBubble styleable) {
                        return styleable.underLine == null || styleable.underLine.isBound();
                    }

                    @Override
                    public StyleableProperty<Boolean> getStyleableProperty(ChatBubble styleable) {
                        return (StyleableProperty<Boolean>) (WritableValue<Boolean>) styleable.underLineProperty();
                    }
                };

        /*
        ------------------------------------
        - Line Spacing                     -
        ------------------------------------
        */
        private static final CssMetaData<ChatBubble, Number> LINE_SPACING =
                new CssMetaData<ChatBubble, Number>("-fx-line-spacing",
                        SizeConverter.getInstance(), 0) {
                    @Override
                    public boolean isSettable(ChatBubble styleable) {
                        return styleable.lineSpacing == null || styleable.lineSpacing.isBound();
                    }

                    @Override
                    public StyleableProperty<Number> getStyleableProperty(ChatBubble styleable) {
                        return (StyleableProperty<Number>) (WritableValue<Number>) styleable.lineSpacingProperty();
                    }
                };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables =
                    new ArrayList<CssMetaData<? extends Styleable, ?>>(Control.getClassCssMetaData());
            Collections.addAll(styleables,
                    FONT,
                    ALIGNMENT,
                    TEXT_ALIGNMENT,
                    TEXT_FILL,
                    WRAP_TEXT,
                    UNDERLINE,
                    LINE_SPACING,
                    HIGHLIGHT_HYPERLINK,
                    HYPERLINK_TEXT_FILL,
                    DISPLAY_HYPERLINK,
                    SELECTION_BACKGROUND_FILL,
                    SELECTION_TEXT_FILL
            );
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return ChatBubble.StylesheetHandler.STYLEABLES;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }
}
