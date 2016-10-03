import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class FujiwaraNoOmise extends PApplet {

GameManager gm;

public void setup () {
  
  
  gm = new GameManager (10, 1800, 1800, 4, 2);
  // frequency of people
  // frequency of vogue change
  // frequency of foe buying item
  // experience required to level up
  // level required to expand item
  
}

public void draw() {
  gm.updateThis();
}

public void keyReleased () {
  gm.clickThis();
}
public class Controller {
  public int howmany = 0;
  public int selection = 0;
  public int colorSelection = 0;
  
  public Controller () {
  }
  
  public void selectThis () {
    setHowmany();
    selectionChange();
    setColorSelection();
  }
  
  public void setHowmany () {
    if (keyCode == UP) {
      howmany++;
    }
    else if (keyCode == DOWN) {
      if (howmany > 0) {
        howmany--;
      }
    }
    else if (keyCode == RIGHT) {
      howmany += 10;
    }
    else if (keyCode == LEFT) {
      if (howmany <= 10) {
        howmany = 0;
      }
      else {
        howmany -= 10;
      }
    }
  }
  
  public void selectionChange () {
    if (key == 'q') {
      selection = 0;
      howmany = 0;
      colorSelection = 0;
    }
    else if (key == 'w') {
      selection = 1;
      howmany = 0;
      colorSelection = 0;
    }
    else if (key == 'e') {
      selection = 2;
      howmany = 0;
      colorSelection = 0;
    }
    else if (key == 'a') {
      selection = 3;
      howmany = 0;
      colorSelection = 0;
    }
    else if (key == 's') {
      selection = 4;
      howmany = 0;
      colorSelection = 0;
    }
    else if (key == 'd') {
      selection = 5;
      howmany = 0;
      colorSelection = 0;
    }
  }
  
  public void setColorSelection () {
    if (key == '1') {
      colorSelection = 0;
    }
    else if (key == '2') {
      colorSelection = 1;
    }
    else if (key == '3') {
      colorSelection = 2;
    }
    else if (key == '4') {
      colorSelection = 3;
    }
    else if (key == '5') {
      colorSelection = 4;
    }
    else if (key == '6') {
      colorSelection = 5;
    }
  }
  
  public void enterBehavior (boolean isBuyingMode, MyShop ms) {
    if (keyCode == ENTER) {
      if (isBuyingMode) {
        ms.createSome(ms.itemOnWindow.get(selection), howmany);
      }
      else {
        ms.createProduct(howmany * 200, new Product ((char)(colorSelection + 97), howmany, 0, 100));
      }
    }
  }
}
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
public class Graphic {
  int vogueColors[];
  String shopname = "";
  public int howmany = 0;
  
  public Graphic () {
    vogueColors = new int[6];
    initializeVogue();
  }
  
  public void initializeVogue () {
    initializeColor();
  }
  
  public void initializeColor () {
    vogueColors[0] = color(219, 36, 91);
    vogueColors[1] = color(142, 0, 204);
    vogueColors[2] = color(40, 96, 163);
    vogueColors[3] = color(224, 234, 58);
    vogueColors[4] = color(71, 234, 126);
    vogueColors[5] = color(86, 99, 143);
  }
  
  // start screen
  public void drawInitialScreen (String s) {
    background(255);
    textAlign(CENTER, CENTER);
    fill(0);
    text("Enter your shop's name", 300, 100);
    fill(255);
    rect(200, 170, 200, 20);
    
    
  }
  
  // called on draw
  public void drawThis (ArrayList<Person> people, MyShop ms, FoeShop[] foes, int howmany, int selection, int colorselection) {
    background(242, 216, 223);
    drawMyShop ();
    drawRoad ();
    drawFoes();
    drawTextField(ms, selection, howmany, colorselection);
    drawPeople(people);
    drawMyShopItems(ms, selection);
    drawFoeShopsItem (foes);
    drawLevel(ms);
  }
  
  public void drawLevel (MyShop ms) {
    textSize(15);
    textAlign(LEFT, LEFT);
    text("LV: " + ms.level, 60, 70);
  }
  
  public void drawMyShop () {
    fill(255);
    stroke(100);
    rect(50, 50, 200, 200, 10, 10, 10, 10);
  }
  
  public void drawRoad () {
    fill (100, 100);
    stroke(100);
    rect (275, 0, 75, 400);
  }
  
  public void drawFoes () {
    fill (50, 20);
    rect (375, 20, 200, 75, 10, 10, 10, 10);
    rect (375, 115, 200, 75, 10, 10, 10, 10);
    rect (375, 210, 200, 75, 10, 10, 10, 10);
    rect (375, 305, 200, 75, 10, 10, 10, 10);
  }
  
  public void drawTextField (MyShop ms, int selection, int howmany, int colorselection) {
    fill(255);
    stroke(0);
    
    rect (50, 275, 200, 100, 10, 10, 10, 10);
    strokeWeight(1);
    fill(0);
    textAlign (LEFT, LEFT);
    text ("Budget: $" + ms.budget, 60, 300);
    line (50, 305, 250, 305);
    
    textSize (15);
    if (selection < ms.itemOnWindow.size()) {
      text("Buy?:  " + howmany, 70, 330);

      text("Estimated Value: " + (int)(howmany * ms.itemOnWindow.get(selection).price * (50 - ms.level) / 50.0f), 70, 360);
      
    }
    else if (selection < ms.maxNum) {
      text("Price?    " + howmany, 50, 320);
      text("Estimated Value: " + howmany * 200, 50, 340);
      createButtons(colorselection);
    }
  }
  
  public void createButtons (int colorSelection) {
    for (int i = 0; i < 6; i++) {
      fill(vogueColors[i]);
      if (colorSelection == i) {
        strokeWeight(2);
      }
      else {
        strokeWeight(1);
      }
      rect (10 / 3.0f + 50 + 33 * i, 350, 30, 20);
    }
    strokeWeight(1);
  }
  
  public void drawPeople (ArrayList<Person> people) {
    for (int i = 0; i < people.size(); i++) {
      fill(vogueColors[(int)people.get(i).sense - 97]);
      stroke(0);
      ellipse (people.get(i).position.x, people.get(i).position.y, 6, 6);
    }
  }
  
  public void drawMyShopItems (MyShop ms, int emphasis) {
    for (int i = 0; i < 3; i++) {
      if (i == emphasis && emphasis < ms.maxNum) {
        strokeWeight(3);
      }
      else {
        strokeWeight(1);
      }
      
      if (ms.itemOnWindow.size() > i) {
        fill (vogueColors[(int)ms.itemOnWindow.get(i).type - 97]);
      }
      else {
        fill(200);
      }
      
      rect (50 + 5 + 65 * i, 80, 60, 70, 5, 5, 5, 5);
      
      if (ms.itemOnWindow.size() <= i && ms.maxNum <= i) {
        fill(100);
        line (56 + 65 * i, 81, 114 + 65 * i, 149);
      }
      else {
        fill(0);
        textAlign(CENTER, CENTER);
        textSize(20);
        try {
          text(ms.itemOnWindow.get(i).howmanyleft, 85 + 65 * i, 105);
          text("$" + ms.itemOnWindow.get(i).price, 85 + 65 * i, 135);
        } catch (Exception e) {
        }
      }
    }
    
    
    for (int i = 0; i < 3; i++) {
      if (i + 3 == emphasis && emphasis < ms.maxNum) {
        strokeWeight(3);
      }
      else {
        strokeWeight(1);
      }
      if (ms.itemOnWindow.size() > i + 3) {
        fill (vogueColors[(int)ms.itemOnWindow.get(i + 3).type - 97]);
      }
      else {
        fill(200);
      }
      
      rect (50 + 5 + 65 * i, 155, 60, 70, 5, 5, 5, 5);
      
      if (ms.itemOnWindow.size() <= i + 3 && ms.maxNum <= i + 3) {
        fill(100);
        line (56 + 65 * i, 156, 114 + 65 * i, 224);
      }
      else {
        fill(0);
        textAlign(CENTER, CENTER);
        textSize(20);
        try {
          text("" + ms.itemOnWindow.get(i + 3).howmanyleft, 85 + 65 * i, 190);
          text("$" + ms.itemOnWindow.get(i).price, 85 + 65 * i, 135);
        } catch (Exception e) {
        }
      }
    }
    strokeWeight(1);
  }
  
  public void drawFoeShopsItem (FoeShop[] foes) {
    for (int i = 0; i < foes.length; i++) {
      textAlign(LEFT,LEFT);
      fill(0);
      textSize(13);
      text("$" + foes[i].budget, 380, 35 + 95 * i);
      for (int p = 0; p < 4; p++) {
        if (foes[i].itemOnWindow.size() > p) {
          fill (vogueColors[(int)foes[i].itemOnWindow.get(p).type - 97]);
        }
        else {
          fill(200);
        }
      
        rect (379 + 49 * p, 40 + 95 * i, 45, 50, 5, 5, 5, 5);
      
        if (foes[i].itemOnWindow.size() <= p) {
          fill(100);
          line (380 + 49 * p, 41 + 95 * i, 424 + 49 * p, 89 + 95 * i);
        }
        else {
          fill(0);
          textAlign(CENTER, CENTER);
          textSize(15);
          text("" + foes[i].itemOnWindow.get(p).howmanyleft, 401.5f + 49 * p, 55 + 95 * i);
          text("$" + foes[i].itemOnWindow.get(p).price, 401.5f + 49 * p, 75 + 95 * i);
        }
      }
    }
  }
}
public class GameManager {
  Model m;
  Graphic g;
  Controller c;
  
  public GameManager (int frequency, int voguefrequency, int foecreationFreq, int exNum, int lvNum) {
    m = new Model (frequency, voguefrequency, foecreationFreq, exNum, lvNum);
    g = new Graphic ();
    c = new Controller ();
  }
  
  public void updateThis() {
    if (m.state == 0) {
      g.drawInitialScreen("a");
    }
    else if (m.state == 1) {
      m.calledOnUpdate();
      g.drawThis(m.people, m.myshop, m.foes, c.howmany, c.selection, c.colorSelection);
    }
  }
  
  public void clickThis () {
    c.selectThis();
    if (c.selection < m.myshop.itemOnWindow.size()) {
      c.enterBehavior (true, m.myshop);
    }
    else if (c.selection < m.myshop.maxNum) {
      c.enterBehavior (false, m.myshop);
    }
  }
  
}
public class Model {
  public ArrayList<Person> people;
  public MyShop myshop;
  public FoeShop foes[];
  public Vogue vogue;
 
  public int frequency;
  public int voguefrequency;
  public int foecreationFreq;
  
  // intial = 0, game = 1
  public int state = 1;
  
  public Model (int frequency, int voguefrequency, int foecreationFreq, int exNum, int lvNum) {
    this.people = new ArrayList<Person> ();
    this.vogue = new Vogue((char)PApplet.parseInt(random(97, 103)));
    this.foes = new FoeShop[4];
    
    // this is temporary one
    Product p = new Product('a', 10, 0, 100);
    myshop = new MyShop("Ryu's department", p, 1000, exNum, lvNum);
    // till here
    
    createFoeShop();
    peopleinitialization (30);
    
    
    this.frequency = frequency;
    this.voguefrequency = voguefrequency;
    this.foecreationFreq = foecreationFreq;
  }
  
  // create n people
  public void peopleinitialization (int n) {
    for (int i = 0; i < 30; i++) {
      this.people.add(new Person(vogue.mostSold, foes, myshop));
    }
  }
  
  // create foe shop
  public void createFoeShop () {
    for (int i = 1; i < 5; i++) {
      this.foes[i - 1] = new FoeShop(("Shop " + i), 1000, i);
    }
  }

  public void calledOnUpdate () {
    if (frameCount % this.frequency == 0) {
      createPeople();
    }
    
    if (frameCount % this.voguefrequency == 0) {
      this.vogue.alterVogue();
    }
    
    if (frameCount % this.foecreationFreq == 0) {
      int r;
      for (int i = 0; i < this.foes.length; i++) {
        r = (int)random(100);
        this.foes[i].createRandomProduct(r);
      }
    }
    processPeople();
  }
  
  // character creation
  public void createMyShop (String name) {
    
  }
  
  // create people
  public void createPeople () {
    this.people.add(new Person(vogue.mostSold, foes, myshop));
  }
  
  public void processPeople () {
    for (int i = 0; i < people.size(); i++) {
      people.get(i).walkToward(myshop, foes);
      if (people.get(i).hasBought) {
        people.remove(i);
      }
    }
  }
}
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
    if (level <= 50) {
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
    if (canAfford((int)(p.price * num * (50 - level) / 50.0f))) {
      budget -= (int)(p.price * num * (50 - level) / 50.0f);
      p.createProduct(num);
    }
  }
  
  public boolean canAfford (int price) {
    return price <= this.budget;
  }
}
public class Person  {
  public char sense;
  public PVector position;
  public PVector velocity;
  public int whichShop;
  public Product whichproduct;
  public float speed;
  
  final float MYSHOP = 200;
  final float FOESHOP1 = 57.5f;
  final float FOESHOP2 = FOESHOP1 + 95;
  final float FOESHOP3 = FOESHOP2 + 95;
  final float FOESHOP4 = FOESHOP3 + 95;
  final float YCOORDINATES[] = {FOESHOP1, FOESHOP2, FOESHOP3, FOESHOP4, MYSHOP};
  
  public boolean doesStartRotate = false;
  
  // 0 = cheep, 1 = attraction, 2 = random
  public int tendency = 0;
 
  public boolean hasBought = false;
  
  public Person (char vogue, FoeShop[] foes, MyShop ms) {
    whichproduct = ms.itemOnWindow.get(0);
    float r = random(1);
    // can be altered
    if (r >= 0.7f) {
      tendency = 0;
    }
    else if (r > 0.4f) {
      tendency = 1;
    }
    else {
      tendency = 2;
    }
    
    initialize (vogue);
    
    float x = random (280, 345);
    int y = PApplet.parseInt(random(2));
    this.position = new PVector (x, y * 400);
    this.speed = random(1, 3);

    if (y == 0) {
      velocity = new PVector (0, speed);
    }
    else {
      velocity = new PVector (0, -speed);
    }
    
    setAim(foes, ms);
  }
  
  // initializer
  public void initialize (char vogue) {
    
    float r = random(1);
    
    if (r <= 0.5f) {
      this.sense = vogue;
    }
    else {
      int r2 = (int)random(6);
      if (r2 == 0) {
        this.sense = 'a';
      }
      else if (r2 == 1) {
        this.sense = 'b';
      }
      else if (r2 == 2) {
        this.sense = 'c';
      }
      else if (r2 == 3) {
        this.sense = 'd';
      }
      else if (r2 == 4) {
        this.sense = 'e';
      }
      else if (r2 == 5) {
        this.sense = 'f';
      }
    }
  }
  
  public void setAim (FoeShop[] foes, MyShop ms) {
    ArrayList<Integer> possibleAimShop = new ArrayList<Integer>();
    ArrayList<Product> possibleAim = new ArrayList<Product>();
    
    for (int i = 0; i < foes.length; i++) {
      for (int p = 0; p < foes[i].itemOnWindow.size(); p++) {
        if (foes[i].itemOnWindow.get(p).type == this.sense) {
          possibleAimShop.add(i);
          possibleAim.add(foes[i].itemOnWindow.get(p));
        }
      }
    }
    
    for (int i = 0; i < ms.itemOnWindow.size(); i++) {
      if (ms.itemOnWindow.get(i).type == this.sense) {
        possibleAimShop.add(4);
        possibleAim.add(ms.itemOnWindow.get(i));
      }
    }
    
    if (possibleAim.size() == 0) {
      hasBought = true;
    }
    else {
      if (tendency == 0) {
        int bestPrice = 1000000000;
        for (int i = 0; i < possibleAim.size(); i++) {
          if (bestPrice > possibleAim.get(i).price) {
            whichShop = possibleAimShop.get(i);
            whichproduct = possibleAim.get(i);
          }
        }
      }
      else if (tendency == 1) {
        int bestAttraction = 100000000;
        for (int i = 0; i < possibleAim.size(); i++) {
          if (bestAttraction > possibleAim.get(i).attraction) {
            whichShop = possibleAimShop.get(i);
            whichproduct = possibleAim.get(i);
          }
        }
      }
      else {
        possibleAimShop.clear();
        possibleAim.clear();
        
        for (int i = 0; i < foes.length; i++) {
          for (int j = 0; j < foes[i].itemOnWindow.size(); j++) {
            possibleAim.add(foes[i].itemOnWindow.get(j));
            possibleAimShop.add(i);
          }
        }
        
        for (int i = 0; i < ms.itemOnWindow.size(); i++) {
          for (int j = 0; j < 1; j++) {
            possibleAim.add(ms.itemOnWindow.get(i));
            possibleAimShop.add(4);
          }
        }
        
        int r = (int) random(possibleAim.size());
        
        whichproduct = possibleAim.get(r);
        whichShop = possibleAimShop.get(r);
      }
    }
  }
  
  // walk to the shop: called every frame
  public void walkToward (MyShop ms, FoeShop foes[]) {
    if (!doesStartRotate) {
      this.position.add(this.velocity);
      if (abs(this.position.y - YCOORDINATES[whichShop]) < speed) {
        //this.position.y = YCOORDINATES[whichShop];
        doesStartRotate = true;
        
        if (whichShop != 4) {
          this.velocity.x = speed / 1.5f;
          this.velocity.y = 0;
        }
        else {
          this.velocity.x = -speed / 1.5f;
          this.velocity.y = 0;
        }
      }
    }
    else {
      this.position.add(this.velocity);
      if (whichShop != 4 && abs(this.position.x - 380) < speed) {
        this.position.x = 380;
        buyProduct (ms, foes);
        hasBought = true;
      }
      else if (whichShop == 4 && abs(this.position.x - 245) < speed) {
        this.position.x = 245;
        buyProduct (ms, foes);
        hasBought = true;
      }
    }
  }
  
  // buy product
  public void buyProduct (MyShop ms, FoeShop[] foes) {
    if (!whichproduct.canBuy()) {
      whichproduct.defame();
    }
    else {
      if (whichShop == 4) {
        ms.getExperience(1);
        ms.buy(whichproduct);
      }
      else {
        foes[whichShop].buy(whichproduct);
      }
      
      whichproduct.buyThis();
    }
  }
}
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
public class Vogue {
  public char mostSold;
  
  // contructor
  public Vogue (char mostSold) {
    this.mostSold = mostSold;
  }
  
  // todo
  public void alterVogue () {
    int rand = (int)random (6);
    if (rand == 0) {
      mostSold = 'a';
    }
    else if (rand == 1) {
      mostSold = 'b';
    }
    else if (rand == 2) {
      mostSold = 'c';
    }
    else if (rand == 3) {
      mostSold = 'd';
    }
    else if (rand == 4) {
      mostSold = 'e';
    }
    else if (rand == 5) {
      mostSold = 'f';
    }
  }
}
  public void settings() {  size (600, 400); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "FujiwaraNoOmise" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
