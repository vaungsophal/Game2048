 public class Autoplay
 {
     // Used for the recursive autoplay method to determine
     // the total number of moves
     private static int autoMoveCount = 0;
     
     // Moves up, left, down, right until it loses and returns the final score
     public static int circlePlay(Game game)
     {
         while(!(game.lost()))
         {
             System.out.println(game);
             System.out.println("Moving up");
             game.act(Location.UP);
             System.out.println(game);
             System.out.println("Moving left");
             game.act(Location.LEFT);
             System.out.println(game);
             System.out.println("Moving down");
             game.act(Location.DOWN);
             System.out.println(game);
             System.out.println("Moving right");
             game.act(Location.RIGHT);
         }
         System.out.println(game);
         return game.getScore();
     }
 
     // Moves randomly and returns the final score
     public static int randomPlay(Game game)
     {
         double num;
         while(!(game.lost()))
         {
             num = Math.random();
 
             if(num > .5)
                 if(num > .75)
                 {
                     System.out.println("Acting up");
                     game.act(Location.UP);
                 }
                 else
                 {
                     System.out.println("Acting left");
                     game.act(Location.LEFT);
                 }
             else
                 if(num > .25)
                 {
                     System.out.println("Acting down");
                     game.act(Location.DOWN);
                 }
                 else
                 {
                     System.out.println("Acting right");
                     game.act(Location.RIGHT);
                 }
 
             System.out.println(game);
         }
 
         System.out.println("GAME LOST");
         return game.getScore();
     }
 
 
     // Moves up, left, up, left until it can't move
     // then goes right, if still can't move goes down
     public static int cornerPlay(Game game)
     {
         while(!(game.lost()))
         {
             while(game.canMove(Location.RIGHT) || game.canMove(Location.UP) ||
                     game.canMove(Location.LEFT))
             {
                 while(game.canMove(Location.UP) || game.canMove(Location.LEFT))
                 {
                     System.out.println("Acting up");
                     game.act(Location.UP);
                     System.out.println(game);
 
                     System.out.println("Acting left");
                     game.act(Location.LEFT);
                     System.out.println(game);
                 }
                 System.out.println("Acting right");
                 game.act(Location.RIGHT);
                 System.out.println(game);
             }
             System.out.println("Acting down");
             game.act(Location.DOWN);
             System.out.println(game);
 
         }
 
         return game.getScore();
     }
 
 
     public static boolean recursivePlay(Game game, Game original, int tile, boolean upFirst)
     {
         System.out.println(game);
 
         if(game.won(tile))
             return true;
 
         Game lastTurn = game.clone();
         autoMoveCount += 1;
         
         // Undos the the entire game every 6000 moves
         if(tile <= 2048 && autoMoveCount % 6000 == 0)
         {
             System.out.println("Reseting the game");
             game = original.clone();
             System.out.println(game);
         }
 
         // Stops automatically after 150000 moves because
         // most games take only 2000-3000
         if(autoMoveCount >= 15000)
         {
             System.out.println("***** Time Limit Reached *****");
             return true;
         }
 
 
         if(upFirst)
         {
             game.act(Location.UP);
             if(! (game.lost() || game.equals(lastTurn)))
                 if(recursivePlay(game.clone(), original, tile, !upFirst))
                     return true;
 
             game.act(Location.LEFT);
             if(! (game.lost() || game.equals(lastTurn)))
                 if(recursivePlay(game.clone(), original, tile, !upFirst))
                     return true;
 
         }
         else
         {
             game.act(Location.LEFT);
             if(! (game.lost() || game.equals(lastTurn)))
                 if(recursivePlay(game.clone(), original, tile, !upFirst))
                     return true;
 
             game.act(Location.UP);
             if(! (game.lost() || game.equals(lastTurn)))
                 if(recursivePlay(game.clone(), original, tile, !upFirst))
                     return true;
         }
 
 
         game.act(Location.RIGHT);
         if(! (game.lost() || game.equals(lastTurn)))
             if(recursivePlay(game.clone(), original, tile, false))
                 return true;
 
         game.act(Location.DOWN);
         if(! (game.lost() || game.equals(lastTurn)))
             if(recursivePlay(game.clone(), original, tile, false))
                 return true;
 
         System.out.println("**** Undo ****");
         return false;
     }
 
     public static int getAutoMoveCount()
     {
         return autoMoveCount;
     }
     
     public static void setAutoMoveCount(int value)
     {
         autoMoveCount = value;
     }
     
 }