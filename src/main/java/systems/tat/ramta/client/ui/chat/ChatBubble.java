package systems.tat.ramta.client.ui.chat;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import javafx.css.*;
import javafx.css.converter.BooleanConverter;
import javafx.css.converter.EnumConverter;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import systems.tat.ramta.client.enums.ChatSender;

import java.util.Objects;

public class ChatBubble extends Control {

    /* Todo
    *   - Link matching, will embed the given link.
    *   - Emoji embed and raw embed (<3 = heart) toggleable
    *   - Voice mails
    *   - Sticker system (optional)
    *   - Send Images from(Web and Gallery) (GIFs)
    */
    protected static final String DEFAULT_EMOJIS_LOCATION = "/frontend/general/emojis";

    private ChatSender chatSender;

    public ChatBubble() {
        this.getStyleClass().add("chat-bubble");
        this.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            ChatBubble.this.requestLayout();
        });
    }

    public ChatBubble(ChatSender chatSender, String message) {
        this();
        this.chatSender = chatSender;
        this.setText(message);
    }

    public ChatSender getChatSender() {
        return this.chatSender;
    }

    public void setChatSender(ChatSender chatSender) {
        this.chatSender = chatSender;
    }
    /*****************************************************
     *                                                   *
     * Properties                                        *
     *                                                   *
     *****************************************************/

    /*****************************************************
     * Text property
     *****************************************************/
    private StringProperty text;

    public final StringProperty textProperty() {
        if(this.text == null) {
            this.text = new SimpleStringProperty(this, "text", "");
        }
        return this.text;
    }

    public void setText(String message) {
        this.textProperty().setValue(message);
    }

    public void addText(String message) {
        this.setText(getText() + message);
    }

    public String getText() {
        return this.text == null ? "" : this.text.getValue();
    }

    /*****************************************************
     * Emojis property
     *****************************************************/
    private StringProperty emojisLocation;

    public final StringProperty emojisLocationProperty() {
        if(this.emojisLocation == null) {
            this.emojisLocation = new SimpleStringProperty(this, "emojisLocation", DEFAULT_EMOJIS_LOCATION);
        }
        return this.emojisLocation;
    }

    public void setEmojisLocation(String location) {
        this.emojisLocationProperty().setValue(location);
    }

    public String getEmojisLocation() {
        return this.emojisLocation == null ? "" : this.emojisLocation.getValue();
    }

    /*****************************************************
     * Alignment property
     *****************************************************/
    private ObjectProperty<Pos> alignment;

    public final ObjectProperty<Pos> alignmentProperty() {
        if(this.alignment == null) {
            this.alignment = new StyleableObjectProperty<Pos>(Pos.TOP_LEFT) {
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
                    return StyleableProperties.ALIGNMENT;
                }
            };
        }
        return this.alignment;
    }

    public void setAlignment(Pos pos) {
        this.alignmentProperty().setValue(pos);
    }

    public Pos getAlignment() {
        return this.alignment == null ? cssAlignmentInitialValue() : this.alignment.get();
    }

    /*****************************************************
     * Alignment property
     *****************************************************/
    private ObjectProperty<TextAlignment> textAlignment;

    public final ObjectProperty<TextAlignment> textAlignmentProperty() {
        if(this.textAlignment == null) {
            this.textAlignment = new StyleableObjectProperty<TextAlignment>(TextAlignment.LEFT) {
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
                    return StyleableProperties.TEXT_ALIGNMENT;
                }
            };
        }
        return this.textAlignment;
    }

    public void setTextAlignment(TextAlignment alignment) {
        this.textAlignmentProperty().setValue(alignment);
    }

    public TextAlignment getTextAlignment() {
        return this.textAlignment == null ? TextAlignment.LEFT : this.textAlignment.get();
    }

    /*****************************************************
     * Font property
     *****************************************************/
    private ObjectProperty<Font> font;

    public final ObjectProperty<Font> fontProperty() {
        if(this.font == null) {
            font = new StyleableObjectProperty<Font>(Font.getDefault()) {
                private boolean fontCss = false;

                @Override
                public void applyStyle(StyleOrigin origin, Font value) {
                    try {
                        fontCss = true;
                        super.applyStyle(origin, value);
                    } catch (Exception exception) {
                        throw exception;
                    } finally {
                        fontCss = false;
                    }
                }

                @Override
                public void set(Font value) {
                    final Font oldFont = get();
                    if(!Objects.equals(value, oldFont)) {
                        super.set(value);
                    }
                }

                @Override
                protected void invalidated() {
                    if(!fontCss) {
                        //Todo: check is font set by hard code...
                    }
                }

                @Override
                public CssMetaData<ChatBubble, Font> getCssMetaData() {
                    return StyleableProperties.FONT;
                }

                @Override
                public Object getBean() {
                    return ChatBubble.this;
                }

                @Override
                public String getName() {
                    return "font";
                }
            };
        }
        return this.font;
    }

    public void setFont(Font font) {
        this.fontProperty().setValue(font);
    }

    public Font getFont() {
        return this.font == null ? Font.getDefault() : this.font.getValue();
    }

    /*****************************************************
     * Underline property
     *****************************************************/
    private BooleanProperty underline;

    public final BooleanProperty underlineProperty() {
        if(this.underline == null) {
            this.underline = new StyleableBooleanProperty(false) {
                @Override
                public Object getBean() {
                    return ChatBubble.this;
                }

                @Override
                public String getName() {
                    return "underline";
                }

                @Override
                public CssMetaData<? extends Styleable, Boolean> getCssMetaData() {
                    return StyleableProperties.UNDERLINE;
                }
            };
        }
        return this.underline;
    }

    public void setUnderline(boolean value) {
        this.underlineProperty().setValue(value);
    }

    public boolean isUnderline() {
        return this.underline != null && this.underline.getValue();
    }

    /*****************************************************
     *                                                   *
     * Stylesheet handling                               *
     *                                                   *
     *****************************************************/
    protected Pos cssAlignmentInitialValue() {
        return Pos.TOP_LEFT;
    }

    private static class StyleableProperties {

        private static final FontCssMetaData<ChatBubble> FONT =
                new FontCssMetaData<ChatBubble>("-fx-font", Font.getDefault()) {
                    @Override
                    public boolean isSettable(ChatBubble chatBubble) {
                        return chatBubble.font == null | !chatBubble.font.isBound();
                    }

                    @Override
                    public StyleableProperty<Font> getStyleableProperty(ChatBubble chatBubble) {
                        return (StyleableProperty<Font>) (WritableValue<Font>) chatBubble.fontProperty();
                    }
                };

        private static final CssMetaData<ChatBubble, Pos> ALIGNMENT =
                new CssMetaData<ChatBubble, Pos>("-fx-alignment",
                        new EnumConverter<Pos>(Pos.class), Pos.TOP_LEFT) {
                    @Override
                    public boolean isSettable(ChatBubble chatBubble) {
                        return chatBubble.alignment == null | !chatBubble.alignment.isBound();
                    }

                    @Override
                    public StyleableProperty<Pos> getStyleableProperty(ChatBubble chatBubble) {
                        return (StyleableProperty<Pos>) (WritableValue<Pos>) chatBubble.alignmentProperty();
                    }

                    @Override
                    public Pos getInitialValue(ChatBubble chatBubble) {
                        return chatBubble.cssAlignmentInitialValue();
                    }
                };

        private static final CssMetaData<ChatBubble, TextAlignment> TEXT_ALIGNMENT =
                new CssMetaData<ChatBubble, TextAlignment>("-fx-text-alignment",
                        new EnumConverter<TextAlignment>(TextAlignment.class), TextAlignment.LEFT) {
                    @Override
                    public boolean isSettable(ChatBubble chatBubble) {
                        return chatBubble.textAlignment == null | !chatBubble.textAlignment.isBound();
                    }

                    @Override
                    public StyleableProperty<TextAlignment> getStyleableProperty(ChatBubble chatBubble) {
                        return (StyleableProperty<TextAlignment>) (WritableValue<TextAlignment>) chatBubble.textAlignmentProperty();
                    }
                };

        private static final CssMetaData<ChatBubble, Boolean> UNDERLINE =
                new CssMetaData<ChatBubble, Boolean>("-fx-underline",
                        BooleanConverter.getInstance(), Boolean.FALSE) {
                    @Override
                    public boolean isSettable(ChatBubble chatBubble) {
                        return chatBubble.underline == null | chatBubble.underline.isBound();
                    }

                    @Override
                    public StyleableProperty<Boolean> getStyleableProperty(ChatBubble chatBubble) {
                        return (StyleableProperty<Boolean>) (WritableValue<Boolean>) chatBubble.underlineProperty();
                    }
                };
    }
}
