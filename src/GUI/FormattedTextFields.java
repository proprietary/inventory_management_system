package GUI;

import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;

public class FormattedTextFields {
    public static UnaryOperator<TextFormatter.Change> integerFieldFormatter = (TextFormatter.Change c) -> {
        return (c.getControlNewText().matches("^[0-9]*$")) ? c : null;
    };

    public static UnaryOperator<TextFormatter.Change> doubleFieldFormatter = (TextFormatter.Change c) -> {
        return c.getControlNewText().matches("^[0-9]*(?:\\.[0-9]{0,2})?$") ? c : null;
    };
}
