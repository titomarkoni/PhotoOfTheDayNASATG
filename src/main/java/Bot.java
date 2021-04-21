import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bot extends TelegramLongPollingBot {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Nasa nasa = new Nasa();
    private long chat_id;

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();

        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        chat_id = update.getMessage().getChatId();
        try {
            sendMessage.setText(input(update.getMessage().getText()));
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "@TestBotYTBot";
    }

    @Override
    public String getBotToken() {
        return "926947041:AAEEatsTLn3x49mr49zMOqIVh-oLnTgrWAw";
    }

    public String input(String msg) throws URISyntaxException, MalformedURLException {
        if (msg.contains("Фото дня")) {
            //getImg();
            return nasa.getImgNasa() + "\nФото дня от NASA дата: " + dateFormat.format(new Date()) + "\nЗаголовок: " + nasa.getTitleNasa();
        } else return "";
    }

    private void getImg() throws URISyntaxException {
        SendPhoto sendPhotoRequest = new SendPhoto();
        try (InputStream in = new URL(nasa.getImgNasa()).openStream()) {
            Path tempFile = Files.createTempFile("my-file", ".jpg");
            Files.copy(in, Paths.get("C:\\srg\\" + tempFile.getFileName()));   // копируем изображение
            System.out.println("file copied");
            sendPhotoRequest.setChatId(chat_id);
            sendPhotoRequest.setPhoto(new File("C:\\srg\\" + tempFile.getFileName())); // отправляем изображение в Telegram из каталога
            System.out.println("file send");
            execute(sendPhotoRequest);
            Files.delete((Paths.get("C:\\srg\\" + tempFile.getFileName())));   // удаляем изображение из каталога
            System.out.println("file delete");
        } catch (IOException e) {
            System.out.println("file not found");
        } catch (TelegramApiException d) {
            d.printStackTrace();
        }
    }
}
