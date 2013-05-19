import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.util.Random;

import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.wrappers.node.SceneObject;
import resources.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;

@Manifest(name = "Daemonheim Chop N Burn", authors = {"Hot Dog"}, description = "Chops maple trees and make bonfires in resource dungeon", version = 0.1)
public class DChopNBurn extends ActiveScript implements PaintListener, KeyListener{

    public void onStart () {
        paintVars.start_fm_exp = Skills.getExperience(Skills.FIREMAKING);
        paintVars.start_wc_exp = Skills.getExperience(Skills.WOODCUTTING);
        paintVars.start_fm_level = Skills.getLevel(Skills.FIREMAKING);
        paintVars.start_wc_level = Skills.getLevel(Skills.WOODCUTTING);
        paintVars.start_time = System.currentTimeMillis();
        paintVars.main_paint_image = getImage("http://i1299.photobucket.com/albums/ag75/Hot_Dog_Scripts/chopnburn_zps4099259d.png");
        paintVars.mouse_paint_image = getImage("http://i1299.photobucket.com/albums/ag75/Hot_Dog_Scripts/mouse_zpscb521230.png");
    }

    @Override
    public int loop() {
        methods.resetTimer();

        if (methods.checkBurn()){
            vars.need_to_burn = true;
        }

        if (methods.stopBurn()){
            vars.need_to_burn = false;
        }

        if (methods.animation_timer.getRemaining() > 0){
            return (50);
        }

        if (vars.need_to_burn)
        {
            SceneObject fire = objects.nearest_fire();
            if (fire != null){
                paintVars.status="Adding Logs to Fire";
                if (methods.useItemOnObject(items.maple_logs(), fire)){
                    sleep(800, 1600);
                }
            } else {
                paintVars.status="Making a Fire";
                if (methods.interact(items.maple_logs(), "Light")){
                    sleep(500, 1500);
                }
            }
        } else {
            paintVars.status="Chopping Maples";
            if (methods.interact(objects.nearest_object(ids.maple_trees), "Chop")){
                sleep(600, 1600);
            }
        }
        return Random.nextInt(150,250);
    }

    @Override
    public void onRepaint(Graphics graphics) {
        paint.draw(graphics);
    }

    private Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch(IOException e) {
            return null;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getExtendedKeyCode() == KeyEvent.VK_TAB){
            if (paintVars.skill_to_show == 1) {
                paintVars.skill_to_show = 2;
            } else {
                paintVars.skill_to_show = 1;
            }
        }
        if (e.getExtendedKeyCode() == KeyEvent.VK_SPACE){
            if (paintVars.show_paint){
                paintVars.show_paint = false;
            } else {
                paintVars.show_paint = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
