package klondike.views.console;

import java.util.Stack;

import klondike.models.Card;
import klondike.utils.IO;

public class WasteView {

    private final Stack<Card> waste;

    WasteView(Stack<Card> waste) {
        this.waste = waste;
    }

    public void writeln() {
        IO.write(Message.WASTE_TITLE);
        if (this.waste.empty()) {
            IO.writeln(Message.EMPTY);
        } else {
            new CardView(this.waste.peek()).writeln();
        }
    }
}
