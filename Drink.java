public class Drink extends Order{
   private char drinkSize;
   private int drinkCode,quantity;

   public Drink(String orderId,boolean fastDelivery,Customer customer,
                int drinkCode,char drinkSize, int quantity){
      super(orderId, fastDelivery, customer);
      this.drinkCode=drinkCode;
      this.drinkSize=drinkSize;
      this.quantity=quantity;
   }
   public int getDrinkCode() {
      return drinkCode;
   }
   public int getQuantity() {
      return quantity;
   }

   public double calcTotal(){
      double total=0.0;
      switch (drinkCode) {
         case 1:
            total += 18.00;
            break;
         case 2:
            total += 20.00;
            break;
         case 3:
            total += 19.00;
            break;
         case 4:
            total += 22.00;
            break;
         case 5:
            total += 12.00;
            break;
         case 6:
            total += 17.00;
            break;
         case 7:
            total += 15.00;
            break;
         case 8:
            total += 16.00;
            break;
         default:
            total = 0.0;
            break;
         }
         switch(drinkSize){
            case 'S':
               total += 1.00;
               total=total*quantity;
               break;
            case 'M':
               total += 2.00;
               total=total*quantity;
               break;
            case 'L':
               total += 3.00;
               total=total*quantity;
               break;
            default:
               total=1.00;
               total=total*quantity;
               break;
       }
       return total;
   }

   @Override
   public String toString() {
      return  super.toString()+
              "\n\nDrink Order:" +
              " \n[ Size: " + drinkSize +
              ", Code:" + drinkCode +
              ", Quantity:" + quantity +
              " ]";
   }
}
