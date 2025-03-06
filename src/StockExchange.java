class StockExchange {
    private static final int MAX_TICKERS = 1024;
    private final OrderBook[] orderBooks = new OrderBook[MAX_TICKERS];
    private final String[] tickers = new String[MAX_TICKERS];


    private int getHashIndex(String ticker) {
        int hash = Math.abs(ticker.hashCode()) % MAX_TICKERS;
        return hash;
    }

    public int findTickerIndex(String ticker) {
        int index = getHashIndex(ticker);


        while (tickers[index] != null) {
            if (tickers[index].equals(ticker)) {
                return index;
            }
            index = (index + 1) % MAX_TICKERS;
        }

        tickers[index] = ticker;
        orderBooks[index] = new OrderBook();
        return index;
    }

    public void addOrder(Order order) {
        int index = findTickerIndex(order.ticker);
        if (orderBooks[index] != null) {
            orderBooks[index].addOrder(order);
        }
    }

    public void matchOrders() {
        for (int i = 0; i < MAX_TICKERS; i++) {
            if (orderBooks[i] != null) {
                synchronized (orderBooks[i]) {
                    orderBooks[i].matchOrders();
                }
            }
        }
    }
}