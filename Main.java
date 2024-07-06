import java.text.DecimalFormat;
import java.util.*;
import java.io.*;
public class Main{
     public static String setDrinkName(Drink drinkToSet){
        String drinkName="";
         if (drinkToSet != null) {
            
            //set names
            switch (drinkToSet.getDrinkCode()){
               case 1:
                  drinkName="Caramel Macchiato";
                  break;
               case 2:
                  drinkName="Vanilla Oatmilk Espresso";
                  break;
               case 3:
                  drinkName="Blonde Vanilla Latte";
                  break;
               case 4:
                  drinkName="Cinnamon Dolce Latte";
                  break;
               case 5:
                  drinkName="Americano";
                  break;
               case 6:
                  drinkName="Capuccino";
                  break;
               case 7:
                  drinkName="Latte";
                  break;
               case 8:
                  drinkName="Mocha";
                  break;
            }
        }
        return drinkName;
    }
    public static String setBeanName(Beans beanToSet){
            String beanName="";
            if (beanToSet != null) {
                    
                    switch (beanToSet.getBeanCode()){
               case 1:
                  beanName="EXCELCSA (300g)";
                  break;
               case 2:
                  beanName="ARABICA (300g)";
                  break;
               case 3:
                  beanName="ROBUSTA (300g)";
                  break;
               case 4:
                  beanName="LIBERICA(300g)";
                  break;
            }
        
    }
    return beanName;
}
   public static void main (String [] args) throws IOException
   {
       
      try {
        //declare packages
         FileReader fr = new FileReader("Orders.txt");
         BufferedReader br = new BufferedReader(fr);
         FileWriter fw = new FileWriter("Report.txt");
         BufferedWriter bw = new BufferedWriter(fw);
         PrintWriter pw = new PrintWriter(bw);
         DecimalFormat df = new DecimalFormat("0.00"  );
         //declare variables
         char drinkSize,paymentType,beanType;
         int drinkCode,quantity,beanCode;
         String name,phoneNum,address,orderId,
                 ewalletUsername,cardNum,cardHolderName;
         boolean fastDelivery=false,bulkBuyer=false;
         //arrays
         int size=20;
         Customer [] customers = new Customer[size];
         Order [] orders = new Order[size];
         Payment [] payments = new Payment[size];
         Scanner input = new Scanner(System.in);
         input.useDelimiter("\n");
         //store all data into array
         int j = 0;
         String inData = null;
         while((inData=br.readLine())!=null)
         {
            //tokenizer to read file
            StringTokenizer st = new StringTokenizer(inData, ";");
            //default payments,by cash
            payments[j]=new Payment();
            //customer details
            name=st.nextToken();
            phoneNum=st.nextToken();
            address=st.nextToken();
            customers[j]=new Customer(name,phoneNum,address);
            //order details
            orderId=st.nextToken();
            String selection=st.nextToken();
            fastDelivery=Boolean.parseBoolean(st.nextToken());

            if(selection.equals("Drink") )
            {
                drinkCode=Integer.parseInt(st.nextToken());
                drinkSize=st.nextToken().charAt(0);
                quantity=Integer.parseInt(st.nextToken());
                orders[j] = new Drink(orderId,fastDelivery,customers[j],drinkCode,drinkSize,quantity);
            }
            else if(selection.equals("Beans"))
            {
                beanType=st.nextToken().charAt(0);
                beanCode=Integer.parseInt(st.nextToken());
                quantity=Integer.parseInt(st.nextToken());
                bulkBuyer=Boolean.parseBoolean(st.nextToken());
                orders[j] = new Beans(orderId,fastDelivery,customers[j],beanType,beanCode,quantity,bulkBuyer);
            }

            //payment type : cash/ewallet/cards
            paymentType=st.nextToken().charAt(0);
            //set payments according to payment type
            if (paymentType=='D')
            {
               cardNum = st.nextToken();
               cardHolderName = st.nextToken();
               //for cards,use setCard
               payments[j].setCard(paymentType,cardNum,cardHolderName);
            }
            else if (paymentType=='E')
            {
               ewalletUsername = st.nextToken();
               //for ewallet,use setEwallet
               payments[j].setEwallet(paymentType,ewalletUsername);
            }
            j++;
         }



         pw.print("============================================================");
         pw.println("\n\t\t\tREPORT INVOICE");
         pw.print("============================================================");
         //total earnings
         //total earnings from drinks
         //total earnings from beans
         double earningsFromBeans=0.0;
         double earningsFromDrinks=0.0;
         for(int i=0;i<size;i++){
            if (orders[i] instanceof Beans){
               earningsFromBeans+= orders[i].calcTotal();
            }
            else if (orders[i] instanceof Drink){
               earningsFromDrinks+= orders[i].calcTotal();
            }
         }


         //total price with tax and discount ( bulk / ewallet / card)


         for(int i=0;i<size;i++) {
            double discount = 0.0;
            double totalPrice = 6; // 6 is for standard delivery
            final double TAX = 0.06;


            if (orders[i] instanceof Drink ) {

               if (((Drink) orders[i]).getQuantity() > 4 ){
                  discount += 0.08;}
               if (payments[i].getPaymentType() == 'E'){
                  discount += 0.05;}

               if (orders[i].isFastDelivery()){
                  totalPrice += 2; // charge for fast delivery is rm2
               }

               totalPrice +=  orders[i].calcTotal() ;
               totalPrice -= (totalPrice*discount);
               totalPrice +=(totalPrice*TAX);
               payments[i].setTotalPrice(totalPrice);
            }
            else if (orders[i] instanceof Beans ) {
               if (((Beans) orders[i]).isBulkBuyer()){
                  discount += 0.1;
               }
               if (payments[i].getPaymentType() == 'E') {
                  discount += 0.05;
               }
               if (orders[i].isFastDelivery()){
                  totalPrice += 2; // charge for fast delivery is rm2
               }

               totalPrice +=  orders[i].calcTotal() ;
               totalPrice -= (totalPrice*discount) + (totalPrice*TAX);
               payments[i].setTotalPrice(totalPrice);
            }
         }


         //calc total bean ordered
         int totalBeanOrdered=0;
         for(int i=0;i< orders.length;i++){
            if (orders[i] instanceof Beans) {
               totalBeanOrdered += ((Beans) orders[i]).getQuantity();
            }
         }

         //calc total drink ordered
         int totalDrinkOrdered=0;
         for(int i=0;i< orders.length;i++){
            if (orders[i] instanceof Drink) {
               totalDrinkOrdered += ((Drink) orders[i]).getQuantity();
            }
         }


         //total orders
         pw.println("\nTotal Earnings          : RM"+ df.format((earningsFromBeans+earningsFromDrinks)));
         pw.println("Earnings from Beans     : RM"+ df.format(earningsFromBeans));
         pw.println("Earnings from Drinks    : RM"+ df.format(earningsFromDrinks));

         pw.println("\nTotal Order(s)          : "+ df.format(totalBeanOrdered+totalDrinkOrdered));
         pw.println("Total Bean(s) Ordered   : "+df.format(totalBeanOrdered));
         pw.println("Total Drink(s) Ordered  : "+df.format(totalDrinkOrdered));


         //most ordered drink(best seller)
         Drink mostOrderedDrink = null;
         int maxQuantity = 0;

         for (int i=0;i<size;i++) {

            if (orders[i] instanceof Drink){
               int iquantity = ((Drink)orders[i]).getQuantity();
               if (iquantity > maxQuantity) {
                  maxQuantity = iquantity;
                  mostOrderedDrink = (Drink)orders[i];
               }
            }
         }
        
         // check if most ordered is found
         
         
            // print the most ordered drink
            pw.println("\nBiggest Drink Order        : " + Main.setDrinkName(mostOrderedDrink) +
                    "\t("+mostOrderedDrink.getQuantity()+ " in one order)");
         

         //least ordered drink (worst seller)
         Drink leastOrderedDrink = null;
         int minQuantity = maxQuantity;

         for (int i=0;i<size;i++) {
            if (orders[i] instanceof Drink){
               int aquantity = ((Drink)orders[i]).getQuantity();
               if (aquantity < minQuantity) {
                  minQuantity = aquantity;
                  leastOrderedDrink = (Drink)orders[i];
               }
            }
         }

         // Check if  found
         pw.println("Smallest Drink Order       : " + Main.setDrinkName(leastOrderedDrink)+
                    "\t("+leastOrderedDrink.getQuantity() + " in one order)");
         
        //most ordered beans(best seller)
         Beans mostOrderedBeans = null;
         maxQuantity = 0;

         for (int i=0;i<size;i++) {

            if (orders[i] instanceof Beans){
               int iquantity = ((Beans)orders[i]).getQuantity();
               if (iquantity > maxQuantity) {
                  maxQuantity = iquantity;
                  mostOrderedBeans = (Beans)orders[i];
               }
            }
         }
         Beans leastOrderedBean = null;
         minQuantity = mostOrderedBeans.getQuantity();

         for (int i=0;i<size;i++) {
            if (orders[i] instanceof Beans){
               int aquantity = ((Beans)orders[i]).getQuantity();
               if (aquantity < minQuantity) {
                  minQuantity = aquantity;
                  leastOrderedBean = (Beans)orders[i];
               }
            }
         }
            pw.println("Biggest Bean Order         : " + Main.setBeanName(mostOrderedBeans)+
                    "\t ("+mostOrderedBeans.getQuantity() + " in one order)");


            pw.println("Smallest Bean Order        : " + Main.setBeanName(leastOrderedBean)+
                    "\t ("+leastOrderedBean.getQuantity() + " in one order)");
         
         String bestPayment = "CASH";
         int paymentCount [] = {0,0,0};
         for(int i = 0; i<size ; i++){
            if (orders[i] instanceof Drink || orders[i] instanceof Beans) {
               if (payments[i].getPaymentType() == 'C') {
                  paymentCount[0] ++;
               }
               else if (payments[i].getPaymentType() == 'E'){
                  paymentCount[1] ++;
               }
               else if (payments[i].getPaymentType() == 'C'){
                  paymentCount[2] ++;
               }
            }
         }


         if (paymentCount[1] > paymentCount[0]) {
            bestPayment = "E-WALLET";
         }
         else if (paymentCount[2] > paymentCount[0]) {
            bestPayment = "CARD";
         }

         pw.println("\nBest Payment Method : " + bestPayment);

         //count deliveries
         int fastDeliveryCount = 0;
         int deliveryCount = 0;
         for(int i=0 ; i<size ; i++){
            if (orders[i] instanceof Beans || orders[i] instanceof Drink) {
               if (orders[i].isFastDelivery())
                  fastDeliveryCount++;
               else
                  deliveryCount++;
            }
         }

         int bulkOrdersCount = 0;
         for (int i = 0 ; i < size ; i++ ){
            if (orders[i] instanceof Beans || orders[i] instanceof Drink) {
               if (orders[i] instanceof Beans) {
                  if (((Beans) orders[i]).isBulkBuyer())
                     bulkOrdersCount++;
               }
            }
         }

         pw.println("\nTotal Delivery Orders     : " + (fastDeliveryCount+deliveryCount));
         pw.println("Normal Delivery Orders    : "+deliveryCount);
         pw.println("Fast Delivery Orders      : "+fastDeliveryCount);
         pw.println("Total Delivery Fee            : RM"+((deliveryCount*6)+(fastDeliveryCount*8)));
         pw.println("Total Additional Fee Charged  : RM"+ (fastDeliveryCount*2));
         pw.println("\nTotal Bulk Orders : " + bulkOrdersCount);

         pw.println("============================================================");

         //find address to alter from name given

         System.out.println(" Change Address? [1/0] : ");
         int search=input.nextInt();
         if (search == 1){
            do{
               int index=0;
               String searchName;
               String newAddress;
               boolean found=false;
               System.out.println("Enter name: ");
               searchName=input.next();
               while((index<j)&&(!found))
               {
                  if(customers[index].getName().equalsIgnoreCase(searchName))
                  {
                     found=true;
                  }
                  else
                  {
                     index++;
                  }
               }
               if(found)
               {
                  System.out.print("The name is found!");
                  System.out.print("\nEnter new address: ");
                  newAddress=input.next();
                  customers[index].setAddress(newAddress);
                  pw.println("\n\nUPDATED:");
                  pw.print(customers[index].toString());
               }
               else
               {

                  System.out.println("The name is not found...");
               }

               System.out.println("Continue? [1/0] : ");
               search = input.nextInt();
            }while(search == 1);
         }

            //print raw orders for checking
         pw.println("\n\n========================================");
         pw.println("\t\tRAW ORDERS");
         pw.println("========================================");
         for(int i=0;i<size;i++) {
            if (orders[i] instanceof Drink)
            {

               pw.println("\n\n-----------------Drinks----------------------");
               pw.println(orders[i].toString()+
                       "\n\n TOTAL PRICE : RM"+ df.format(payments[i].getTotalPrice())+
                       payments[i].toString());
               pw.println("\n\n++++++++++++++++++++++++++++++++++++++++++++");
            }

            if (orders[i] instanceof Beans)
            {

               pw.println("\n\n----------------Beans----------------------");
               pw.println(orders[i].toString()+
                       "\n\n TOTAL PRICE : RM"+df.format(payments[i].getTotalPrice())+
                       payments[i].toString());
               pw.println("\n\n+++++++++++++++++++++++++++++++++++++++++++");
            }
         }


         br.close();
         pw.close();
        }
    
      
      catch (FileNotFoundException fnfe)
      {System.out.println("Error: File not found - " +fnfe.getMessage());}
      catch (IOException io)
      {System.out.println("Error reading or writing file - " + io.getMessage());}
      catch (Exception e)
      {
          System.out.println("An error occurred - "+e.getMessage());
          e.printStackTrace();
      }
   }
}
