public class Order {
    enum Type { BUY, SELL }

    final Type type;
    final String ticker;
    int quantity;
    final double price;

    Order(Type type, String ticker, int quantity, double price) {
        this.type = type;
        this.ticker = ticker;
        this.quantity = quantity;
        this.price = price;
    }
}
