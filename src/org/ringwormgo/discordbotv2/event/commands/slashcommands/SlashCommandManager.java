package org.ringwormgo.discordbotv2.event.commands.slashcommands;

import java.util.HashMap;

import org.ringwormgo.discordbotv2.event.commands.slashcommands.commands.AboutSlashCommand;
import org.ringwormgo.discordbotv2.event.commands.slashcommands.commands.BanSlashCommand;
import org.ringwormgo.discordbotv2.event.commands.slashcommands.commands.GithubSlashCommand;
import org.ringwormgo.discordbotv2.event.commands.slashcommands.commands.KickSlashCommand;
import org.ringwormgo.discordbotv2.event.commands.slashcommands.commands.TimeoutSlashCommand;
import org.ringwormgo.discordbotv2.event.commands.slashcommands.commands.PingSlashCommand;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SlashCommandManager {
	private HashMap<String, SlashCommand> commands = new HashMap<String, SlashCommand>();
	
	public SlashCommandManager() {
		commands.put("ban", new BanSlashCommand());
		commands.put("kick", new KickSlashCommand());
		commands.put("ping", new PingSlashCommand());
		commands.put("about", new AboutSlashCommand());
		commands.put("github", new GithubSlashCommand());
		
		commands.put("timeout", new TimeoutSlashCommand());
	}
	
	public void manage(SlashCommandInteractionEvent event) {
		System.out.println("Slash command " + event.getName() + " used");
		
		new Thread(() -> {
			SlashCommand cmd = commands.get(event.getName());
			if(cmd == null) {
				System.err.printf("Unknown command %s used by %#s%n", event.getName(), event.getUser());
				event.reply("Unknown command!").queue();
				return;
			}
			cmd.execute(event);
		}, event.getName() + " command execution thread").start();
	}
}
