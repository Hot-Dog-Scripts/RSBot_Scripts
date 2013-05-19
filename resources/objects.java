package resources;
/*
@author: Hot Dog
*/

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class objects {
    public static SceneObject nearest_fire () {
        return SceneEntities.getNearest( new Filter<SceneObject>() {
            @Override
            public boolean accept(SceneObject sO) {
                return sO.getDefinition().getName().equals("Fire") && Calculations.distanceTo(sO) <= 7;
            }
        });
    }

    public static SceneObject nearest_object (final int... id) {
        return SceneEntities.getNearest(id);
    }
}
