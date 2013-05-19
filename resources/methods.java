package resources;/*
@author: Hot Dog
*/

import org.powerbot.core.script.util.Random;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class methods {

    public static Timer animation_timer = new Timer(2200);

    public static void moveMouseOfScreen () {
        Mouse.move(Random.nextInt(764, 800), Random.nextInt(550, 600));
    }

    public static boolean interact (final SceneObject target, final String action)
    {
        if (target == null) {
            return false;
        }

        if (Calculations.distanceTo(target)  > 7){
            Walking.walk(target);
            return false;
        }

        if (!target.isOnScreen()){
            Camera.turnTo(target);
            return false;
        }

        if (target.click(false)){
            if (!Menu.contains(action)){
                moveMouseOfScreen();
                return false;
            }

            if (Menu.select(action)){
                return true;
            }
        }
        return false;
    }

    public static boolean interact (final Item target, final String action)
    {
        if (target == null) {
            return false;
        }

        if (target.getWidgetChild().interact(action)){
            return true;
        }

        return false;
    }

    public static boolean useItemOnObject (final Item item, final SceneObject obj)
    {
        if (item == null || obj == null){
            return false;
        }
        if (interact(items.maple_logs(), "Use")){
            if (obj.click(true)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkBurn () {
        return Inventory.isFull() && Inventory.contains(ids.maple_logs) && !vars.need_to_burn;
    }

    public static boolean stopBurn () {
        return !Inventory.contains(ids.maple_logs) && vars.need_to_burn;
    }

    public static void resetTimer () {
        if (!Players.getLocal().isIdle()){
            animation_timer.reset();
        }
    }



}
