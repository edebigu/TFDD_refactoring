package klondike.models.builders;

import klondike.models.Stock;

public class EmptyStockBuilder {

	public Stock build() {
		Stock stock = new Stock();
		while (!stock.getCards().empty()) {
			stock.getCards().pop();
		}
		return stock;
	}
}
