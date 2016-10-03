public class MyShop {
  public String name;
  public ArrayList <Product> products;
  public ArrayList <Product> itemOnWindow;
  public int level = 1;
  public int experience;
  public int budget;
  
  public int maxNum;
  
  private int expNum = 1;
  private int lvNum = 1;
  
  // constructor
  public MyShop (String name, Product product, int budget, int expNum, int lvNum) {
    this.name = name;
    this.products = new ArrayList<Product>();
    this.itemOnWindow = new ArrayList <Product> ();
    this.budget = budget;
    this.itemOnWindow.add(product);
    this.expNum = expNum;
    this.lvNum = lvNum;
  }
  
  // returns if surviving or not
  public boolean isSurvivingYet () {
    if (this.budget >= 0) {
      return true;
    }
    return false;
  }
  
  // create product with $investment
  public void createProduct (int investment, Product newProduct) {
    if (canAfford(investment)) {
      this.budget -= investment;
      this.itemOnWindow.add(newProduct);
    }
  }
  
  // create some products
  public void createSomeProduct (int num, Product p) {
    this.budget -= p.createProduct (num);
  }
  
  // todo: whether this shop can create item or not
  public boolean canCreateProduct () {
    return false;
  }
  
  // earn experience
  public void getExperience (int e) {
    if (level < 50) {
      this.experience += e;
    }
    levelup();
    increaseItemSpace();
  }
  
  // level up!!!!
  public void levelup () {
    if (experience == expNum) {
      level++;
      experience = 0;
    }
  }
  
  // you can have more item
  public void increaseItemSpace () {
    if (experience == 0 && level % lvNum == 0) {
      if (maxNum < 6) {
        maxNum++;
      }
    }
  }
  
  public void buy (Product p) {
    budget += p.price;
  }
  
  public void createSome(Product p, int num) {
    if (canAfford((int)(p.price * num * (100 - level)/ 100.0))) {
      budget -= (int)(p.price * num * (100 - level) / 100.0);
      p.createProduct(num);
    }
  }
  
  public boolean canAfford (int price) {
    return price <= this.budget;
  }
}