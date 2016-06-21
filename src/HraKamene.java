import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class HraKamene {

	// unfortunatelly only squares
	int riadky = 4;
	int stlpce = 4;
	int[][] pole = new int[riadky][stlpce];
	long timeOfStart = 0;
	long actualTime = 0;

	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	public Set random() {
		Random rng = new Random();
		Set<Integer> generated = new LinkedHashSet<Integer>();
		while (generated.size() < riadky * stlpce - 1) {
			Integer next = rng.nextInt(riadky * stlpce - 1) + 1;
			generated.add(next);
		}
		return generated;
	}

	public int[] getRandomValues() {
		Iterator<Integer> i = random().iterator();
		int[] poleCisel = new int[riadky * stlpce - 1];
		Integer p = null;
		int poradie = 0;
		while (i.hasNext()) {
			p = i.next();
			poleCisel[poradie] = p;
			poradie++;
		}
		return poleCisel;
	}

	private int[][] vytvoreniePola() {
		int pocetPolicok = (riadky * stlpce) - 1;
		int cislaVPoli = 0;
		int[] array = getRandomValues();
		for (int row = 0; row < riadky; row++) {
			for (int column = 0; column < stlpce; column++) {
				if (cislaVPoli < pocetPolicok) {
					pole[column][row] = array[cislaVPoli];
					cislaVPoli++;
				} else {
					pole[column][row] = 0;
				}
			}
		}
		return pole;
	}

	void vykresleniePola() throws WrongInputForStart {
		for (int row = 0; row < riadky; row++) {
			for (int column = 0; column < stlpce; column++) {
				if (pole[column][row] < 10 && pole[column][row] > 0) {
					System.out.print(" " + pole[column][row] + " ");
				} else if (pole[column][row] < riadky * stlpce && pole[column][row] > 0) {
					System.out.print(pole[column][row] + " ");
				} else if (pole[column][row] == 0) {
					System.out.print("  ");
				}
			}
			System.out.println();
		}

		actualTime = System.currentTimeMillis();
		int riadokEmpty = 0;
		int stlpecEmpty = 0;
		for (int row = 0; row < riadky; row++) {
			for (int column = 0; column < stlpce; column++) {
				if (pole[column][row] == 0) {
					riadokEmpty = row;
					stlpecEmpty = column;
				}
			}
		}

		System.out.println("Enter movement keys (W / S / A / D) or for STOP the game enter 'END':");
		String userInput = readLine();
		if (userInput.equals("END")) {
			System.out.println("Game was ended. Too shame for you.");
			actualTime = System.currentTimeMillis();
			long timeOfPlaying = (actualTime - timeOfStart) / 1000;
			System.out.println("Èas hrania je: " + timeOfPlaying + " sek.");
			System.out.println("-------------------------");
			System.exit(0);
		} else if (userInput.equals("W")) {
			System.out.println("posun hore");
		} else if (userInput.equals("S")) {
			System.out.println("posun dole");
		} else if (userInput.equals("A")) {
			System.out.println("posun dolava");
		} else if (userInput.equals("D")) {
			System.out.println("posun doprava");
		} else {
			throw new WrongInputForStart("Zly vstup pre end alebo posun.");
		}
	}

	public void newGameStarted() throws WrongInputForStart {
		System.out.println("For starting game enter 'NEW':");
		String newGame = readLine();
		if (newGame.equals("NEW")) {
			timeOfStart = System.currentTimeMillis();
			System.out.println("New game started. GOOD LUCK.");
			System.out.println("-------------------------");
			do {
				vykresleniePola();
				long timeOfPlaying = (actualTime - timeOfStart) / 1000;
				System.out.println("Èas hrania je: " + timeOfPlaying + " sek.");
				System.out.println("-------------------------");
			} while (true);
		} else {
			throw new WrongInputForStart("Zly vstup pre start game.");
		}
	}

	public static void main(String[] args) {
		HraKamene game = new HraKamene();
		game.vytvoreniePola();
		game.newGameStarted();
	}
}
