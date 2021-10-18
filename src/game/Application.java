package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.*;
import game.enemies.AldrichTheDevourer;
import game.enemies.YhormTheGiant;
import game.enemies.Skeleton;
import game.enums.Status;
import game.grounds.*;
import game.items.FogDoor;
import game.weapons.StormRuler;

/**
 * The main class for the Design O' Souls game.
 *
 */
public class Application {

	/**
	 * Runs the program for the Design O' Souls game.
	 * @param args the command line arguments for the main method
	 */
	public static void main(String[] args) {

		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(),
				new Valley(), new Cemetery());

		// Map for Profane Capital
		List<String> map = Arrays.asList(
				"..++++++..+++...........................++++......+++.................+++.......",
				"........+++++..............................+++++++.................+++++........",
				"...........+++.......................................................+++++......",
				"....c...................................................................++......",
				"....................................................c....................+++....",
				"....................c.......+.............................................+++...",
				".............................+++.......++++.....................................",
				".............................++.......+..................c...++++...............",
				"......................................c......................+++++++............",
				"..................................###___###...................+++...............",
				"..................................#_______#......................+++............",
				"...........++.....................#_______#.......................+.............",
				".........+++......................#_______#........................++...........",
				"............+++....c..............####_####..........................+......c...",
				"..............+....................................c.................++.........",
				"..............++......................c..........................++++++.........",
				"............+++...................................................++++..........",
				"+..................................................................++...........",
				"++...+++.........................................................++++...........",
				"+++......................................+++...............c........+.++........",
				"++++.......++++.......c.................++.........................+....++......",
				"#####___#####++++......................+...............................+..+.....",
				"_..._....._._#.++......................+...................................+....",
				"...+.__..+...#+++................................c..........................+...",
				"...+.....+._.#.+.....+++++...++..............................................++.",
				"___.......___#.++++++++++++++.+++.............................................++");
		GameMap gameMap = new GameMap(groundFactory, map);
		world.addGameMap(gameMap);

		// Map for Anor Londo
		List<String> map2 = Arrays.asList(
				"..........+++................................+..........+++.......",
				"........+..++........................................+++++........",
				"...........+++.........................................+++++......",
				"....c.....................................................++......",
				"...........................................................+++....",
				"....................c.......................................+++...",
				".....................................++++.........................",
				"...c.......................................c...++++...............",
				"...............................................+++..++............",
				"................................................+++...............",
				"...................##___######################....................",
				"...........++......#..........._...#.........#....................",
				"...................#...#....#........+..#.._.#....................",
				"............c......#._.......................#................c...",
				"...................#...#................#....#.........++.........",
				"..............++...#........#........__......#....................",
				"..............+....##___######################....................",
				".....................................................++...........",
				"..................................................................");
		GameMap gameMap2 = new GameMap(groundFactory, map2);
		world.addGameMap(gameMap2);

		// Create a bonfire manager
		BonfireManager bonfireManager = new BonfireManager();

		// Create the Firelink Shrine bonfire
		Bonfire bonfire1 = new Bonfire("Firelink Shrine's Bonfire", gameMap.at(38, 11));
		bonfire1.addCapability(Status.BONFIRE_LIT);
		bonfire1.addCapability(Status.LAST_BONFIRE);
		bonfire1.setBonfireManager(bonfireManager);
		gameMap.at(38, 11).setGround(bonfire1);

		// Create the Anor Londo bonfire
		Bonfire bonfire2 = new Bonfire("Anor Londo's Bonfire", gameMap2.at(38, 0));
		bonfire2.setBonfireManager(bonfireManager);
		gameMap2.at(38, 0).setGround(bonfire2);


		// Create and place the player actor, with their initial location being at the bonfire
		Actor player = new Player("Unkindled", '@', 200, bonfireManager);
		// Start the player on top of the Firelink Shrine bonfire
		world.addPlayer(player, gameMap.at(38, 11));

		// Uncomment this line to start the player near the Firelink Shrine fog door
//		world.addPlayer(player, gameMap.at(35, 23));
		// Uncomment this line to start the player near Aldrich
//		world.addPlayer(player, gameMap2.at(30, 9));
		// Uncomment this line to start near the Anor Londo bonfire and some chests
//		world.addPlayer(player, gameMap2.at(36, 0));


		// Create and place the skeletons
		gameMap.at(10, 19).addActor(new Skeleton(gameMap.at(10, 19)));
		gameMap.at(14, 7).addActor(new Skeleton(gameMap.at(14, 7)));
		gameMap.at(25, 11).addActor(new Skeleton(gameMap.at(25, 11)));
		gameMap.at(27, 17).addActor(new Skeleton(gameMap.at(27, 17)));
		gameMap.at(35, 14).addActor(new Skeleton(gameMap.at(35, 14)));
		gameMap.at(54, 11).addActor(new Skeleton(gameMap.at(54, 11)));
		gameMap.at(54, 17).addActor(new Skeleton(gameMap.at(54, 17)));

		// Create and place Yhorm the Giant
		gameMap.at(6, 25).addActor(new YhormTheGiant(gameMap.at(6, 25)));

		// Create and place Storm Ruler next to Yhorm
		gameMap.at(7, 25).addItem(new StormRuler());

		// Create and place chests
		gameMap2.at(12, 12).addActor(new Chest(gameMap2.at(12, 12)));
		gameMap2.at(13, 12).addActor(new Chest(gameMap2.at(13, 12)));
		gameMap2.at(14, 13).addActor(new Chest(gameMap2.at(14, 13)));
		gameMap2.at(10, 16).addActor(new Chest(gameMap2.at(10, 16)));
		gameMap2.at(32, 9).addActor(new Chest(gameMap2.at(32, 9)));
		gameMap2.at(36, 2).addActor(new Chest(gameMap2.at(36, 2)));
		gameMap2.at(36, 3).addActor(new Chest(gameMap2.at(36, 3)));
		gameMap2.at(38, 3).addActor(new Chest(gameMap2.at(38, 3)));

		// Create and place Aldrich the Devourer
		gameMap2.at(30, 13).addActor(new AldrichTheDevourer(gameMap2.at(30, 13)));

		// Create fog door for Profane Capital
		FogDoor fogDoor1 = new FogDoor();
		fogDoor1.addAction(new MoveActorAction(gameMap2.at(35, 0), "to Anor Londo"));
		gameMap.at(35, 25).addItem(fogDoor1);

		// Create fog door for Anor Londo
		FogDoor fogDoor2 = new FogDoor();
		fogDoor2.addAction(new MoveActorAction(gameMap.at(35, 25), "to Profane Capital"));
		gameMap2.at(35, 0).addItem(fogDoor2);

		// Run the game
		world.run();
	}
}
