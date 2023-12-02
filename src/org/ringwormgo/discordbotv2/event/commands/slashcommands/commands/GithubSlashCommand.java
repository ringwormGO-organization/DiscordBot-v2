package org.ringwormgo.discordbotv2.event.commands.slashcommands.commands;

import org.ringwormgo.discordbotv2.event.commands.slashcommands.SlashCommand;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class GithubSlashCommand extends SlashCommand {
	private String githubURL = "https://github.com/ringwormGO-organization";
	
	public GithubSlashCommand() {}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		event.reply("You can find the RingWormGO github at " + githubURL).queue();
	}
}
