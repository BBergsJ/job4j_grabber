package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.Post;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlRuParse implements Parse {

    private static Map<String, Integer> months = new HashMap<>() {{
        put("янв", 1);
        put("фев", 2);
        put("мар", 3);
        put("апр", 4);
        put("май", 5);
        put("июн", 6);
        put("июл", 7);
        put("авг", 8);
        put("сен", 9);
        put("окт", 10);
        put("ноя", 11);
        put("дек", 12);
    }};

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> rsl = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Document doc = Jsoup.connect(link.endsWith("/") ? link + i : link + "/" + i).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                rsl.add(detail(href.attr("href")));
            }
        }
        return rsl;
    }

    @Override
    public Post detail(String link) throws IOException {
        Document document = Jsoup.connect(link).get();
        Elements msgBody = document.select(".msgBody");
        Elements msgFooter = document.select("td.msgFooter");
        Elements msgTitle = document.select(".messageHeader");
        return new Post(
                link,
                msgTitle.get(0).ownText(),
                msgBody.get(1).text(),
                dateConverter(msgFooter.get(0).ownText())
        );
    }

    public static String dateConverter(String date) {
        String[] strings = date.split(", ");
        String formattedTime = strings[1].replaceAll("[\\s\\[\\]\\|]", "");
        LocalTime timeForJoin = LocalTime.parse(formattedTime);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yy, HH:mm");
        if (strings[0].contains("сегодня")) {
            return LocalDateTime.of(LocalDate.now(), timeForJoin).format(dtf);
        } else if (strings[0].contains("вчера")) {
            return LocalDateTime.of(LocalDate.now().minusDays(1), timeForJoin).format(dtf);
        } else {
            String[] dateSplit = strings[0].split(" ");
            LocalDate dateForJoin = LocalDate.of(
                    Integer.parseInt(dateSplit[2]),
                    months.get(dateSplit[1]),
                    Integer.parseInt(dateSplit[0])
            );
            return LocalDateTime.of(dateForJoin, timeForJoin).format(dtf);
        }
    }

//    public static void main(String[] args) throws Exception {
//        for (int i = 1; i <= 5; i++) {
//            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get();
//            Elements row = doc.select(".postslisttopic");
//            for (Element td : row) {
//                Element href = td.child(0);
//                System.out.println(href.attr("href"));
//                System.out.println(href.text());
//                Element date = td.parent().child(5);
//                System.out.println(dateConverter(date.text()));
//                System.out.println(date.text() + System.lineSeparator());
//            }
//        }
//    }
//
//    public static Post parseToPost(String url) throws IOException {
//        Document document = Jsoup.connect(url).get();
//        Elements msgBody = document.select(".msgBody");
//        Elements msgFooter = document.select("td.msgFooter");
//        Elements msgTitle = document.select(".messageHeader");
//        return new Post(
//                url,
//                msgTitle.get(0).ownText(),
//                msgBody.get(1).text(),
//                dateConverter(msgFooter.get(0).ownText())
//        );
//    }
}

//        Elements elements = doc.getElementsByClass("forumTable").get(0).getElementsByTag("tr");
//        for (Element e : elements) {
//            System.out.println(e.child(5).text());
//        }