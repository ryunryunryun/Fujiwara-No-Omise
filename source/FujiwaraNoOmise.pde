GameManager gm;

void setup () {
  size (600, 400);
  
  gm = new GameManager (10, 1800, 1800, 4, 2);
  // frequency of people
  // frequency of vogue change
  // frequency of foe buying item
  // experience required to level up
  // level required to expand item
  
}

void draw() {
  gm.updateThis();
}

void keyReleased () {
  gm.clickThis();
}