public class Payment {
   private char paymentType;
   private String cardNum, cardHolderName, ewalletUsername;
   private double totalPrice;

   public Payment() {
      paymentType = 'C';
      cardNum = " ";
      cardHolderName = "";
      ewalletUsername = "";
   }

   public void setCard(char paymentType, String cardNum, String cardHolderName) {
      this.paymentType = paymentType;
      this.cardNum = cardNum;
      this.cardHolderName = cardHolderName;
   }

   public void setEwallet(char paymentType, String ewalletUsername) {
      this.paymentType = paymentType;
      this.ewalletUsername = ewalletUsername;
   }

   public char getPaymentType() {
      return paymentType;
   }


   public double getTotalPrice() {
      return totalPrice;
   }

   public void setTotalPrice(double totalPrice) {
      this.totalPrice = totalPrice;
   }

   @Override
   public String toString() {
      String display="";
      if (paymentType == 'C') {
         display =
                 "\n\n Payment Type: Cash";
      }
      else if (paymentType == 'E') {
         display=
                 "\n\n Payment Type: E-Wallet" +
                 "\n Username      : " + ewalletUsername;
      }
      else if (paymentType == 'D') {
         display=
                 "\n\n Payment Type: Card" +
                 "\n Card Number      : " + cardNum +
                 "\n Card Holder Name : " + cardHolderName;
      }
      return display;
   }
}

