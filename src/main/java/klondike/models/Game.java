package klondike.models;

import java.util.*;

public class Game {

    public static final int NUMBER_OF_PILES = 7;

    private Stock stock;

    private Stack<Card> waste;

    private Map<Suit, Foundation> foundations;

    private List<Pile> piles;

    public Game() {
        this.clear();
    }

    public void clear() {
        this.stock = new Stock();
        this.waste = new Stack<Card>();
        this.foundations = new HashMap<Suit, Foundation>();
        for (Suit suit : Suit.values()) {
            this.foundations.put(suit, new Foundation(suit));
        }
        this.piles = new ArrayList<Pile>();
        for (int i = 0; i < Game.NUMBER_OF_PILES; i++) {
            this.piles.add(new Pile(i + 1, this.stock.takeTop(i + 1)));
        }
    }

    public boolean isFinished() {
        for (Suit suit : Suit.values()) {
            if (!this.foundations.get(suit).isComplete()) {
                return false;
            }
        }
        return true;
    }

    public Error moveFromStockToWaste() {
        if (this.stock.getCards().empty()) {
            return Error.EMPTY_STOCK;
        }
        this.waste.push(this.stock.getCards().pop().flip());
        return null;
    }

    public Error moveFromWasteToFoundation(Suit suit) {
        assert suit != null;
        if (this.waste.empty()) {
            return Error.EMPTY_WASTE;
        }
        Foundation foundation = this.foundations.get(suit);
        if (!foundation.fitsIn(this.waste.peek())) {
            return Error.NO_FIT_FOUNDATION;
        }
        foundation.getCards().push(this.waste.pop());
        return null;
    }

    public Error moveFromWasteToStock() {
        if (!this.stock.getCards().empty()) {
            return Error.NO_EMPTY_STOCK;
        }
        if (this.waste.empty()) {
            return Error.EMPTY_WASTE;
        }
        while (!this.waste.empty()) {
            this.stock.getCards().push(this.waste.pop().flip());
        }
        return null;
    }

    public Error moveFromWasteToPile(int pileIndex) {
        assert (0 <= pileIndex) && (pileIndex <= Game.NUMBER_OF_PILES);
        if (this.waste.empty()) {
            return Error.EMPTY_WASTE;
        }
        Pile pile = this.piles.get(pileIndex);
        if (!pile.fitsIn(this.waste.peek())) {
            return Error.NO_FIT_PILE;
        }
        pile.addToTop(Arrays.asList(this.waste.pop()));
        return null;
    }

    public Error moveFromFoundationToPile(Suit suit, int pileIndex) {
        assert suit != null;
        assert (0 <= pileIndex) && (pileIndex <= Game.NUMBER_OF_PILES);
        Foundation foundation = this.foundations.get(suit);
        if (foundation.getCards().empty()) {
            return Error.EMPTY_FOUNDATION;
        }
        Pile pile = this.piles.get(pileIndex);
        if (!pile.fitsIn(foundation.getCards().peek())) {
            return Error.NO_FIT_PILE;
        }
        pile.addToTop(Arrays.asList(foundation.getCards().pop()));
        return null;
    }

    public Error moveFromPileToFoundation(int pileIndex, Suit suit) {
        assert (0 <= pileIndex) && (pileIndex <= Game.NUMBER_OF_PILES);
        assert suit != null;
        Pile pile = this.piles.get(pileIndex);
        
        if (pile.getCards().empty()) {
            return Error.EMPTY_PILE;
        }
        Card card = pile.getTop(1).get(0);
        Foundation foundation = this.foundations.get(suit);
        if (!foundation.fitsIn(card)) {
            return Error.NO_FIT_FOUNDATION;
        }
        foundation.getCards().push(card);
        pile.removeTop(1);
        return null;
    }

    public Error moveFromPileToPile(int originIndex, int destinationIndex, int numberOfCards) {
        assert (0 <= originIndex) && (originIndex <= Game.NUMBER_OF_PILES);
        assert (0 <= destinationIndex) && (destinationIndex <= Game.NUMBER_OF_PILES);
        assert (0 <= numberOfCards);
        if (originIndex == destinationIndex) {
            return Error.SAME_PILE;
        }
        Pile originPile = this.piles.get(originIndex);
        
        if (originPile.numberOfFaceUpCards() < numberOfCards) {
            return Error.NO_ENOUGH_CARDS_PILE;
        }
        Pile destinationPile = this.piles.get(destinationIndex);
        List<Card> cards = originPile.getTop(numberOfCards);
        if (!destinationPile.fitsIn(cards.get(cards.size() - 1))) {
            return Error.NO_FIT_PILE;
        }
        originPile.removeTop(numberOfCards);
        destinationPile.addToTop(cards);
        return null;
    }


    public Stock getStock() {
        return this.stock;
    }

    public Stack<Card> getWaste() {
        return this.waste;
    }

    public Map<Suit, Foundation> getFoundations() {
        return foundations;
    }

    public List<Pile> getPiles() {
        return piles;
    }
}
