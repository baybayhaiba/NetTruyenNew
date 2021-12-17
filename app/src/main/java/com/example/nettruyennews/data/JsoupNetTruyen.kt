package com.example.nettruyennews.data

import Util
import com.example.nettruyennews.model.Book
import com.example.nettruyennews.model.Chapter
import com.example.nettruyennews.model.DescriptionBook
import com.example.nettruyennews.network.OkHttpHtml
import org.jsoup.Jsoup

class JsoupNetTruyen() : BookService {
    override suspend fun home(url: String): List<Book> {

        val html = OkHttpHtml.webToHtml(url)

        val document = Jsoup.parse(html)
        val books = mutableListOf<Book>()

        val elements = document.getElementsByClass("items")[0].children()

        for (element in elements) {
            val listManga = element.getElementsByClass("item")

            for (content in listManga) {
                val infoBook = content.getElementsByClass("box_img").select("a")
                val titleBook = infoBook.attr("title")
                val linkBook = infoBook.attr("href")
                val imageBook = infoBook.select("img").attr("data-original")

                val book = Book(title = titleBook, link =  linkBook, image =  imageBook)

                books.add(book)
            }
        }


        return books
    }

    override suspend fun description(book: Book): DescriptionBook {

        val html = OkHttpHtml.webToHtml(book.link)

        val document = Jsoup.parse(html)

        val elements = document.getElementsByClass("row")[0]
            .getElementsByClass("col-xs-8 col-info")[0]
            .getElementsByClass("list-info")[0]

        val ratingDocument = document.getElementsByClass("row")[0]
            .getElementsByClass("col-xs-8 col-info")[0]
            .getElementsByClass("mrt5 mrb10")
            .select("span")

        val descriptionDocument = document.getElementsByClass("detail-content")[0]
            .select("p")

        val chapterDocument = document.getElementsByClass("list-chapter")
            .select("nav").select("li")


        val author =
            elements.getElementsByClass("author row")[0].getElementsByClass("col-xs-8").last()
                ?.text() ?: Util.DEFAULT_AUTHOR
        val status =
            elements.getElementsByClass("status row")[0].getElementsByClass("col-xs-8").last()
                ?.text() ?: Util.DEFAULT_STATUS
        val crawCategories =
            elements.getElementsByClass("kind row")[0].getElementsByClass("col-xs-8").select("a")
        val view =
            elements.getElementsByClass("row").select("p").last()?.text() ?: Util.DEFAULT_VIEW
        val description = descriptionDocument.text()
        val rating = ratingDocument[1].text() ?: Util.DEFAULT_RATING
        val categories = mutableListOf<String>()
        val chapters = mutableListOf<Chapter>()
        for (category in crawCategories) {
            categories.add(category.text())
        }

        for ((index, chapter) in chapterDocument.withIndex()) {

            if (index == 0) {
                continue
            }

            val title = chapter.getElementsByClass("col-xs-5 chapter").text()
            val link = chapter.getElementsByClass("col-xs-5 chapter").select("a").attr("href")
            val timeAgo = chapter.getElementsByClass("col-xs-4 text-center small").text()
            val viewChapter = chapter.getElementsByClass("col-xs-3 text-center small").text()

            chapters.add(Chapter(title, link, timeAgo, viewChapter))
        }



        return DescriptionBook(
            view, categories, author, status, rating,
            description, book, chapters
        )
    }

    override suspend fun detail(
        currentChapter: Int,
        descriptionBook: DescriptionBook
    ): List<String> {

        val html = OkHttpHtml.webToHtml(descriptionBook.chapter[currentChapter].link)

        val document = Jsoup.parse(html)

        val rootImageDocument = document.getElementsByClass("reading-detail box_doc")[0]
            .getElementsByClass("page-chapter")

        val images = mutableListOf<String>()

        for (image in rootImageDocument) {
            images.add(image.select("img").attr("src"))
        }


        return images
    }


    companion object {
        @Volatile
        private var INSTANCE: JsoupNetTruyen? = null

        fun getInstance(): JsoupNetTruyen {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = JsoupNetTruyen()
                }
                return INSTANCE!!
            }
        }

    }

}