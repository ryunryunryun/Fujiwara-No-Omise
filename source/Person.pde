public class Person  {
  public char sense;
  public PVector position;
  public PVector velocity;
  public int whichShop;
  public Product whichproduct;
  public float speed;
  
  final float MYSHOP = 200;
  final float FOESHOP1 = 57.5;
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
    if (r >= 0.7) {
      tendency = 0;
    }
    else if (r > 0.4) {
      tendency = 1;
    }
    else {
      tendency = 2;
    }
    
    initialize (vogue);
    
    float x = random (280, 345);
    int y = int(random(2));
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
    
    if (r <= 0.5) {
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
          this.velocity.x = speed / 1.5;
          this.velocity.y = 0;
        }
        else {
          this.velocity.x = -speed / 1.5;
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