package org.kindit.runa;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.kindit.runa.data.JsonConfig;
import org.kindit.runa.listeners.CommandManager;
import org.kindit.runa.listeners.MessageManager;

public class Runa {

    private final ShardManager shardManager;

    public ShardManager getShardManager() {
        return shardManager;
    }

    public Runa() throws LoginException {
        // Build shard manager
        DefaultShardManagerBuilder builder =
            DefaultShardManagerBuilder.createDefault(
                JsonConfig.getInstance().TOKEN
            );
        builder.setStatus(OnlineStatus.ONLINE);
        builder.enableIntents(
            GatewayIntent.MESSAGE_CONTENT,
            GatewayIntent.GUILD_PRESENCES,
            GatewayIntent.GUILD_MEMBERS
        );
        builder.enableCache(CacheFlag.VOICE_STATE);
        shardManager = builder.build();

        // Register listeners
        shardManager.addEventListener(
            new MessageManager(),
            new CommandManager()
        );

        setUptimeStatus(shardManager);
    }

    private void setUptimeStatus(ShardManager shardManager) {
        new Thread(() -> {
            while (true) {
                long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
                shardManager.setActivity(
                    Activity.watching(convertMillisecondToReadableTime(uptime))
                );
                try {
                    Thread.sleep(5500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        })
            .start();
    }

    private static String convertMillisecondToReadableTime(long millisecond) {
        long days = TimeUnit.MILLISECONDS.toDays(millisecond);
        millisecond -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS.toHours(millisecond);
        millisecond -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(millisecond);
        millisecond -= TimeUnit.MINUTES.toMillis(minutes);

        StringBuilder result = new StringBuilder();

        if (days > 0) {
            result
                .append(days)
                .append(" day")
                .append(days > 1 ? "s" : "")
                .append(" ");
        }
        if (hours > 0) {
            result
                .append(hours)
                .append(" hour")
                .append(hours > 1 ? "s" : "")
                .append(" ");
        }
        if (minutes > 0) {
            result
                .append(minutes)
                .append(" minute")
                .append(minutes > 1 ? "s" : "")
                .append(" ");
        }
        if (result.isEmpty()) {
            result
                .append(0)
                .append(" minute")
                .append(minutes > 1 ? "s" : "")
                .append(" ");
        }

        return result.toString().trim();
    }

    public static void main(String[] args) {
        try {
            new Runa();
        } catch (LoginException e) {
            System.out.println("Error: Provided bot token is invalid");
        }
    }
}
