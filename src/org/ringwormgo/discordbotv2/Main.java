package org.ringwormgo.discordbotv2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.ringwormgo.discordbotv2.event.Event;
import org.ringwormgo.discordbotv2.event.Listener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Main {
	public static String DISCORD_TOKEN; 
	public static String GITHUB_LOGIN;
	public static String GITHUB_TOKEN;
	
	public static JDA jda;
	public static GitHub github;
	
	public static HashMap<String, HashMap<List<Role>, Guild>> mutedPplAndRoles = new HashMap<String, HashMap<List<Role>, Guild>>();
		
	public Main() throws Exception {
		File token = new File("token.env");
		if(!token.exists()) throw new LoginException("Token file missing!");
		
		BufferedReader reader = new BufferedReader(new FileReader(token));
		DISCORD_TOKEN = reader.readLine();
		GITHUB_LOGIN = reader.readLine();
		GITHUB_TOKEN = reader.readLine();
		reader.close();
		
		jda = JDABuilder.createDefault(DISCORD_TOKEN)
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
	                    .addOption(OptionType.STRING, "reason", "The ban reason"),
	            Commands.slash("kick", "Kicks a user form the server")
			            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.KICK_MEMBERS)) // only usable with ban permissions
		                .setGuildOnly(true)
		                .addOption(OptionType.USER, "user", "The user to kick", true)
		                .addOption(OptionType.STRING, "reason", "The kick reason"),
		        Commands.slash("about", "Learn about the bot!"),
		        Commands.slash("github", "Get stats for a github repo")
		        	.addOption(OptionType.STRING, "account", "GitHub repo owner", true)
		        	.addOption(OptionType.STRING, "repo", "GitHub repo name", true),
		        Commands.slash("timeout", "Timeout a person")
		        	.setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
		        	.addOption(OptionType.USER, "user", "The user to timeout", true)
		        	.addOption(OptionType.INTEGER, "time", "Number of hours to timeout", true)
		        	.addOption(OptionType.STRING, "reason", "Reason for timeout")
	        ).queue();
		github = new GitHubBuilder().withOAuthToken(GITHUB_TOKEN, GITHUB_LOGIN).build();		
	}
	
	public static void main(String[] args) throws Exception {
		new Main();
	}
}
