// maybe todo: ai
// maybe todo: whether it creates a product or not
// todo: create product

public class FoeShop  {
  public String name; 
  public ArrayList <Product> products; // currently dummy
  public ArrayList <Product> itemOnWindow;
  public int level; // currently dummy
  public int experience; // currently dummy
  public int budget;
  public int maxNum;
  
  public FoeShop (String name, int budget, int maxNum) {
    this.name = name;
    this.budget = budget;
    this.itemOnWindow = new ArrayList <Product> ();
    this.maxNum = maxNum;
    randomInitialization (maxNum);
  }
  
  // create (manNum) items randomly
  public void randomInitialization (int maxNum) {
    int vogue;
    int price;
    for (int i = 0; i < maxNum; i++) {
      vogue = (int)random(97, 103);
      price = (int)random(1, 100);
      
      itemOnWindow.add (new Product((char)vogue, price, 0, 50));
    }
  }
  
  // create random product 
  public void createRandomProduct (int num) {
    int r = (int)random(this.maxNum);
    
    createProduct (num, itemOnWindow.get(r));
  }
  
  // create num
  public void createProduct (int num, Product p) {
    if (this.canCreateProduct (num, p)) {
      this.budget -= p.createProduct (num);
    }
  }
  
  // checks if it can create num product or not
  public boolean canCreateProduct (int num, Product p) {
    if (p.estimation(num) > this.budget) {
      return false;
    }
    return true;
  }
  
  public void buy (Product p) {
    this.budget += p.price;
  }
}