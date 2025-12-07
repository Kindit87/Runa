package org.kindit.runa.commands.player;

import org.kindit.runa.commands.Command;
import org.kindit.runa.commands.Subcommand;
import org.kindit.runa.commands.player.subcommands.AddTrack;
import org.kindit.runa.commands.player.subcommands.GetQueue;
import org.kindit.runa.commands.player.subcommands.LoopTrack;
import org.kindit.runa.commands.player.subcommands.PauseTrack;
import org.kindit.runa.commands.player.subcommands.PlayerVolume;
import org.kindit.runa.commands.player.subcommands.ResumeTrack;
import org.kindit.runa.commands.player.subcommands.ShuffleQueue;
import org.kindit.runa.commands.player.subcommands.SkipTrack;
import org.kindit.runa.commands.player.subcommands.TrackInfo;

public class Player extends Command {

    private Player() {
        super("player", "Audio player");
    }

    public static Player createCommand() {
        Player command = new Player();
        command.setSubcommands(
            new Subcommand[] {
                new AddTrack("add", "Add track in queue", command),
                new SkipTrack("skip", "Skip track", command),
                new PauseTrack("pause", "Pauses it", command),
                new ResumeTrack("resume", "Resuming", command),
                new PlayerVolume("volume", "Sound volume", command),
                new TrackInfo("track-info", "Gets track information", command),
                new LoopTrack("loop", "loop track", command),
                new ShuffleQueue("shuffle", "Shuffles the queue", command),
                new GetQueue("queue", "Get the whole queue", command),
            }
        );
        return command;
    }
}
