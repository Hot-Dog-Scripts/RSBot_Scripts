package resources;/*
@author: Hot Dog
*/

import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Skills;

import java.awt.*;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

public class paint {

    // Format functions taken from Mentor_Altair's Wilderness Agility Script
    private static int ph(int arg0) {
        return (int) (arg0 * 3600000D / (System.currentTimeMillis() - paintVars.start_time));
    }

    private static String fn(int n) {
        return NumberFormat.getInstance().format(n);
    }

    private static String tf(long duration) {
        String res = "";
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration)
                - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                .toHours(duration));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                .toMinutes(duration));
        if (days == 0) {
            res = (hours + ":" + minutes + ":" + seconds);
        } else {
            res = (days + ":" + hours + ":" + minutes + ":" + seconds);
        }
        return res;
    }
    //End of functions taken

    public static void draw (Graphics g1) {
        if (!paintVars.show_paint){
            return;
        }

        paintVars.current_fm_exp = Skills.getExperience(Skills.FIREMAKING);
        paintVars.current_wc_exp = Skills.getExperience(Skills.WOODCUTTING);
        paintVars.current_fm_level = Skills.getLevel(Skills.FIREMAKING);
        paintVars.current_wc_level = Skills.getLevel(Skills.WOODCUTTING);
        paintVars.current_time = System.currentTimeMillis();

        String current_skill = "", exp_gained = "", exp_hour = "", time_ran = "", levels_gained = "", ttl = "", level = "";

        if (paintVars.skill_to_show == 1) {
            current_skill = "Woodcutting";
            exp_gained = fn(paintVars.current_wc_exp - paintVars.start_wc_exp);
            levels_gained = fn(paintVars.current_wc_level - paintVars.start_wc_level);
            ttl = tf((long) ((double) Skills.getExperienceToLevel(Skills.WOODCUTTING, paintVars.current_wc_level + 1) / (double) ph(paintVars.current_wc_exp - paintVars.start_wc_exp) * 3600000));
            exp_hour = fn(ph(paintVars.current_wc_exp - paintVars.start_wc_exp));
            level = fn(paintVars.current_wc_level);
        } else {
            current_skill = "Firemaking";
            exp_gained = fn(paintVars.current_fm_exp - paintVars.start_fm_exp);
            levels_gained = fn(paintVars.current_fm_level - paintVars.start_fm_level);
            ttl = tf((long) ((double) Skills.getExperienceToLevel(Skills.FIREMAKING, paintVars.current_fm_level + 1) / (double) ph(paintVars.current_fm_exp - paintVars.start_fm_exp) * 3600000));
            exp_hour = fn(ph(paintVars.current_fm_exp - paintVars.start_fm_exp));
            level = fn(paintVars.current_fm_level);
        }

        time_ran = tf(paintVars.current_time - paintVars.start_time);

        Graphics2D g = (Graphics2D)g1;
        g.drawImage(paintVars.main_paint_image, 0, 356, null);
        g.setFont(new Font("Tahoma", 0, 16));
        g.setColor(new Color(255, 255, 255));
        g.drawString("Run Time: " + time_ran + " || Status: " + paintVars.status + "|| Current Skill: " + current_skill, 14, 442);
        g.drawString("Experience Gained: " + exp_gained + " (" + exp_hour + " per hour)", 14, 467);
        g.drawString("Level: " + level + " (" + levels_gained + " gained) || Time TNL: " + ttl, 14, 492);
        g.drawString("Press \"Tab\" to Toggle Skill || Press the \"Spacebar\" to Hide/Show Paint", 14, 521);
       //20, 61
        g.drawImage(paintVars.mouse_paint_image, Mouse.getX() - 20, Mouse.getY() - 61, null);
    }

}
