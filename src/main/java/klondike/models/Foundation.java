package klondike.models;

import java.util.Stack;

public class Foundation  {

    private Suit suit;
    protected Stack<Card> cards;

    public Foundation(Suit suit) {
    	this.cards = new Stack<Card>();
        this.suit = suit;
    }

    public boolean isComplete() {
        return this.cards.size() == Number.values().length;
    }

    public boolean fitsIn(Card card) {
        assert card != null;
        return card.getSuit() == this.suit &&
                (card.getNumber() == Number.AS ||
                        (!this.cards.empty() && card.isNextTo(this.cards.peek())));
    }

    public Suit getSuit() {
        return this.suit;
    }
     
    
	public Stack<Card> getCards() {
		return cards;
	}
}
