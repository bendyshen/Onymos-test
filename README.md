# Onymos-test

# Stock Order Matching System

## Overview
This project implements a **stock order matching system** that supports **1,024 stock tickers** and allows for efficient order matching using a **lock-free linked list structure**. It ensures that:

- **Buy orders are stored in descending order (highest price first).**
- **Sell orders are stored in ascending order (lowest price first).**
- **Orders are matched efficiently in O(n) time complexity.**
- **No dictionaries, maps, or equivalent data structures are used.**
- **Multi-threading is supported for concurrent order execution.**

## Features
- **Efficient Ticker Matching**: Uses a **custom hash-based indexing** for fast `ticker` lookup (O(1) average complexity).
- **Order Insertion with Sorting**: Ensures that buy orders are sorted in descending order and sell orders are sorted in ascending order.
- **Order Matching**: Matches buy and sell orders with **price priority and time priority**.
- **Partial and Complete Order Execution**: Updates order quantities accordingly.

## Data Structures
### 1. `Order`
Represents an individual stock order.
```java
class Order {
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
```

### 2. `OrderNode`
A node in the linked list for storing orders.
```java
class OrderNode {
    final Order order;
    volatile OrderNode next;

    OrderNode(Order order) {
        this.order = order;
    }
}
```

### 3. `OrderBook`
Maintains buy and sell orders for a specific stock.
```java
class OrderBook {
    private volatile OrderNode buyOrders = null;
    private volatile OrderNode sellOrders = null;

    public void addOrder(Order order) {
        // Implementation of sorted insertion
    }

    public synchronized void matchOrders() {
        // Implementation of order matching
    }
}
```

### 4. `StockExchange`
Handles order books for multiple tickers without using dictionaries or maps.
```java
class StockExchange {
    private static final int MAX_TICKERS = 1024;
    private final OrderBook[] orderBooks = new OrderBook[MAX_TICKERS];
    private final String[] tickers = new String[MAX_TICKERS];
    
    public int findTickerIndex(String ticker) {
        // Custom hash-based indexing
    }
    
    public void addOrder(Order order) {
        // Adding orders to the correct OrderBook
    }
    
    public void matchOrders() {
        // Iterating through all tickers to match orders
    }
}
```

## How It Works
### Order Insertion (O(n))
- **Buy orders** are inserted in **descending order** based on price.
- **Sell orders** are inserted in **ascending order** based on price.
- If multiple orders have the same price, **earlier orders take priority**.

### Order Matching (O(n))
- The highest-priced **buy** order is matched with the lowest-priced **sell** order.
- If the buy price is **greater than or equal to** the sell price, a trade occurs.
- Orders are updated after each trade:
  - If an order is fully executed, it is removed.
  - If an order is partially executed, its remaining quantity is updated.
- **Matching continues until no further trades are possible.**


## Installation & Execution
### **1. Compile & Run**
```sh
javac Simulator.java
java Simulator
```
### **2. Multi-threaded Simulation**
- The system spawns **4 worker threads** to randomly generate buy/sell orders.
- Orders are continuously added and matched in real-time.

## Future Improvements
- Implement **better collision handling** for ticker indexing.
- Introduce **order expiration handling**.
- Optimize **multi-threading** with lock-free queues.

## Conclusion
This implementation successfully:
- Supports **1,024 stock tickers** without using maps/dictionaries.  -
-  Efficiently **matches orders** in **O(n)** complexity.  
- Ensures **price & time priority** for fair order execution.  
- Provides **multi-threading support** for high-performance trading.  



