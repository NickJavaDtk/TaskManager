package ru.webDevelop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.webDevelop.telegram.TelegramBotTask;

@SpringBootApplication
public class WebDevelopApplication {

	public static void main(String[] args) throws TelegramApiException {
		ApplicationContext cnt = SpringApplication.run(WebDevelopApplication.class, args);
		//cnt.getBean(TelegramBot.class).printProperties();
		//System.out.println(cnt.getBean(TelegramBotTask.class).getBotToken());
		//System.out.println(cnt.getBean(TelegramBotTask.class).getBotUsername());
		TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
		botsApi.registerBot(cnt.getBean(TelegramBotTask.class, TelegramLongPollingBot.class));

	}

}
