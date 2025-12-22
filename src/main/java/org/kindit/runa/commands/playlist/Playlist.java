package org.kindit.runa.commands.playlist;

import org.kindit.runa.commands.Command;
import org.kindit.runa.commands.Subcommand;
import org.kindit.runa.commands.playlist.subcommands.AddToUserPlaylist;
import org.kindit.runa.commands.playlist.subcommands.DeleteFromUserPlaylist;
import org.kindit.runa.commands.playlist.subcommands.EditTrackInUserPlaylist;
import org.kindit.runa.commands.playlist.subcommands.GetUserPlaylist;
import org.kindit.runa.commands.playlist.subcommands.PlayUserPlaylist;

public class Playlist extends Command {

    private Playlist() {
        super("playlist", "Your playlist");
    }

    public static Playlist createCommand() {
        Playlist command = new Playlist();
        command.setSubcommands(
            new Subcommand[] {
                new AddToUserPlaylist("add", "Add to yours playlist", command),
                new DeleteFromUserPlaylist(
                    "delete",
                    "Delete from your playlist",
                    command
                ),
                new EditTrackInUserPlaylist(
                    "edit",
                    "Edit track in your playlist",
                    command
                ),
                new GetUserPlaylist("get", "get", command),
                new PlayUserPlaylist(
                    "play",
                    "Add entire playlist to queue",
                    command
                ),
            }
        );
        return command;
    }
}
