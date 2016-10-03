
GameManager gm;

void setup () {
  size (600, 400);
  
  gm = new GameManager (1, 1800, 300, 1, 1);
  // frequency of people
  // frequency of vogue change
  // frequency of foe buying item
  // experience required to level up
  // level required to expand item
  
}

void draw() {
  gm.updateThis();
}

void keyPressed () {
  gm.clickThis();
}