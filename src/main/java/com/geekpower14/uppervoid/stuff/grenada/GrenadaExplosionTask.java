package com.geekpower14.uppervoid.stuff.grenada;

import com.geekpower14.uppervoid.Uppervoid;
import net.samagames.tools.ParticleEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/*
 * This file is part of Uppervoid.
 *
 * Uppervoid is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Uppervoid is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Uppervoid.  If not, see <http://www.gnu.org/licenses/>.
 */
public class GrenadaExplosionTask extends BukkitRunnable
{
    private final Uppervoid plugin;
    private final Grenada grenada;
    private final Item tnt;

    private double time;

    public GrenadaExplosionTask(Uppervoid plugin, Grenada grenada, Item tnt)
    {
        this.plugin = plugin;
        this.grenada = grenada;
        this.tnt = tnt;

        this.time = 1.3D;
    }

    @Override
    public void run()
    {
        if(this.tnt == null || this.tnt.isDead())
        {
            this.cancel();
            return;
        }

        float pitch = this.time % 2 == 0 ? 1.5F : 0.5F;

        for(Player p : this.plugin.getServer().getOnlinePlayers())
            p.getWorld().playSound(this.tnt.getLocation(), Sound.BLOCK_NOTE_PLING, 0.8F, pitch);

        if(this.tnt.isOnGround())
            ParticleEffect.FIREWORKS_SPARK.display(1F, 2F, 1F, 0.00005F, 5, this.tnt.getLocation(), 50);

        if(this.time <= 0)
        {
            this.cancel();
            this.plugin.getServer().getScheduler().runTask(this.plugin, () -> this.grenada.onItemTouchGround(this.plugin.getArena(), this.tnt));
            this.tnt.remove();

            return;
        }

        this.time -= 0.25D;
    }
}