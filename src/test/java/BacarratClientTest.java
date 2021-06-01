import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BacarratClientTest {
	
	BacarratGame thisGame = new BacarratGame();
	BacarratDealer thisDealer = thisGame.theDealer;
	ArrayList<Card> testDeck = new ArrayList<Card>();
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;
	Card card1;
	Card card2;
	
	boolean checkValue(int a){
		if(a >= 1 && a <= 13) {
			return true;
		}
		return false;
	}
	
	boolean checkSuits(String a){
		if(a == "Spades" || a == "Hearts" || a == "Diamonds" || a == "Clubs") {
			return true;
		}
		return false;
	}
	
	boolean cardCheck(Card a, Card b) {
		if(a.value == b.value && b.suite == a.suite) {
			return true;
		}
		return false;
	}
	
	boolean checkHandTotal(int total) {
		if(total < 10) {
			return true;
		}
		return false;
	}
	
	/* Testing BacarratDealer functions */
	
	//Testing deck before generating
	@Test
	@Order(1)
	void generateDeckTest() {
		testDeck = thisDealer.deck;
		assertEquals(0, testDeck.size(), "Deck not created of right size");
	}
	
	//Testing deck after generating deck
	@Test
	@Order(2)
	void generateDeckTest1() {
		thisGame.theDealer.generateDeck();
		testDeck = thisDealer.deck;
		assertEquals(52, testDeck.size(), "Deck not created of right size");
	}
	
	// Testing the first card of dealHand()
	@Test
	@Order(3)
	void dealHandTest() {
		card1 = new Card("", -1);
		playerHand = new ArrayList<Card>();
		thisGame.theDealer.generateDeck();
		playerHand = thisDealer.dealHand();
		int i = 0;
		for(Card cards : playerHand) {
			if(i == 0) {
				card1 = cards;
			}	
		}
		assertEquals(true, checkValue(card1.value), "Not correct value set to card");
		assertEquals(true, checkSuits(card1.suite), "Not correct suite set to card");
	}
	
	// Testing the second card of dealHand()
	@Test
	@Order(4)
	void dealHandTest1() {
		card2 = new Card("", -1);
		playerHand = new ArrayList<Card>();
		thisDealer.generateDeck();
		playerHand = thisDealer.dealHand();
		int i = 0;
		for(Card cards : playerHand) {
			if(i == 1) {
				card2 = cards;
			}
			i++;
		}
		assertEquals(true, checkValue(card2.value), "Not correct value set to card");
		assertEquals(true, checkSuits(card2.suite), "Not correct suite set to card");
		
	}
	
	// Testing the drawOne function
	@Test
	@Order(5)
	void drawOneTest() {
		thisDealer.generateDeck();
		Card drawFirst = thisDealer.drawOne();
		assertEquals(true, checkValue(drawFirst.value), "Not correct value set to card");
		assertEquals(true, checkSuits(drawFirst.suite), "Not correct suite set to card");
	}
	
	@Test
	@Order(6)
	void drawOneTest1() {
		thisDealer.generateDeck();
		Card drawSecond = thisDealer.drawOne();
		assertEquals(true, checkValue(drawSecond.value), "Not correct value set to card");
		assertEquals(true, checkSuits(drawSecond.suite), "Not correct suite set to card");
	}
	
	//Testing the shuffling of deck and comparing cards before and after
	// the shuffle
	@Test
	@Order(7)
	void shuffleDeckTest() {
		thisDealer.generateDeck();
		Card firstCheck = thisDealer.drawOne();
		assertEquals(true, checkValue(firstCheck.value), "Not correct value set to card");
		assertEquals(true, checkSuits(firstCheck.suite), "Not correct suit set to card");
		thisDealer.generateDeck();
		thisDealer.shuffleDeck();
		Card secondCheck = thisDealer.drawOne();
		assertEquals(false, cardCheck(secondCheck, firstCheck), "Not shuffled properly");
	}
	
	// checking the deck size function
	@Test
	@Order(8)
	void deckSizeTest() {
		thisDealer.generateDeck();
		assertEquals(52, thisDealer.deckSize(), "Not correct size");
	}
	
	/* Testing BacarratGame Functions */
	
	// checking the calculation of scores
	@Test
	@Order(9)
	void evaluateWinningsTest() {
		thisGame.currentBet = 50000;
		thisGame.chosePlayer = true;
		thisGame.playerWon = true;
		assertEquals(50000, thisGame.evaluateWinnings(), "Money won not calculated correctly");
	}
	
	@Test
	@Order(10)
	void evaluateWinningsTest1() {
		thisGame.currentBet = 50000;
		thisGame.choseBanker = true;
		thisGame.playerWon = true;
		assertEquals(-50000, thisGame.evaluateWinnings(), "Money won not calculated correctly");
	}
	
	@Test
	@Order(11)
	void evaluateWinningsTest2() {
		thisGame.currentBet = 50000;
		thisGame.choseTie = true;
		thisGame.playerWon = true;
		assertEquals(50000, thisGame.evaluateWinnings(), "Money won not calculated correctly");
	}
	
	@Test
	@Order(12)
	void evaluateWinningsTest3() {
		thisGame.currentBet = 50000;
		thisGame.choseBanker = true;
		thisGame.playerWon = false;
		assertEquals(50000, thisGame.evaluateWinnings(), "Money won not calculated correctly");
	}
	
	/* Test Card class functions */
	
	// checking the constructor and getCardValue function in Card class
	@Test
	@Order(13)
	void cardConstructorTest() {
		Card newCard = new Card("Hearts", 5);
		assertEquals("Hearts", newCard.suite, "Constructor not working");
		assertEquals(5, newCard.value, "Constructor not working");
		assertEquals(5, newCard.getCardValue(newCard), "Constructor not working");
	}
	
	/* Testing BacarratGameLogic Functions */
	
	@Test
	@Order(14)
	void handTotalTest() {
		playerHand = new ArrayList<Card>();
		thisDealer.generateDeck();
		thisDealer.shuffleDeck();
		playerHand = thisDealer.dealHand();
		int total = BacarratGameLogic.handTotal(playerHand);
		assertEquals(true, checkHandTotal(total), "Hand Total not calculated right");
	}

	// Runs a for loop to test multiple times the result of who
	// won 
	@Test
	@Order(15)
	void whoWonTest() {
		for(int i = 0; i < 3; i++) {
			playerHand = new ArrayList<Card>();
			thisDealer.generateDeck();
			thisDealer.shuffleDeck();
			playerHand = thisDealer.dealHand();
			
			bankerHand = new ArrayList<Card>();
			thisDealer.generateDeck();
			thisDealer.shuffleDeck();
			bankerHand = thisDealer.dealHand();
			
			int totalPlayer = BacarratGameLogic.handTotal(playerHand);
			int totalBanker = BacarratGameLogic.handTotal(bankerHand);
			
			if(totalPlayer > totalBanker) {
				assertEquals("The player won", BacarratGameLogic.whoWon(playerHand, bankerHand), 
						"winner not decided correctly");
			}
			else if(totalPlayer < totalBanker) {
				assertEquals("The dealer won", BacarratGameLogic.whoWon(playerHand, bankerHand), 
						"winner not decided correctly");
			}
			else if(totalPlayer == totalBanker) {
				assertEquals("It was a draw", BacarratGameLogic.whoWon(playerHand, bankerHand), 
						"winner not decided correctly");
				
			}
		}
	}
	
	// Checks if banker needs to draw a card for a default playerCard value
	@Test
	@Order(16)
	void evaluateBankerDrawTest() {
		bankerHand = new ArrayList<Card>();
		thisDealer.generateDeck();
		thisDealer.shuffleDeck();
		bankerHand = thisDealer.dealHand();
		
		int totalBanker = BacarratGameLogic.handTotal(bankerHand);
		Card playerCard = new Card("",-1);
		
		if(totalBanker <= 2) {
			assertEquals(true, BacarratGameLogic.evaluateBankerDraw(bankerHand, playerCard), 
					"Banker Draw not evaluated correctly" );
		}
	}
	
	// Checks if banker needs to draw a card for a certain playerCard value
	@Test
	@Order(17)
	void evaluateBankerDrawTest1() {
		bankerHand = new ArrayList<Card>();
		thisDealer.generateDeck();
		thisDealer.shuffleDeck();
		bankerHand = thisDealer.dealHand();
		
		int totalBanker = BacarratGameLogic.handTotal(bankerHand);
		Card playerCard = new Card("Diamonds", 8);
		
		if(totalBanker <= 2) {
			assertEquals(true, BacarratGameLogic.evaluateBankerDraw(bankerHand, playerCard), 
					"Banker Draw not evaluated correctly" );
		}
		else {
			assertEquals(false, BacarratGameLogic.evaluateBankerDraw(bankerHand, playerCard), 
					"Banker Draw not evaluated correctly" );
		}
	}
	
	// Checks if the Player needs to draw or not
	@Test
	@Order(18)
	void evaluatePlayerDrawTest() {
		playerHand = new ArrayList<Card>();
		thisDealer.generateDeck();
		thisDealer.shuffleDeck();
		playerHand = thisDealer.dealHand();
		
		int totalPlayer = BacarratGameLogic.handTotal(playerHand);
		
		if(totalPlayer <= 5) {
			assertEquals(true, BacarratGameLogic.evaluatePlayerDraw(playerHand), 
					"Banker Draw not evaluated correctly" );
		}
		else {
			assertEquals(false, BacarratGameLogic.evaluatePlayerDraw(playerHand), 
					"Banker Draw not evaluated correctly" );
		}
		
	}
	
	
}
