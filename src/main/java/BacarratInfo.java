import java.util.ArrayList;

import java.io.Serializable;

public class BacarratInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	int currentBalance;
	//booleans used from the client to let the server know this information..
	boolean chosePlayer = false;
	boolean choseBanker = false;
	boolean choseTie = false;
	double currentBet = 0;
	boolean isPlayingAgain = false;
	boolean isPlayingTheGame = false;
	
	//Function used to calculate hand points in the server
	public int pointsForCurrentDeck(ArrayList<Card> thisHand) {
		int totalPoints = BacarratGameLogic.handTotal(thisHand);
		
		return totalPoints;
	}
	
	//String used to identify a card in the server
	public String indentifyCard(Card thisCard) {
		String theCard = "";
		theCard += thisCard.value;
		theCard += " Of ";
		theCard += thisCard.suite;
		
		return theCard;
	}
	
	//String used to show the clients bet decision, depending on what they chose
	public String displayBetDecision() {
		String betDecision = " on the ";
		
		if(choseBanker == true) {
			betDecision += "Banker.";
		} else if (chosePlayer == true) {
			betDecision += "Player.";
		} else if (choseTie) {
			betDecision += "Tie.";
		}
		return betDecision;
	}
	
	//A displayDeckMessage to show the cards of the player
	public String displayPlayerDeckMessage(ArrayList<Card> thisHand) {
		String theString = "Players hand is currently : \n";
		int i = 0;
		for(Card cards : thisHand) {
			theString += cards.value;
			theString += " Of ";
			theString += cards.suite;
			if(i < thisHand.size() - 1) {
				theString += " and ";
			}
			i++;
		}
		theString += "\nPlayers Points: " + BacarratGameLogic.handTotal(thisHand);
		return theString;
	}
	
	//A displayDeckMessage to show the cards of the banker
	public String displayBankersDeckMessage(ArrayList<Card> bankersHand) {
		String theString = "Bankers hand is currently : \n";
		int i = 0;
		for(Card cards : bankersHand) {
			theString += cards.value;
			theString += " Of ";
			theString += cards.suite;
			if(i < bankersHand.size() - 1) {
				theString += " and ";
			}
			i++;
		}
		theString += "\nBanker Points: " + BacarratGameLogic.handTotal(bankersHand);
		return theString;
	}	
	
}
