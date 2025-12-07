package org.kindit.runa.commands.player.subcommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.kindit.runa.commands.Command;
import org.kindit.runa.commands.Subcommand;
import org.kindit.runa.lavaplayer.PlayerManager;
import org.kindit.runa.lavaplayer.TrackScheduler;

public class LoopTrack extends Subcommand {

    public LoopTrack(String name, String description, Command parentCommand) {
        super(name, description, parentCommand);
    }

    @Override
    public SubcommandData getSubCommandData() {
        return new SubcommandData(userName, description).addOptions(
            new OptionData(OptionType.STRING, "type", "Looping types", true)
                .addChoice("track", "track")
                .addChoice("queue", "queue")
        );
    }

    @Override
    public void interaction(SlashCommandInteractionEvent event)
        throws Exception {
        TrackScheduler scheduler = PlayerManager.getINSTANCE().getMusicManager(
            event.getChannel().asTextChannel().getGuild()
        ).scheduler;
        event.deferReply().setEphemeral(true).queue();

        if (event.getOption("type").getAsString().equals("queue")) {
            PlayerManager.getINSTANCE().queueLoop(
                event.getChannel().asTextChannel()
            );

            event
                .getHook()
                .sendMessageEmbeds(
                    Command.replyEmbed(
                        "Queue loop: " + scheduler.isQueueLoop,
                        Command.GOOD_COLOR
                    )
                )
                .setEphemeral(true)
                .queue();
        } else {
            PlayerManager.getINSTANCE().loop(
                event.getChannel().asTextChannel()
            );

            event
                .getHook()
                .sendMessageEmbeds(
                    Command.replyEmbed(
                        "Track loop: " + scheduler.isLoop,
                        Command.GOOD_COLOR
                    )
                )
                .setEphemeral(true)
                .queue();
        }
    }
}
