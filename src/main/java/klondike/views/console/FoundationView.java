package klondike.views.console;

import klondike.models.Foundation;
import klondike.utils.IO;

public class FoundationView {

    private final Foundation foundation;

    FoundationView(Foundation foundation) {
        this.foundation = foundation;
    }


    public void writeln() {
        IO.writetab();
        IO.write(this.foundation.getSuit().toString().toLowerCase() + ": ");
        if (this.foundation.getCards().empty()) {
            IO.write(Message.EMPTY);
        } else {
            IO.write(Message.FOUNDATION_FORMAT.replace(Message.NUMBER_TAG, this.foundation.getCards().peek().getNumber().toString().toLowerCase()));
        }
        IO.writeln();
    }
}
