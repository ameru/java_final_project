
import java.util.*;

public class finalProjRehash {

		public static void main(String[] args) {
			
			int players;
			Scanner input = new Scanner(System.in);
			
			//first round begins here:
			printBoard printboard = new printBoard();
			System.out.println(printboard.printTable());
			
			//initialize wallet:
			double userWallet[] = new double[6];
			for(int pos = 0; pos < userWallet.length; pos++)
			{
				userWallet[pos] = 100;
			}
			
			//begin while loop:
			String nextRound = "";
			do
			{	
				for(int roundpos = 0; roundpos >= 0; roundpos++)
				{
				
				//for first round only:
				if(roundpos == 0)
				{
					System.out.print("Welcome to the craps table! Would you like to play this round? Enter \"yes\" to play or \"no\" to quit. ");
					nextRound = input.next().trim().toString();
				}
				
				//for all following rounds:
				else
				{
					System.out.print("Would you like to play again? Enter \"yes\" to play again or \"no\" to quit. ");
					nextRound = input.next().trim().toString();
					if (nextRound.equalsIgnoreCase("yes"))
							System.out.println(printboard.printTable());
				} 
				
				if (nextRound.equalsIgnoreCase("yes"))
				{
					
				
				//ensures that players are not over 6:	
				do
				{
					System.out.print("How many players will be playing? (maximum of 6) ");
			
					players = input.nextInt();
				} while (players > 6);
			
				
					//initializes first bets:
					double[] firstBet = new double[players];
					for(int pos = 0; pos < firstBet.length; pos ++)
					{
						System.out.print("Player " + (pos +1) +  ", how much would you like to bet? ");
						firstBet[pos] = input.nextDouble();
					}
			
				
				
					System.out.print("Which player will be rolling the dice? ");
					int playerTurn = input.nextInt();
					System.out.print("Player " + playerTurn + ", please enter \"roll\" to roll the dice. ");
					String discard = input.next();
					System.out.println();
			
			
			
					//dice are rolled:
					Random rand = new Random();
					int die1 = rand.nextInt(6) + 1;
					int die2 = rand.nextInt(6) + 1;
					int sumPassRound = die1 + die2;
					System.out.println("You rolled: " + die1 + " + " + die2 + " = " + sumPassRound);
					System.out.println("You rolled " + sumPassRound);
			
			
			
					//automatic win:
					if(sumPassRound == 7 || sumPassRound == 11)
					{
						System.out.println("Congratulations! You won.");
						for(int pos = 0; pos < firstBet.length; pos++)
						{
							userWallet[pos] += firstBet[pos];
							System.out.println("Player " + (pos+1) + ", you won $" + firstBet[pos] + "!");
							System.out.println("You currently have $" + userWallet[pos] + " in your wallet.");
							System.out.println();
						}
					}
					
					else
					{
						//automatic loss:
						if(sumPassRound == 2 || sumPassRound == 3 || sumPassRound == 12)
						{
							System.out.println("Sorry, you have lost.");
							for(int pos = 0; pos < firstBet.length; pos++)
							{
								userWallet[pos] -= firstBet[pos];
								System.out.println("Player " + (pos+1) + ", you have lost $" + firstBet[pos] + ".");
								System.out.println("You currently have $" + userWallet[pos] + " in your wallet.");
								System.out.println();
							}
								roundpos++;
								continue;
						}
						
						else
						{
							//procedure to point round:
							System.out.println("You rolled " + sumPassRound + ". This is your point value as we proceed to the point round.");
							System.out.println();
						}
				
				
				
						//initializing second bet:
						double[] secondBet = new double[players];
						for(int pos = 0; pos < secondBet.length; pos++)
						{
							secondBet[pos] = 0;
						}
						char[] forOrAgainst = new char[players];
			
						for(int pos = 0; pos < secondBet.length; pos ++)
						{
							System.out.print("Player " + (pos +1) +  ", how much would you like to bet in the point round? (enter 0 if not) ");
							secondBet[pos] = secondBet[pos] + input.nextDouble();
							System.out.print("Player " + (pos +1) +  ", would you like to bet for or against the point value? Enter 'f' for for and 'a' for against. ");
							forOrAgainst[pos] = input.next().charAt(0);
						}
			
						
						
						System.out.println();
						int sumPointRound = 0;
						int die3;
						int die4;
						
						//odds of rolling various sums in point round (account for discrepancies in winnings/losses):
						double[] odds = new double[11]; //{2,1.5,1.2,1.2,1.5,2}
						odds[0] = 1; //odds on a 2
						odds[1] = 1; //odds on a 3
						odds[2] = 2; //odds on a 4
						odds[3] = 1.5; //odds on a 5
						odds[4] = 1.2; //odds on a 6
						odds[5] = 1; //odds on a 7
						odds[6] = 1.2; //odds on an 8
						odds[7] = 1.5; //odds on a 9
						odds[8] = 2; //odds on a 10
						odds[9] = 1; //odds on an 11
						odds[10] = 1; //odds on a 12
			
			
						
						while (sumPointRound != 7 && sumPointRound != sumPassRound)
						{
							die3 = rand.nextInt(5) + 1;
							die4 = rand.nextInt(5) + 1;
							sumPointRound = die3 + die4;
							System.out.println("You rolled: " + die3 + " + " + die4 + " = " + sumPointRound);
						} 
		
						
						//point round loss:
						if(sumPointRound == 7)
						{
							System.out.println("If you bet for the point, you have lost. If you bet against the point, you have won!");
							for(int pos = 0; pos < secondBet.length; pos++)
							{
								if(forOrAgainst[pos] == 'f')
								{
									userWallet[pos] -= (firstBet[pos] + secondBet[pos]);
									System.out.println("Player " + (pos+1) + ", you have lost $" + (firstBet[pos] + secondBet[pos]) + ".");
									System.out.println("You currently have $" + userWallet[pos] + " in your wallet.");
									System.out.println();
								}
								else
								{
									userWallet[pos] += (firstBet[pos] + (secondBet[pos] * (1/(odds[sumPassRound - 2]))));
									System.out.println("Player " + (pos+1) + ", you have won $" + (firstBet[pos] + (secondBet[pos] * 
											(1/(odds[sumPassRound - 2])))) + "! This is less than the amount "
												+ "you bet because of the odds of rolling each different number.");
									System.out.println("You currently have $" + userWallet[pos] + " in your wallet.");
									System.out.println();
								}
							}
						}
						
						
						
						//point round win:
						if(sumPointRound == sumPassRound)
						{
							System.out.println("If you bet for the point, you have won! If you bet against the point, you have lost.");
							for(int pos = 0; pos < secondBet.length; pos++)
							{
								if(forOrAgainst[pos] == 'a')
								{
									userWallet[pos] -= (firstBet[pos] + secondBet[pos]);
									System.out.println("Player " + (pos+1) + ", you have lost $" + (firstBet[pos] + secondBet[pos]) + ".");
									System.out.println("You currently have $" + userWallet[pos] + " in your wallet.");
									System.out.println();
								}
								else
								{
									userWallet[pos] += (firstBet[pos] + (secondBet[pos]* odds[sumPointRound-2]));
									System.out.println("Player " + (pos+1) + ", you have won $" + (firstBet[pos] + 
											(secondBet[pos] * odds[sumPointRound-2])) + "! This is more than the amount you bet because "
												+ "of the odds of rolling each different number.");		
									System.out.println("You currently have $" + userWallet[pos] + " in your wallet.");
									System.out.println();
			
								} 	
							}
						}
					}
				}
			else
				break;
				
				}
			} while(nextRound.equalsIgnoreCase("yes"));
		}
	}
