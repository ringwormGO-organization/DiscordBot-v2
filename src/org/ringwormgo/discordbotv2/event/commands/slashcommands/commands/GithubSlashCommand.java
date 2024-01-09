package org.ringwormgo.discordbotv2.event.commands.slashcommands.commands;

import java.text.DateFormat;

import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHRepository;
import org.ringwormgo.discordbotv2.Main;
import org.ringwormgo.discordbotv2.event.commands.slashcommands.SlashCommand;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class GithubSlashCommand extends SlashCommand {
	public GithubSlashCommand() {}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		String user = event.getOption("account", OptionMapping::getAsString);
        String repo = event.getOption("repo", OptionMapping::getAsString);
//        
        event.deferReply().queue();
        event.reply("Responce is going to be sent is another message!").queue();
        
        try {
        	EmbedBuilder builder = new EmbedBuilder();
        	GHRepository repoInst = Main.github.getRepository(user + "/" + repo);
//			builder.setImage(repoInst.getOwner().getAvatarUrl());
			
			builder.addField("Owner Name", repoInst.getOwnerName(), false);
			builder.addField("Repository", repoInst.getFullName(), false);
			builder.addField("Description", repoInst.getDescription(), false);
			builder.addField("URL", repoInst.getUrl().toString(), false);
			builder.addField("Last updated", DateFormat.getInstance().format(repoInst.getUpdatedAt()), false);
			
			builder.addBlankField(false);
			
			builder.addField("Stargazers Count", String.valueOf(repoInst.getStargazersCount()), true);
			builder.addField("Forks", String.valueOf(repoInst.getForksCount()), true);
			
			builder.addBlankField(false);
			
			builder.addField("Total Issues", String.valueOf(repoInst.getIssues(GHIssueState.ALL).size()), true);
			builder.addField("Open Issues", String.valueOf(repoInst.getOpenIssueCount()), true);
			builder.addField("Closed Issues", String.valueOf(repoInst.getIssues(GHIssueState.CLOSED).size()), true);
			
			builder.addBlankField(false);
			
			builder.addField("Total Pull requests", String.valueOf(repoInst.getPullRequests(GHIssueState.ALL).size()), true);
			builder.addField("Open Pull requests", String.valueOf(repoInst.getPullRequests(GHIssueState.OPEN).size()), true);
			builder.addField("Closed/Merged Pull requests", String.valueOf(repoInst.getPullRequests(GHIssueState.CLOSED).size()), true);
			
//			builder.addBlankField(false);
			
			event.getChannel().sendMessageEmbeds(builder.build()).queue();
			event.getChannel().sendMessage(event.getUser().getAsMention()).queue();
		} catch (Exception e) {
			event.reply("Sorry! An error occured: " + e.getMessage()).queue();
			e.printStackTrace();
		}
	}
}
