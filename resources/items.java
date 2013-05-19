package resources;/*
@author: Hot Dog
*/

import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

public class items {
    public static Item maple_logs () {
        return Inventory.getItem(ids.maple_logs);
    }
}
