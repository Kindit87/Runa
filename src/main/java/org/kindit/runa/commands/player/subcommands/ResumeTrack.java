package org.kindit.runa.commands.player.subcommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.kindit.runa.commands.Command;
import org.kindit.runa.commands.Subcommand;
import org.kindit.runa.lavaplayer.PlayerManager;

public class ResumeTrack extends Subcommand {

    public ResumeTrack(String name, String description, Command parentCommand) {
        super(name, description, parentCommand);
    }

    @Override
    public SubcommandData getSubCommandData() {
        return new SubcommandData(userName, description);
    }

    @Override
    public void interaction(SlashCommandInteractionEvent event) {
        event.deferReply().setEphemeral(true).queue();

        PlayerManager.getINSTANCE().resume(event.getChannel().asTextChannel());

        event
            .getHook()
            .sendMessageEmbeds(Command.successfullyReplyEmbed())
            .queue();
    }
}
