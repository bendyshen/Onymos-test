class OrderBook {
    private volatile OrderNode buyOrders = null;
    private volatile OrderNode sellOrders = null;

    public void addOrder(Order order) {
        OrderNode newNode = new OrderNode(order);
        // If it's a buy order, sort the orders in descendant order in terms of price
        if (order.type == Order.Type.BUY) {

            if (buyOrders == null || buyOrders.order.price < order.price) {
                newNode.next = buyOrders;
                buyOrders = newNode;
                return;
            }

            OrderNode prev = buyOrders, curr = buyOrders.next;
            while (curr != null && curr.order.price >= order.price) {
                prev = curr;
                curr = curr.next;
            }
            prev.next = newNode;
            newNode.next = curr;
        } else {
            // If it's a sell order, sort the orders in ascendant order in terms of price
            if (sellOrders == null || sellOrders.order.price > order.price) {
                newNode.next = sellOrders;
                sellOrders = newNode;
                return;
            }

            OrderNode prev = sellOrders, curr = sellOrders.next;
            while (curr != null && curr.order.price <= order.price) {
                prev = curr;
                curr = curr.next;
            }
            prev.next = newNode;
            newNode.next = curr;
        }
    }

    public void matchOrders() {
        while (buyOrders != null && sellOrders != null) {
            if (buyOrders == null || sellOrders == null) return;

            Order buyOrder = buyOrders.order;
            Order sellOrder = sellOrders.order;


            if (!buyOrder.ticker.equals(sellOrder.ticker)) {
                return;
            }

            if (buyOrder.price >= sellOrder.price) {
                int tradeQuantity = Math.min(buyOrder.quantity, sellOrder.quantity);
                double tradePrice = sellOrder.price;

                System.out.println("Matched: " + buyOrder.ticker + " " + tradeQuantity + " @ " + tradePrice);


                if (buyOrder.quantity > tradeQuantity) {
                    buyOrder.quantity -= tradeQuantity;
                    if (sellOrders != null) sellOrders = sellOrders.next;
                } else if (sellOrder.quantity > tradeQuantity) {
                    sellOrder.quantity -= tradeQuantity;
                    if (buyOrders != null) buyOrders = buyOrders.next;
                } else {
                    if (buyOrders != null) buyOrders = buyOrders.next;
                    if (sellOrders != null) sellOrders = sellOrders.next;
                }
            } else {
                break;
            }
        }
    }
}