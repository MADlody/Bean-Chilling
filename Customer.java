public class Customer {
   private String name,phoneNum,address;

   public Customer(){
      name="INVALID";
      phoneNum="INVALID";
      address="INVALID";
   }
   public Customer(String name,String phoneNum,String address){
      this.name=name;
      this.phoneNum=phoneNum;
      this.address=address;
   }
   public void setAddress(String address)
   {
       this.address=address;
   }

   public String getName(){return name;}
   public String toString(){
      return  "\n Name         : " +name+
              "\n Phone Number : " +phoneNum+
              "\n Address      : "+address;
   }
}
   