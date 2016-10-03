public class Product {
  public char type;
  public int price;
  // affects user's decision : if conflicting randomly chosen
  public int attraction;
  public int howmanyleft;
  
  public Product (char type, int price, int attraction, int howmanyleft) {
    this.type = type;
    this.price = price;
    this.attraction = attraction;
    this.howmanyleft = howmanyleft;
  }
  
  // create num products and returns how much it takes
  public int createProduct (int num) {
    howmanyleft += num;
    
    return price * num;
  }
  
  // returns estimation if num is created
  public int estimation (int num) {
    return price * num;
  }
  
  // checks if there are any left
  public boolean canBuy () {
    return howmanyleft > 0;
  }
  
  // boooooo
  public void defame () {
    this.attraction -= 10;
  }
  
  public void buyThis () {
    this.howmanyleft --;
  }
}