public class BookManager {
  public static void sortById(Book[] bookList){
    for(int i = 1; i < bookList.length; i++){
      Book currentBook = bookList[i];
      int j = i - 1;
      int currentId = Integer.parseInt(currentBook.getBookId().substring(1));
      while (j >= 0 && Integer.parseInt(bookList[j].getBookId().substring(1)) > currentId) {
        bookList[j + 1] = bookList[j];
        j--;
      }
      bookList[j + 1] = currentBook;
    }
  }
}
