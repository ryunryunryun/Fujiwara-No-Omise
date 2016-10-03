public class Graphic {
  color vogueColors[];
  String shopname = "";
  public int howmany = 0;
  
  public Graphic () {
    vogueColors = new color[6];
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

      text("Estimated Value: " + (int)(howmany * ms.itemOnWindow.get(selection).price * (50 - ms.level) / 50.0), 70, 360);
      
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
      rect (10 / 3.0 + 50 + 33 * i, 350, 30, 20);
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
          text("" + foes[i].itemOnWindow.get(p).howmanyleft, 401.5 + 49 * p, 55 + 95 * i);
          text("$" + foes[i].itemOnWindow.get(p).price, 401.5 + 49 * p, 75 + 95 * i);
        }
      }
    }
  }
}