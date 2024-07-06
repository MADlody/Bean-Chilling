public abstract class Order {
   protected String orderId;
   protected boolean fastDelivery;
   protected Customer customer;

   public Order(){
      orderId="INVALID";
      fastDelivery=false;
      customer=new Customer();
   }

   public Order(String orderId,boolean fastDelivery,Customer customer){
      this.orderId=orderId;
      this.fastDelivery=fastDelivery;
      this.customer=customer;
   }

   public String getOrderId() {
      return orderId;
   }

   public boolean isFastDelivery() {
      return fastDelivery;
   }

   public abstract double calcTotal();

   @Override
   public String toString() {
      return "\n | Id " + orderId + " | Fast Delivery: " + fastDelivery + " |\n"
              + customer ;

   }
}
