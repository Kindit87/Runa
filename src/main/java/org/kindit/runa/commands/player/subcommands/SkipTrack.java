package org.kindit.runa.commands.player.subcommands;

import java.util.Objects;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.kindit.runa.commands.Command;
import org.kindit.runa.commands.Subcommand;
import org.kindit.runa.lavaplayer.PlayerManager;
import org.kindit.runa.lavaplayer.TrackScheduler;

public class SkipTrack extends Subcommand {

    public SkipTrack(String name, String description, Command parentCommand) {
        super(name, description, parentCommand);
    }

    @Override
    public SubcommandData getSubCommandData() {
        return new SubcommandData(userName, description).addOptions(
            new OptionData(OptionType.INTEGER, "quantity", "Skip track", true)
                .setMinValue(1)
                .setMaxValue(Integer.MAX_VALUE)
        );
    }

    @Override
    public void interaction(SlashCommandInteractionEvent event) {
        TrackScheduler scheduler = PlayerManager.getINSTANCE().getMusicManager(
            event.getChannel().asTextChannel().getGuild()
        ).scheduler;
        event.deferReply().setEphemeral(true).queue();

        int skipOption = Objects.requireNonNull(
            event.getOption("quantity")
        ).getAsInt();

        if (scheduler.getQueue().size() <= skipOption - 1) {
            scheduler.clearQueue();
        } else {
            for (int i = 0; i < skipOption - 1; i++) {
                scheduler.getQueue().poll();
            }
            scheduler.nextTrack();
        }

        event
            .getHook()
            .sendMessageEmbeds(Command.successfullyReplyEmbed())
            .queue();
    }
}
