public class Beans extends Order {
   private char beanType;
   private int beanCode;
   private int quantity;
   private boolean bulkBuyer;


   public Beans(String orderId,boolean fastDelivery,Customer customer,
                char beanType,int beanCode,int quantity,boolean bulkBuyer){
      super(orderId, fastDelivery, customer);
      this.beanType=beanType;
      this.beanCode=beanCode;
      this.quantity=quantity;
      this.bulkBuyer=bulkBuyer;
   }
   public int getBeanCode() {
      return beanCode;
   }
   public int getQuantity() {
      return quantity;
   }

   public boolean isBulkBuyer() {
      return bulkBuyer;
   }

   public double calcTotal(){
      double total=0.0;
      if (beanType=='W') {
         switch (beanCode) {
            case 1:
               total += 250.00;
               break;
            case 2:
               total += 150.00;
               break;
            case 3:
               total += 300.00;
               break;
            case 4:
               total += 200.00;
               break;
         }
      }
      else if (beanType=='G') {
         switch (beanCode) {
            case 1:
               total += 270.00;
               break;
            case 2:
               total += 170.00;
               break;
            case 3:
               total += 320.00;
               break;
            case 4:
               total += 220.00;
               break;
         }
      }
      total=total*quantity;
      return total;
   }

   @Override
   public String toString() {
      return super.toString()+
              "\n\nBeans Order" +
              "\n[ Type:" + beanType +
              ", Code:" + beanCode +
              ", Quantity:" + quantity +
              " ]";
   }
}
