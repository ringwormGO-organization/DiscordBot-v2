package org.ringwormgo.discordbotv2;

import org.ringwormgo.discordbotv2.event.Event;
import org.ringwormgo.discordbotv2.event.Listener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Main {
	public static final String TOKEN = "MTE3ODI1MjAyMzUxNTMyODU3Mg.GFEH8r.hXmktqqh6Np0EJpNoOnYrIFr9pEUstTwe-ZYKU"; 
	
	public static JDA jda;
	
	public static void main(String[] args) {
		jda = JDABuilder.createDefault(TOKEN)
				.enableIntents(GatewayIntent.MESSAGE_CONTENT)
				.enableIntents(GatewayIntent.GUILD_MODERATION)
				.enableIntents(GatewayIntent.GUILD_MEMBERS)
				.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
				.setBulkDeleteSplittingEnabled(false)
				.setActivity(Activity.playing("Discord"))
				.addEventListeners(new Event())
				.addEventListeners(new Listener())
				.build();
		
		jda.updateCommands().addCommands(
	            Commands.slash("ping", "Calculate ping of the bot"),
	            Commands.slash("ban", "Ban a user from the server")
	                    .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.BAN_MEMBERS)) // only usable with ban permissions
	                    .setGuildOnly(true)
	                    .addOption(OptionType.USER, "user", "The user to ban", true)
	                    .addOption(OptionType.INTEGER, "delete", "Deletes messages sent by the user from enters seconds ago", true)
	                    .addOption(OptionType.STRING, "reason", "The ban reason")
	        ).queue();
	}
}
