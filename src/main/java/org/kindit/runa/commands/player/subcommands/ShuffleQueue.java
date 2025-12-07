package org.kindit.runa.commands.player.subcommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.kindit.runa.commands.Command;
import org.kindit.runa.commands.Subcommand;
import org.kindit.runa.lavaplayer.PlayerManager;
import org.kindit.runa.lavaplayer.TrackScheduler;

public class ShuffleQueue extends Subcommand {

    public ShuffleQueue(
        String name,
        String description,
        Command parentCommand
    ) {
        super(name, description, parentCommand);
    }

    @Override
    public SubcommandData getSubCommandData() {
        return new SubcommandData(userName, description);
    }

    @Override
    public void interaction(SlashCommandInteractionEvent event)
        throws Exception {
        TrackScheduler scheduler = PlayerManager.getINSTANCE().getMusicManager(
            event.getChannel().asTextChannel().getGuild()
        ).scheduler;
        event.deferReply().setEphemeral(true).queue();

        scheduler.shuffleQueue();

        event
            .getHook()
            .sendMessageEmbeds(Command.successfullyReplyEmbed())
            .queue();
    }
}
