import java.util.concurrent.ThreadLocalRandom;

public class Simulator {
    private static final StockExchange exchange = new StockExchange();
    private static final String[] tickers = { "AAPL", "TSLA", "GOOG", "AMZN", "MSFT" };

    private static void randomOrderGenerator() {
        while (true) {
            Order.Type type = Math.random() > 0.5 ? Order.Type.BUY : Order.Type.SELL;
            String ticker = tickers[(int) (Math.random() * tickers.length)];
            int quantity = (int) (Math.random() * 100) + 1;
            double price = (Math.random() * 400) + 100;

            exchange.addOrder(new Order(type, ticker, quantity, price));
            exchange.matchOrders();

            try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            new Thread(Simulator::randomOrderGenerator).start();
        }
    }
}
