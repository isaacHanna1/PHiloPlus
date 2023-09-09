
package philoplus.philoPlusClasses;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;

public class AutoCompleteComboBox <T> extends ComboBox<T> {
public AutoCompleteComboBox() {
        super();
        setEditable(true);
        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE && !getEditor().getText().isEmpty()) {
                String text = getEditor().getText().substring(0, getEditor().getText().length() - 1);
                getEditor().setText(text);
                getEditor().end();
                show();
            }
        });
        setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                getSelectionModel().select((T) getEditor().getText());
                getEditor().end();
                hide();
            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                if (getEditor().getText().isEmpty()) {
                    getSelectionModel().clearSelection();
                }
            } else if (event.getCode() == KeyCode.DOWN) {
                show();
            } else {
                filterItems(getEditor().getText());
                show();
            }
        });
}
    private void filterItems(String text) {
        ObservableList<T> items = getItems();
        String filter = text.toLowerCase();
        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            if (item.toString().toLowerCase().startsWith(filter)) {
                getSelectionModel().select(item);
                getEditor().setText(item.toString());
                getEditor().end();
                break;
            }
        }
    }
}
