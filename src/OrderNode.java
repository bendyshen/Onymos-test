class OrderNode {
    final Order order;
    volatile OrderNode next;

    OrderNode(Order order) {
        this.order = order;
    }
}