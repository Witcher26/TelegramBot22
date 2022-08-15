package com.zvezdilin.TelegramBot.model.entity;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class JSoupNews {

    public static void main(String[] args) throws IOException {
        System.out.println("Telegram bot");

        TelegramBot bot = new TelegramBot("5586945414:AAGHN0R9ybKOhKQFr1oVEcPLklwyDYHddXc");
        bot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {
                try {
                    System.out.println(upd);
                    long chatId = upd.message().chat().id();
                    String incomeMessage = upd.message().text();

                    //logic
                    //пользователь даёт на вход номер новости, мы её ему возвращаем
                    int number = Integer.parseInt(incomeMessage);
                    Document doc = Jsoup.connect("http://lenta.ru/rss").get();
                    int index = number - 1;
                    Element news = doc.select("item").get(index);
                    String category = news.select("category").text();
                    String title = news.select("title").text();
                    String link = news.select("link").text();
                    String description = news.select("description").text();
                    String result = category + "\n" + title + "\n" + description + "\n" + link;
                    System.out.println(result);

                    //send response
                    SendMessage request = new SendMessage(chatId, result);
                    bot.execute(request);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}