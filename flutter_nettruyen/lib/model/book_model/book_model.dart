class BookModel {
  String author;
  String year;
  String description;
  String image;
  List<String> chapters;

  BookModel(
      {required this.author,
      required this.image,
      required this.year,
      required this.description,
      required this.chapters});
}
