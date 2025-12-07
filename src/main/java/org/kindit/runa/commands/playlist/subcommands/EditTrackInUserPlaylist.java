package org.kindit.runa.commands.playlist.subcommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.kindit.runa.commands.Command;
import org.kindit.runa.commands.Subcommand;
import org.kindit.runa.data.JsonUserPlaylistData;

public class EditTrackInUserPlaylist extends Subcommand {

    public EditTrackInUserPlaylist(
        String name,
        String description,
        Command parentCommand
    ) {
        super(name, description, parentCommand);
    }

    @Override
    public SubcommandData getSubCommandData() {
        return new SubcommandData(userName, description).addOptions(
            new OptionData(
                OptionType.STRING,
                "name",
                "Your name for url",
                true
            ),
            new OptionData(
                OptionType.STRING,
                "new-url",
                "YouTube video URL",
                true
            )
        );
    }

    @Override
    public void interaction(SlashCommandInteractionEvent event)
        throws Exception {
        event.deferReply().setEphemeral(true).queue();

        JsonUserPlaylistData playlistData = new JsonUserPlaylistData(
            event.getMember().getIdLong()
        );

        String newUrl = event.getOption("new-url").getAsString();
        String newName = event.getOption("name").getAsString();

        playlistData.editUrl(newName, newUrl);

        if (playlistData.isSet()) {
            event
                .getHook()
                .sendMessageEmbeds(Command.successfullyReplyEmbed())
                .setEphemeral(true)
                .queue();
        } else {
            event
                .getHook()
                .sendMessageEmbeds(Command.notSuccessfullyReplyEmbed())
                .setEphemeral(true)
                .queue();
        }
    }
}
