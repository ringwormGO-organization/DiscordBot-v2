package org.ringwormgo.discordbotv2.event.commands.slashcommands.commands;

import org.ringwormgo.discordbotv2.event.commands.slashcommands.SlashCommand;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingSlashCommand extends SlashCommand {
	public PingSlashCommand() {}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		long time = System.currentTimeMillis();
        event.reply("Pong!").setEphemeral(true)
	         .flatMap(v ->
	              event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time) // then edit original
	         ).queue();
	}
}
