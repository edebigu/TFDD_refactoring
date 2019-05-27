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
                        (!this.empty() && card.isNextTo(this.peek())));
    }

    public Suit getSuit() {
        return this.suit;
    }
    
    public boolean empty() {
        return this.cards.empty();
    }
    
    public Card peek() {
        return this.cards.peek();
    }
    
    public void push(Card card) {
        this.cards.push(card);
    }
    
    public Card pop() {
        return this.cards.pop();
    }
    
	public Stack<Card> getCards() {
		return cards;
	}
}
